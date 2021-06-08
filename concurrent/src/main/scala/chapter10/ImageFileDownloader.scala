package com.github.trackiss
package chapter10

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import okhttp3._

import java.io.{File, IOException}
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.util.{Failure, Success, Try}

final class DownloadFailException extends IOException

object ImageFileDownloader {
  val jpegMediaType: MediaType = MediaType.parse("image/jpeg")

  def apply(
      config: Config,
      client: OkHttpClient,
      wnidWordMap: Map[String, String]
  ): Behavior[Message] = {

    Behaviors.receive { (ctx, msg) =>
      msg match {
        case imageNetUrl: ImageNetUrl =>
          val request = new Request.Builder().url(imageNetUrl.url).build()

          client
            .newCall(request)
            .enqueue(new Callback {
              override def onFailure(call: Call, e: IOException): Unit =
                imageNetUrl.sender ! DownloadFailure(e, imageNetUrl, ctx.self)

              override def onResponse(call: Call, response: Response): Unit = {
                if (
                  response.isSuccessful && response
                    .body()
                    .contentType() == jpegMediaType
                ) {
                  val dir = new File(
                    new File(config.outputDirPath),
                    imageNetUrl.wnid + "-" + wnidWordMap(imageNetUrl.wnid)
                  )
                  dir.mkdir()

                  val downloadFile = new File(dir, imageNetUrl.id + ".jpg")

                  if (!downloadFile.exists()) downloadFile.createNewFile()

                  val tmpFilePath = Paths.get(downloadFile.getAbsolutePath)

                  Try {
                    Files.write(
                      tmpFilePath,
                      response.body().bytes(),
                      StandardOpenOption.WRITE
                    )
                  } match {
                    case Success(_) =>
                      imageNetUrl.sender ! DownloadSuccess(
                        downloadFile.getAbsolutePath,
                        imageNetUrl,
                        ctx.self
                      )
                    case Failure(e) =>
                      downloadFile.delete()
                      imageNetUrl.sender ! DownloadFailure(
                        e,
                        imageNetUrl,
                        ctx.self
                      )
                  }
                } else {
                  imageNetUrl.sender ! DownloadFailure(
                    new DownloadFailException,
                    imageNetUrl,
                    ctx.self
                  )
                }
                response.close()
              }
            })
          Behaviors.same
        case _ => Behaviors.same
      }
    }
  }
}

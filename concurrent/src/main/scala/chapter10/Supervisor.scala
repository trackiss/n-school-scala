package com.github.trackiss
package chapter10

import akka.actor.typed.{
  ActorRef,
  Behavior,
  MailboxSelector,
  SupervisorStrategy
}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors, Routers}
import okhttp3.OkHttpClient

import java.util.concurrent.TimeUnit
import scala.io.Source

object Supervisor {
  def apply(config: Config): Behavior[Message] = {
    val wordsFileSource = Source.fromFile(config.wordsFilePath)
    val wnidWordMap = wordsFileSource
      .getLines()
      .map(s => {
        val strs = s.split("\t")
        (strs.head, strs.tail.mkString("\t"))
      })
      .toMap

    val client = new OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.SECONDS)
      .writeTimeout(1, TimeUnit.SECONDS)
      .readTimeout(1, TimeUnit.SECONDS)
      .build()

    Behaviors.setup { ctx =>
      val urlsFileLoader =
        ctx.spawn(UrlsFileLoader(config), "urls-loader-actor")

      val pool =
        Routers
          .pool(config.numOfDownloader)(
            Behaviors
              .supervise(ImageFileDownloader(config, client, wnidWordMap))
              .onFailure(SupervisorStrategy.restart)
          )
          .withRoundRobinRouting()

      val router =
        ctx.spawn(pool, "downloader-actors", MailboxSelector.bounded(10))
      run(ctx, urlsFileLoader, router, config, 0, 0, 0, wnidWordMap)
    }
  }

  private def run(
      context: ActorContext[Message],
      urlFileLoader: ActorRef[Message],
      router: ActorRef[Message],
      config: Config,
      successCount: Int,
      failureCount: Int,
      fileLoadedUrlCount: Int,
      wnidWordWrap: Map[String, String]
  ): Behavior[Message] =
    Behaviors.receiveMessage {
      case Start(_) =>
        urlFileLoader ! LoadUrlsFile(context.self)
        Behaviors.same
      case imageNetUrl: ImageNetUrl =>
        router ! imageNetUrl.copy(sender = context.self)
        run(
          context,
          urlFileLoader,
          router,
          config,
          successCount,
          failureCount,
          fileLoadedUrlCount + 1,
          wnidWordWrap
        )
      case DownloadSuccess(_, _, sender) =>
        printConsoleAndCheckFinish(
          context,
          sender,
          successCount + 1,
          failureCount,
          fileLoadedUrlCount
        )
        run(
          context,
          urlFileLoader,
          router,
          config,
          successCount + 1,
          failureCount,
          fileLoadedUrlCount,
          wnidWordWrap
        )
      case DownloadFailure(_, _, sender) =>
        printConsoleAndCheckFinish(
          context,
          sender,
          successCount,
          failureCount + 1,
          fileLoadedUrlCount
        )
        run(
          context,
          urlFileLoader,
          router,
          config,
          successCount,
          failureCount + 1,
          fileLoadedUrlCount,
          wnidWordWrap
        )
      case _ => Behaviors.same
    }

  private def printConsoleAndCheckFinish(
      context: ActorContext[Message],
      sender: ActorRef[Message],
      successCount: Int,
      failureCount: Int,
      fileLoadedUrlCount: Int
  ): Unit = {
    val total = successCount + failureCount
    println(
      s"total: $total, successCount: $successCount, failureCount: $failureCount"
    )
    if (total == fileLoadedUrlCount) sender ! Finished(context.self)
  }
}

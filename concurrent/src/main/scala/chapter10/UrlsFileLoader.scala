package com.github.trackiss
package chapter10

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.io.{Codec, Source}

object UrlsFileLoader {
  def apply(config: Config): Behavior[Message] =
    Behaviors.receive { (ctx, msg) =>
      msg match {
        case LoadUrlsFile(sender) =>
          val urlsFileSource = Source.fromFile(config.urlsFilePath)(Codec.UTF8)
          val urlIterator = urlsFileSource.getLines()
          urlIterator foreach { line =>
            val strs = line.split("\t")
            val id = strs.head
            val url = strs.tail.mkString("\t")
            val wnid = id.split("_").head
            sender ! ImageNetUrl(id, url, wnid, ctx.self)
          }
          urlsFileSource.close()
          Behaviors.same
        case _ => Behaviors.same
      }
    }
}

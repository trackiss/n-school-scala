package com.github.trackiss
package chapter10

import akka.actor.typed.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ShokyuAndChukyuAndJokyu {
  def main(): Unit = {
    val wordsFilePath = "/path/to/words.txt"
    val urlsFilePath = "/path/to/fall11_urls.txt"
    val outputDirPath = "/path/to/output"
    val numOfDownloader = 2000

    val config =
      Config(wordsFilePath, urlsFilePath, outputDirPath, numOfDownloader)

    val system = ActorSystem(Supervisor(config), "image-downloader-actor")
    system ! Start(system)

    Await.ready(system.whenTerminated, Duration.Inf)
  }
}

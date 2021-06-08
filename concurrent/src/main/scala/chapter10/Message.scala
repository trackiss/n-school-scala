package com.github.trackiss
package chapter10

import akka.actor.typed.ActorRef

abstract class Message(val sender: ActorRef[Message])

sealed abstract class SupervisorMessage(sender: ActorRef[Message])
    extends Message(sender)
case class Start(override val sender: ActorRef[Message])
    extends SupervisorMessage(sender)
case class Finished(override val sender: ActorRef[Message])
    extends SupervisorMessage(sender)
case class DownloadSuccess(
    tmpFilePath: String,
    imageNetUrl: ImageNetUrl,
    override val sender: ActorRef[Message]
) extends SupervisorMessage(sender)
case class DownloadFailure(
    e: Throwable,
    imageNetUrl: ImageNetUrl,
    override val sender: ActorRef[Message]
) extends SupervisorMessage(sender)

sealed abstract class UrlsFileLoaderMessage(sender: ActorRef[Message])
    extends Message(sender)
case class LoadUrlsFile(override val sender: ActorRef[Message])
    extends UrlsFileLoaderMessage(sender)

sealed abstract class ImageFileDownloaderMessage(sender: ActorRef[Message])
    extends Message(sender)

case class ImageNetUrl(
    id: String,
    url: String,
    wnid: String,
    override val sender: ActorRef[Message]
) extends ImageFileDownloaderMessage(sender)

package com.github.trackiss
package chapter08

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

object Shokyu {
  final case class Message()

  object MessageCountActor {
    def apply(receiveCount: Int = 0): Behavior[Message] =
      Behaviors.receive { (_, _) =>
        println(receiveCount + 1)
        MessageCountActor(receiveCount + 1)
      }
  }

  def MessageCountActorApp(): Unit = {
    val system = ActorSystem(MessageCountActor(), "message-count-actor")

    (0 until 10000).foreach(_ => system ! Message())

    Thread.currentThread().join()
  }
}

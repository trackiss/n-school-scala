package com.github.trackiss
package chapter08

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import java.time.LocalDateTime

object Jokyu {
  final case class Message(value: String)

  object TooLateLoggingActor {
    def apply(): Behavior[Message] =
      Behaviors.receive { (ctx, msg) =>
        Thread.sleep(1000)
        ctx.log.info(msg.value)
        Behaviors.same
      }
  }

  def TooMuchMessageApp(): Unit = {
    val system = ActorSystem(TooLateLoggingActor(), "logging-actor")

    while (true) {
      system ! Message(LocalDateTime.now().toString)
    }
  }
}

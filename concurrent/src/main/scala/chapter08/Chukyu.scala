package com.github.trackiss
package chapter08

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Chukyu {
  final case class Message(value: String, sender: ActorRef[Message])

  object MainActor {
    def apply(): Behavior[Unit] =
      Behaviors.setup { ctx =>
        val greeter = ctx.spawn(GreetActor(), "greeter-actor")
        val replyer = ctx.spawn(ReplyActor(), "replyer-actor")

        Behaviors.receiveMessage { _ =>
          replyer ! Message("Nice to meet you.", greeter)
          Behaviors.same
        }
      }
  }

  object GreetActor {
    def apply(): Behavior[Message] =
      Behaviors.receive { (ctx, msg) =>
        ctx.log.info(msg.value)
        Thread.sleep(500)
        msg.sender ! Message(msg.value.init + ", too.", ctx.self)
        Behaviors.same
      }

  }

  object ReplyActor {
    def apply(): Behavior[Message] =
      Behaviors.receive { (ctx, msg) =>
        ctx.log.info(msg.value)
        Thread.sleep(500)
        msg.sender ! Message(msg.value.init + ", too.", ctx.self)
        Behaviors.same
      }
  }

  def ReplyActorApp(): Unit = {
    val system = ActorSystem(MainActor(), "greeter-actor")
    system ! ()
  }
}

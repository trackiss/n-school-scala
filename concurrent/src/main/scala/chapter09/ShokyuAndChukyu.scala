package com.github.trackiss
package chapter09

import akka.actor.typed.{ActorSystem, Behavior, SupervisorStrategy}
import akka.actor.typed.scaladsl.Behaviors

object ShokyuAndChukyu {
  object ParentActor {
    def apply(): Behavior[(Int, Int)] =
      Behaviors
        .supervise[(Int, Int)] {
          Behaviors.setup { ctx =>
            val child = ctx.spawn(ChildActor(), "child-actor")

            Behaviors.receiveMessage { numPair =>
              child ! numPair
              ctx.log.info(child.toString)
              Behaviors.same
            }
          }
        }
        .onFailure(SupervisorStrategy.resume)
  }

  object ChildActor {
    def apply(): Behavior[(Int, Int)] =
      Behaviors.receive { (ctx, numPair) =>
        ctx.log.info(
          s"${numPair._1} / ${numPair._2} = ${numPair._1 / numPair._2}"
        )
        Behaviors.same
      }
  }

  def MustResume(): Unit = {
    val system = ActorSystem(ParentActor(), "parent-actor")

    system ! (10, 5)
    system ! (10, 0)
    system ! (10, 5)
  }
}

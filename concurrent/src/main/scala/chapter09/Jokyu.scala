package com.github.trackiss
package chapter09

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

import scala.math.ceil

object Jokyu {
  final case class Task(numbers: Seq[Int], sender: ActorRef[Result])
  final case class Result(countPrimeNumber: Int)

  object MainActor {
    private def splitRange(range: Range, divideBy: Int): Seq[Range] = {
      val chunkSize = ceil((range.last - range.head) / divideBy.toDouble).toInt

      (0 until divideBy) map { i =>
        if (i != divideBy - 1)
          (chunkSize * i + range.head) to (chunkSize * i + range.head + chunkSize - 1)
        else
          (chunkSize * i + range.head) to range.last
      }
    }

    def apply(numbers: Range, numChild: Int): Behavior[Result] = {
      Behaviors.setup { ctx =>
        val children = (1 to numChild) map { x =>
          ctx.spawn(PrimeNumberJudgeActor(), s"child-actor-$x")
        }

        val ranges = splitRange(numbers, numChild)

        children.zip(ranges) foreach { case (child, range) =>
          child ! Task(range, ctx.self)
        }

        run(ctx, numChild, 0, 0)
      }
    }

    private def run(
        context: ActorContext[Result],
        numChild: Int,
        count: Int,
        acc: Int
    ): Behavior[Result] = {
      Behaviors.receiveMessage { msg =>
        if (count == numChild - 1) {
          context.log.info(s"Result: ${acc + msg.countPrimeNumber}")
          Behaviors.same
        } else
          MainActor.run(
            context,
            numChild,
            count + 1,
            acc + msg.countPrimeNumber
          )
      }
    }
  }

  object PrimeNumberJudgeActor {
    private def isPrime(n: Int): Boolean =
      if (n < 2) false else !((2 until n - 1) exists (n % _ == 0))

    def apply(): Behavior[Task] =
      Behaviors.receive { (ctx, msg) =>
        val result = (msg.numbers map { number =>
          if (isPrime(number)) 1 else 0
        }).sum

        ctx.log.info(s"${ctx.self.path.name}: $result")

        msg.sender ! Result(result)

        Behaviors.same
      }
  }

  def PrimeNumberSearch(): Unit = {
    val _ = ActorSystem(MainActor(1010000 to 1040000, 4), "main-actor")
  }
}

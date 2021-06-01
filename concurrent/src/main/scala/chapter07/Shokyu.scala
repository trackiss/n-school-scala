package com.github.trackiss
package chapter07

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success}

object Shokyu {
  def compositeFutureChallenge(): Unit = {
    val random = new Random()

    val f1 = Future {
      random.nextInt(10)
    }
    val f2 = Future {
      random.nextInt(10)
    }
    val f3 = Future {
      random.nextInt(10)
    }
    val f4 = Future {
      random.nextInt(10)
    }

    val compositeFuture = for {
      x1 <- f1
      x2 <- f2
      x3 <- f3
      x4 <- f4
    } yield x1 * x2 * x3 * x4

    compositeFuture onComplete {
      case Success(value)     => println(value)
      case Failure(throwable) => throwable.printStackTrace()
    }
    Await.result(compositeFuture, Duration.Inf)
  }
}

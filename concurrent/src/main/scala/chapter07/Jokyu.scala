package com.github.trackiss
package chapter07

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.Random

object Jokyu {
  def countDownLatchSample(): Unit = {
    val random = new Random()
    val count = new AtomicInteger(0)

    val promises = (0 until 3) map { _ => Promise[Int] }

    val futures = (0 until 8) map { _ =>
      Future[Int] {
        val ms = random.nextInt(1000)
        Thread.sleep(ms)
        ms
      }
    }

    futures foreach {
      _ foreach { ms =>
        if (count.incrementAndGet() <= promises.size)
          promises(count.get - 1).success(ms)
      }
    }

    promises foreach { _.future foreach println }
  }
}

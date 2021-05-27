package com.github.trackiss
package chapter04

import java.util.concurrent.FutureTask

object Chukyu {
  def futureTaskForLatch(): Unit = {
    val futureTasks =
      for (i <- 1 to 3)
        yield new FutureTask[Int](() => {
          Thread.sleep(1000)
          println(s"FutureTask $i finished")
          i
        })

    futureTasks.foreach(new Thread(_).start())

    new Thread(() => {
      futureTasks.foreach(_.get())
      println("All finished.")
    }).start()
  }
}

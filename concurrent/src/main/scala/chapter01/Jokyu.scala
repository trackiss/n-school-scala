package com.github.trackiss
package chapter01

object Jokyu {
  def deadLock(): Unit = {
    var now = 0L

    val threadA = new Thread(() =>
      synchronized {
        Thread.sleep(1000)
        now = System.currentTimeMillis()
      }
    )

    val threadB = new Thread(() =>
      synchronized {
        while (now == 0)
          Thread.sleep(1000)

        println(now)
      }
    )

    threadA.start()
    threadB.start()
  }
}

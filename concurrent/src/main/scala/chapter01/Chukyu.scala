package com.github.trackiss
package chapter01

object Chukyu {
  def tenThousandNamePrint(): Unit =
    (0 until 10000) foreach { _ =>
      new Thread(() => {
        Thread.sleep(1000)
        println(Thread.currentThread().getName)
      }).start()
    }
}

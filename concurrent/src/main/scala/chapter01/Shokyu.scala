package com.github.trackiss
package chapter01

object Shokyu {
  def quadNumberPrint(): Unit =
    (0 until 4) foreach { t =>
      new Thread(() => {
        (1 to 100000) foreach { n => println(s"thread $t: $n") }
      }).start()
    }
}

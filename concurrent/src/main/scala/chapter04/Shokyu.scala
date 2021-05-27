package com.github.trackiss
package chapter04

object Shokyu {
  def interruption(): Unit = {
    val t = new Thread(() =>
      try {
        while (true)
          println("Sleeping...")
        Thread.sleep(1000)
      } catch {
        case _: InterruptedException =>
      }
    )

    t.start()
    t.interrupt()
  }
}

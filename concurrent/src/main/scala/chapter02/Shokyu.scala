package com.github.trackiss
package chapter02

import java.util.concurrent.atomic.AtomicLong

object Shokyu {
  def fooBar(): Unit = (1 to 100) foreach { _ =>
    new Thread(() => println(AtomicLongCounter.next)).start()
  }

  object AtomicLongCounter {
    val count = new AtomicLong(0)
    def next: Long = count.incrementAndGet()
  }
}

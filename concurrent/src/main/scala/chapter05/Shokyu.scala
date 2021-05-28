package com.github.trackiss
package chapter05

import java.util.concurrent.Executors

object Shokyu {
  def tenThousandNamePrinter(): Unit = {
    val es = Executors.newFixedThreadPool(10)

    for (_ <- 1 to 10000)
      es.submit(new Runnable {
        override def run(): Unit = Thread.sleep(1000);
        println(Thread.currentThread().getName)
      })

    es.shutdownNow()
  }
}

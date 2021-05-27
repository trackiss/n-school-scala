package com.github.trackiss
package chapter04

import java.util.concurrent.{CopyOnWriteArrayList, Semaphore}

object Jokyu {
  val arrayList = new CopyOnWriteArrayList[Runnable]()
  val semaphore = new Semaphore(10)

  def queueWIthSemaphore(): Unit = {
    for (i <- 1 to 100)
      arrayList.add(() => {
        Thread.sleep(1000)
        println(s"Runnable: $i finished.")
      })

    (1 to 20) foreach {
      val rs = new Thread(() => {
        while (true) {
          try semaphore.acquire()
          try {
            val r = arrayList.remove(0)
            r.run()
          } finally {
            semaphore.release()
          }
        }
      })

      rs.start()
    }
  }
}

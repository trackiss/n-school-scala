package com.github.trackiss
package advance.chapter05

import scala.collection.immutable.{HashMap, TreeMap}

object Jokyu {
  def foo(): Unit = {
    var a: Array[Int] = Array.empty
    var hm = HashMap[Int, Int]()
    var tm = TreeMap[Int, Int]()

    benchmark {
      a = (0 until 100000).toArray
    }
    benchmark {
      val _ = a(99999)
    }

    benchmark {
      hm = HashMap((0 until 100000) map (x => x -> x): _*)
    }
    benchmark {
      val _ = hm(99999)
    }

    benchmark {
      tm = TreeMap((0 until 100000) map (x => x -> x): _*)
    }
    benchmark {
      val _ = tm(99999)
    }
  }

  def benchmark(f: => Unit): Unit = {
    val begin = System.nanoTime()
    f
    val end = System.nanoTime()

    val formatter = java.text.NumberFormat.getNumberInstance()
    println(s"time: ${formatter.format(end - begin)} ナノ秒")
  }
}

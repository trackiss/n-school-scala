package com.github.trackiss
package advance.chapter05

object Shokyu {
  def foo(): Unit = {
    var l1 = List[Int]()
    var a1: Array[Int] = Array.empty
    var l2 = List[Int]()
    var a2: Array[Int] = Array.empty

    benchmark {
      (0 to 10000) foreach (x => l1 = l1 :+ x)
    }

    benchmark {
      (0 to 10000) foreach (x => a1 = a1 :+ x)
    }

    benchmark {
      (0 to 10000) foreach (x => l2 = x +: l2)
    }

    benchmark {
      (0 to 10000) foreach (x => a2 = x +: a2)
    }
  }

  def benchmark(f: => Unit): Unit = {
    val begin = System.currentTimeMillis()
    f
    val end = System.currentTimeMillis()

    val formatter = java.text.NumberFormat.getNumberInstance()
    println(s"time: ${formatter.format(end - begin)} ミリ秒")
  }
}

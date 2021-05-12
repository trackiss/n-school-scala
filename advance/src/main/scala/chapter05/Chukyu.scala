package com.github.trackiss
package chapter05

object Chukyu {
  def foo(): Unit = {
    val l: List[Int] = (0 until 10000000).toList
    val a: Array[Int] = (0 until 10000000).toArray

    benchmark {
      val _ = l(9999999)
    }

    benchmark {
      val _ = a(9999999)
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

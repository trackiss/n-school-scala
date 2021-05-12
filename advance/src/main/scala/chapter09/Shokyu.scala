package com.github.trackiss
package chapter09

object Shokyu {
  def format(number: Int): String = {
    val ss = number.toString.reverse.zipWithIndex map {
      case (c: Char, i: Int) =>
        if (i % 3 == 2) c.toString + " " else c.toString
    }

    ss.mkString.trim.replace(' ', ',').reverse
  }
}

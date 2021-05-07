package com.github.trackiss
package advance.chapter01

import scala.annotation.tailrec

object Shokyu {
  @tailrec
  def fact(n: Int, acc: Int): Int =
    if (n <= 1) acc else fact(n - 1, acc * n)
}

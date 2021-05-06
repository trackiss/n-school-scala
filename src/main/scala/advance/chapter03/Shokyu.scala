package com.github.trackiss
package advance.chapter03

object Shokyu {
  def foo(): Option[Int] = {
    val v1 = Some(2)
    val v2 = Some(3)
    val v3 = Some(5)
    val v4 = Some(7)
    val v5 = Some(11)

    for {
      x1 <- v1
      x2 <- v2
      x3 <- v3
      x4 <- v4
      x5 <- v5
    } yield x1 * x2 * x3 * x4 * x5
  }
}

package com.github.trackiss
package advance.chapter03

object Chukyu {
  def foo(): Option[Int] = {
    val f1 = Some((x: Int) => x * 2)
    val f2 = Some((x: Int) => x + 10)
    val f3 = Some((x: Int) => x / 3)

    val fff = for {
      x1 <- f1
      x2 <- f2
      x3 <- f3
    } yield x1 andThen x2 andThen x3

    fff map (_(15))
  }
}

package com.github.trackiss
package advance.chapter06

object Shokyu {
  def filter[T](list: List[T])(f: T => Boolean): List[T] =
    list.foldLeft(Nil: List[T])((m, n) => if (f(n)) n :: m else m).reverse
}

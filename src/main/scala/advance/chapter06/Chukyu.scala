package com.github.trackiss
package advance.chapter06

object Chukyu {
  def flatten(list: List[_]): List[Any] = list match {
    case Nil => Nil
    case x :: xs =>
      x match {
        case y :: ys => (y :: flatten(ys)) ::: flatten(xs)
        case _       => x :: flatten(xs)
      }
  }
}

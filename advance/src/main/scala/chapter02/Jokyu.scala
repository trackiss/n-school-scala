package com.github.trackiss
package chapter02

object Jokyu {
  def y[A, B](f: (A => B, A) => B, a: A): B = f((yy: A) => y(f, yy), a)

  def isSorted[E](sortedSeq: Seq[E])(ordered: (E, E) => Boolean): Boolean =
    if (sortedSeq.isEmpty) true
    else
      y(
        (f: Int => Boolean, n: Int) => {
          if (n == sortedSeq.length - 1) true
          else if (!ordered(sortedSeq(n), sortedSeq(n + 1))) false
          else f(n + 1)
        },
        0
      )
}

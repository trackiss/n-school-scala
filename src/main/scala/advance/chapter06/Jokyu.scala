package com.github.trackiss
package advance.chapter06

object Jokyu {
  def y[A, B, C, D](f: ((A, B, C) => D, A, B, C) => D, a: A, b: B, c: C): D =
    f((aa: A, bb: B, cc: C) => y(f, aa, bb, cc), a, b, c)

  def split[A](n: Int, list: List[A]): (List[A], List[A]) =
    if (n < 1) (Nil, list)
    else
      y(
        (
            f: (Int, List[A], List[A]) => (List[A], List[A]),
            i: Int,
            ll: List[A],
            rl: List[A]
        ) => {
          (i, rl) match {
            case (_, Nil)     => (ll.reverse, Nil)
            case (0, list)    => (ll.reverse, rl)
            case (i, x :: xs) => f(i - 1, x :: ll, xs)
          }
        },
        n,
        Nil,
        list
      )
}

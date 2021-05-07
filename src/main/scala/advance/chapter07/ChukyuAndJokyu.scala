package com.github.trackiss
package advance.chapter07

object ChukyuAndJokyu {
  sealed trait LazyList[+A] {
    def headOption: Option[A] = this match {
      case Cons(h, _)    => Some(h())
      case EmptyLazyList => None
    }

    def tail: LazyList[A] = this match {
      case Cons(_, t)    => t()
      case EmptyLazyList => throw new Exception()
    }
  }

  final case object EmptyLazyList extends LazyList[Nothing]

  final case class Cons[+A](h: () => A, t: () => LazyList[A])
      extends LazyList[A]

  object LazyList {
    def cons[A](h: => A, t: => LazyList[A]): LazyList[A] = {
      lazy val hh = h
      lazy val tt = t

      Cons(() => hh, () => tt)
    }

    def empty[A]: LazyList[A] = EmptyLazyList
  }
}

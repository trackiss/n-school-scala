package com.github.trackiss
package advance.chapter04

object Jokyu {
  sealed trait Either[+E, +A] {
    def map[B](f: A => B): Either[E, B] = this match {
      case Right(v) => Right(f(v))
      case Left(e)  => Left(e)
    }

    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
      case Right(v) => f(v)
      case Left(e)  => Left(e)
    }
  }

  final case class Left[+E](get: E) extends Either[E, Nothing]

  final case class Right[+A](get: A) extends Either[Nothing, A]
}

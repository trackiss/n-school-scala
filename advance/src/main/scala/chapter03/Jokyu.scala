package com.github.trackiss
package chapter03

object Jokyu {
  sealed trait Option[+A] {
    def map[B](f: A => B): Option[B] = this match {
      case Some(v) => Some(f(v))
      case None    => None
    }

    def getOrElse[B >: A](default: => B): B = this match {
      case Some(v) => v
      case None    => default
    }

    def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None
  }

  final case class Some[+A](get: A) extends Option[A]
  final case object None extends Option[Nothing]
}

package com.github.trackiss
package chapter11

abstract class MyFirstCombinator {
  sealed trait ParseResult[+T]
  @SuppressWarnings(Array("org.wartremover.warts.LeakingSealed"))
  case class Success[+T](value: T, next: String) extends ParseResult[T]
  case object Failure extends ParseResult[Nothing]

  type Parser[+T] = String => ParseResult[T]

  def string(literal: String): Parser[String] = input =>
    if (input.startsWith(literal))
      Success(literal, input substring literal.length)
    else
      Failure

  /** string parser
    * @param literal 文字列
    * @return
    */
  def s(literal: String): Parser[String] = string(literal)

  def oneOf(chars: Seq[Char]): Parser[String] = input =>
    if (input.nonEmpty && chars.contains(input.head))
      Success(input.head.toString, input drop 1)
    else
      Failure

  def select[T, U >: T](left: => Parser[T], right: => Parser[U]): Parser[U] =
    input => {
      left(input) match {
        case success @ Success(_, _) => success
        case Failure                 => right(input)
      }
    }

  def combine[T, U](left: Parser[T], right: Parser[U]): Parser[(T, U)] =
    input =>
      left(input) match {
        case Success(value1, next1) =>
          right(next1) match {
            case Success(value2, next2) => Success((value1, value2), next2)
            case Failure                => Failure
          }
        case Failure => Failure
      }

  def map[T, U](parser: Parser[T], function: T => U): Parser[U] = input =>
    parser(input) match {
      case Success(value, next) => Success(function(value), next)
      case Failure              => Failure
    }

  private def y[A, B](f: (A => B, A) => B, a: A): B = f((yy: A) => y(f, yy), a)

  def rep[T](parser: Parser[T]): Parser[List[T]] = input =>
    y[String, (List[T], String)](
      (f, i) => {
        parser(i) match {
          case Success(v, n) =>
            f(n) match { case (r: List[T], nn: String) => (v :: r, nn) }
          case Failure => (Nil, i)
        }
      },
      input
    ) match {
      case (r, n) => Success(r, n)
    }
}

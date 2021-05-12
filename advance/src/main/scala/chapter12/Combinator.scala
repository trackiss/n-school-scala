package com.github.trackiss
package chapter12

abstract class Combinator {
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

  def ss(str: String): Parser[String] = s(str) <~ spacing

  def oneOf(chars: Seq[Char]): Parser[String] = input =>
    if (input.nonEmpty && chars.contains(input.head))
      Success(input.head.toString, input drop 1)
    else
      Failure

  private def y[A, B](f: (A => B, A) => B, a: A): B = f((yy: A) => y(f, yy), a)

  def rep[T](parser: => Parser[T]): Parser[List[T]] = input =>
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

  def rep1sep[T](parser: => Parser[T], sep: Parser[String]): Parser[List[T]] =
    parser ~ rep(sep ~> parser) ^^ { case (s, vs) => s :: vs }

  def success[T](value: T): Parser[T] = input => Success(value, input)

  def repsep[T](parser: => Parser[T], sep: Parser[String]): Parser[List[T]] =
    rep1sep(parser, sep) | success(List[T]())

  val floatingPointNumber: Parser[String] = input => {
    val r = """^(-?\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r
    val matchIterator = (r findAllIn input).matchData

    if (matchIterator.hasNext) {
      val next = matchIterator.next()
      val all = next group 0
      val target = next group 1

      Success(target, input substring all.length)
    } else {
      Failure
    }
  }

  val stringLiteral: Parser[String] = input => {
    val r =
      ("^\"(" + """([^"\p{Cntrl}\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*+""" + ")\"").r
    val matchIterator = (r findAllIn input).matchData

    if (matchIterator.hasNext) {
      val next = matchIterator.next()
      val all = next group 0
      val target = next group 1

      Success(target, input substring all.length)
    } else {
      Failure
    }
  }

  val spacing: Parser[String] =
    rep(oneOf(Seq(' ', '\t', '\n', '\r'))) ^^ { _.mkString }

  implicit class RichParser[T](val parser: Parser[T]) {

    /** select
      * @param right 選択を行うパーサー
      * @return
      */
    def |[U >: T](right: => Parser[U]): Parser[U] =
      input =>
        parser(input) match {
          case success @ Success(_, _) => success
          case Failure                 => right(input)
        }

    /** combine
      * @param right 逐次合成を行うパーサー
      * @tparam U パーサーの結果の型
      * @return
      */
    def ~[U](right: => Parser[U]): Parser[(T, U)] =
      input =>
        parser(input) match {
          case Success(value1, next1) =>
            right(next1) match {
              case Success(value2, next2) => Success((value1, value2), next2)
              case Failure                => Failure
            }
          case Failure => Failure
        }

    /** use left
      * @param right 右側のパーサー
      * @return パーサーの結果の型
      */
    def <~(right: => Parser[Any]): Parser[T] =
      input =>
        parser(input) match {
          case Success(value1, next1) =>
            right(next1) match {
              case Success(_, next2) => Success(value1, next2)
              case Failure           => Failure
            }
          case Failure => Failure
        }

    /** use right
      * @param right 右側のパーサー
      * @tparam U パーサーの結果の型
      * @return
      */
    def ~>[U](right: => Parser[U]): Parser[U] =
      input =>
        parser(input) match {
          case Success(value1, next1) =>
            right(next1) match {
              case Success(value2, next2) => Success(value2, next2)
              case Failure                => Failure
            }
          case Failure => Failure
        }

    /** map
      * @param fuunction 適用する関数
      * @tparam U パーサーの結果の型
      * @return
      */
    def ^^[U](function: T => U): Parser[U] =
      input =>
        parser(input) match {
          case Success(value, next) => Success(function(value), next)
          case Failure              => Failure
        }
  }
}

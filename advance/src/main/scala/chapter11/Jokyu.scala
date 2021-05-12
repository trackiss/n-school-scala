package com.github.trackiss
package chapter11

object Jokyu {
  final case class NaturalNumberArith(num: Int, terms: List[(String, Int)])

  object NaturalNumberArithParser extends MyFirstCombinator {
    def digitExcludingZero: Parser[String] = oneOf('1' to '9')

    def digit: Parser[String] = select(s("0"), digitExcludingZero)

    def naturalNumber: Parser[Int] = map(
      combine(digitExcludingZero, rep(digit)),
      { t: (String, List[String]) =>
        t match {
          case (s: String, ss: List[String]) => (s +: ss).mkString.toInt
        }
      }
    )

    def apply(input: String): ParseResult[NaturalNumberArith] = map(
      combine(
        naturalNumber,
        rep(
          select(combine(s("+"), naturalNumber), combine(s("-"), naturalNumber))
        )
      ),
      { t: (Int, List[(String, Int)]) =>
        t match {
          case (s, ss) => NaturalNumberArith(s, ss)
        }
      }
    )(input)
  }
}

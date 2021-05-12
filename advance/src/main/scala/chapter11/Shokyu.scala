package com.github.trackiss
package chapter11

object Shokyu {
  final case class FullClassName(grade: String, className: String)

  object FullClassNameParser extends MyFirstCombinator {
    def grade: Parser[String] = oneOf('1' to '3')

    def className: Parser[String] = oneOf('A' to 'D')

    def apply(input: String): ParseResult[FullClassName] = map(
      combine(combine(combine(grade, s("年")), className), s("組")),
      { t: (((String, String), String), String) =>
        t match {
          case (((g, _), cn), _) =>
            FullClassName(g, cn)
        }
      }
    )(input)
  }
}

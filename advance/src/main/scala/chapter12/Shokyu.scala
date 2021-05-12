package com.github.trackiss
package chapter12

object Shokyu {
  object ArithParser extends Combinator {
    def expr: Parser[Any] = term ~ rep(s("+") ~ term | s("-") ~ term)

    def term: Parser[Any] = factor ~ rep(s("*") ~ factor | s("/") ~ factor)

    def factor: Parser[Any] = floatingPointNumber | s("(") ~ expr ~ s(")")

    def apply(input: String): Any = expr(input)
  }
}

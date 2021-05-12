package com.github.trackiss
package chapter12

import scala.util.parsing.combinator._

object Chukyu {
  object JavaTokenParsersJSONParser extends JavaTokenParsers {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    def value: Parser[Any] =
      obj | arr | stringLiteral | floatingPointNumber ^^ {
        _.toDouble
      } | "null" ^^ { _ => null } | "true" ^^ { _ => true } | "false" ^^ { _ =>
        false
      }

    def obj: Parser[Map[String, Any]] =
      "{" ~> repsep(member, ",") <~ "}" ^^ { Map[String, Any]() ++ _ }

    def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

    def member: Parser[(String, Any)] = {
      val x = stringLiteral ~ ":" ~ value

      stringLiteral ~ ":" ~ value ^^ { case n ~ _ ~ v => (n, v) }
    }

    def apply(input: String): Any = parseAll(value, input)
  }
}

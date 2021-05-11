package com.github.trackiss
package advance.chapter13

import scala.util.parsing.combinator.JavaTokenParsers

object ChatBotTextParser extends JavaTokenParsers {
  def chatBot: Parser[ChatBot] =
    "(" ~ "chatbot" ~ commandList ~ ")" ^^ { case _ ~ _ ~ cs ~ _ =>
      ChatBot(cs)
    }

  def commandList: Parser[List[Command]] = rep(command)

  def command: Parser[Command] = replyCommand | timeCommand | setValuesCommand

  def replyCommand: Parser[ReplyCommand] =
    "(" ~ "reply" ~ string ~ replyList ~ ")" ^^ { case _ ~ _ ~ s ~ rs ~ _ =>
      ReplyCommand(s.r, rs)
    }

  def setValuesCommand: Parser[GetValueCommand] =
    "(" ~ "set-values" ~ replyListWithIndex ~ ")" ^^ { case _ ~ _ ~ is ~ _ =>
      GetValueCommand(is.toMap)
    }

  def replyList: Parser[List[String]] =
    "(" ~ rep(string) ~ ")" ^^ { case _ ~ ss ~ _ => ss }

  def replyListWithIndex: Parser[List[(Int, String)]] =
    "(" ~ rep(digitAndStringPair) ~ ")" ^^ { case _ ~ ds ~ _ => ds }

  def timeCommand: Parser[TimeCommand] =
    "(" ~ "time" ~ string ~ digits ~ digits ~ string ~ replyList ~ ")" ^^ {
      case _ ~ _ ~ r ~ s ~ e ~ z ~ rs ~ _ =>
        TimeCommand(r.r, s.toInt, e.toInt, z, rs)
    }

  def digits: Parser[String] = """[0-9]+""".r

  def string: Parser[String] = stringLiteral ^^ { s =>
    s.substring(1, s.length - 1)
  }

  def digitAndStringPair: Parser[(Int, String)] =
    "(" ~ digits ~ ":" ~ string ~ ")" ^^ { case _ ~ d ~ _ ~ s ~ _ =>
      (d.toInt, s)
    }

  def hand: Parser[String] = "rock" | "paper" | "scissor"

  def apply(input: String): ParseResult[ChatBot] = parseAll(chatBot, input)
}

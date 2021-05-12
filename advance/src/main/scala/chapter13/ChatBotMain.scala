package com.github.trackiss
package chapter13

import scala.annotation.tailrec
import scala.io.Source
import scala.io.StdIn
import scala.util.Using

@SuppressWarnings(
  Array(
    "org.wartremover.warts.PublicInference",
    "org.wartremover.warts.StringPlusAny"
  )
)
object ChatBotMain extends App {
  locally {
    val _ =
      Using(Source.fromFile("./advance/src/main/scala/chapter13/chatbot.txt")) {
        source =>
          val text = source.mkString

          val chatBot = ChatBotTextParser(text) match {
            case ChatBotTextParser.Success(result, _) => result
            case _: ChatBotTextParser.NoSuccess =>
              scala.sys.error("parse error")
          }

          println(s"chatBot: $chatBot")
          println("ChatBot booted.")

          @tailrec
          def checkInput(): Unit = {
            val input: String = StdIn.readLine(">> ") match {
              case "exit" => System.exit(0); ???
              case t @ _  => t
            }

            @tailrec
            def execute(input: String, commands: List[Command]): Unit = {
              if (
                commands.nonEmpty && !((commands.headOption getOrElse (throw new Exception)) exec input)
              ) {
                execute(input, commands drop 1)
              }
            }

            execute(input, chatBot.commands)
            checkInput()
          }

          checkInput()
      }
  }

}

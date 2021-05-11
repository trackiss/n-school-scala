package com.github.trackiss
package advance.chapter13

import java.time.{LocalDateTime, ZoneId}
import scala.util.Random
import scala.util.Random.shuffle
import scala.util.matching.Regex

sealed trait Command {
  def exec(input: String): Boolean
}

final case class ReplyCommand(regex: Regex, replies: List[String])
    extends Command {
  override def exec(input: String): Boolean = regex findFirstIn input match {
    case Some(_) =>
      shuffle(replies).headOption foreach println
      true
    case None => false
  }
}

final case class TimeCommand(
    regex: Regex,
    start: Int,
    end: Int,
    zone: String,
    replies: List[String]
) extends Command {
  override def exec(input: String): Boolean = {
    val now = LocalDateTime.now() atZone ZoneId.of(zone)
    val isInTime = start <= now.getHour && now.getHour <= end
    regex findFirstIn input match {
      case Some(_) if isInTime =>
        shuffle(replies).headOption foreach println
        true
      case _ => false
    }
  }
}

final case class GetValueCommand(indexAndStrings: Map[Int, String])
    extends Command {
  override def exec(input: String): Boolean =
    if (input startsWith "get-value")
      (input drop 9).trim.toIntOption match {
        case Some(v) =>
          println(indexAndStrings.getOrElse(v, "not found"))
          true
        case None => false
      }
    else false
}

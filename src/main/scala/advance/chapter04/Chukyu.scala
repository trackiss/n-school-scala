package com.github.trackiss
package advance.chapter04

object Chukyu {
  sealed trait Result
  case class Point(point: Int) extends Result

  sealed trait Error extends Result
  case object StudentNotFound extends Error
  case object ResultNotFound extends Error

  private[this] val results = Map(
    "taro" -> Some(90),
    "jiro" -> None
  )

  def find(name: String): Result = {
    for {
      pointOpt <- (results get name) toRight StudentNotFound
      point <- pointOpt toRight ResultNotFound
    } yield Point(point)
  }.merge

}

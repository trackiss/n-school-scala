package com.github.trackiss
package advance.chapter04

object Chukyu {
  sealed trait Result
  final case class Point(point: Int) extends Result

  sealed trait Error extends Result
  final case object StudentNotFound extends Error
  final case object ResultNotFound extends Error

  private[this] val results = Map(
    "taro" -> Some(90),
    "jiro" -> None
  )

  def find(name: String): Result = {
    //val x = (results get name) toRight StudentNotFound
    for {
      pointOpt <- ((results get name) toRight StudentNotFound)
      point <- pointOpt toRight ResultNotFound
    } yield Point(point)
  }.merge
}

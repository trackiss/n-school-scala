package com.github.trackiss
package advance.chapter04

import scala.util.Try

object Shokyu {
  def createString(size: Int): Try[String] = Try {
    require(size >= 0, "sizeはゼロ以上である必要があります")
    ((0 until size) map (_ => "a")).mkString
  }
}

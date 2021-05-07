package com.github.trackiss
package advance.chapter08

import scala.annotation.tailrec

object Chukyu {
  @tailrec
  private def power(l: Long, r: Long, acc: Long): Long =
    if (r == 0) acc else power(l, r - 1, l * acc)

  @tailrec
  private def tetration(l: Long, r: Long, acc: Long): Long =
    if (r == 0) acc else tetration(l, r - 1, power(l, acc, 1))

  implicit class LongExtension(l: Long) {
    def :^(r: Long): Long = power(l, r, 1)
    def :↑(r: Long): Long = tetration(l, r, 1)
  }
}

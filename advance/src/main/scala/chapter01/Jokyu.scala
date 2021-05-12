package com.github.trackiss
package chapter01

object Jokyu {
  def twice(f: Int => Int): Int => Int = f compose f
}

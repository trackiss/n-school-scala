package com.github.trackiss
package chapter08

object Shokyu {
  implicit class StringExtension(s: String) {
    def twice: String = s + s
  }
}

package com.github.trackiss
package advance.chapter10

object Shokyu {
  class Book(private val title: String)

  object Book {
    def unApply(book: Book): Option[String] = Some(book.title)
  }
}

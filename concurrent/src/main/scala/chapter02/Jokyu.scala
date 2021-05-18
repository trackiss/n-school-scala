package com.github.trackiss
package chapter02

import java.security.MessageDigest

object Jokyu {
  def fooBar(): Unit = (1 to 100) foreach { _ =>
    new Thread(() => println(HashDigestProvider2.digest("Hello!"))).start()
  }

  object HashDigestProvider2 {
    def digest(str: String): List[Byte] = {
      val md = MessageDigest.getInstance("SHA-1")
      md.update(str.getBytes)
      md.digest().toList
    }
  }
}

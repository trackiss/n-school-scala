package com.github.trackiss
package chapter02

import chapter02.Shokyu.AtomicLongCounter

import java.security.MessageDigest

object Chukyu {
  def fooBar(): Unit = (1 to 100) foreach { _ =>
    new Thread(() => println(HashDigestProvider1.digest("Hello!"))).start()
  }

  object HashDigestProvider1 {
    private[this] val md = MessageDigest.getInstance("SHA-1")

    def digest(str: String): List[Byte] = md.synchronized {
      md.reset()
      md.update(str.getBytes)
      md.digest().toList
    }
  }
}

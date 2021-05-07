package com.github.trackiss
package advance.chapter07

object Shokyu {
  val tribs: LazyList[Int] =
    0 #:: 0 #:: 1 #:: ((tribs lazyZip tribs.tail lazyZip tribs.tail.tail) map (
      (a, b, c) => a + b + c
    ))
}

package com.github.trackiss
package chapter07

object Shokyu {
  val tribs: LazyList[Int] =
    0 #:: 0 #:: 1 #:: ((tribs lazyZip (tribs drop 1) lazyZip (tribs drop 1 drop 1)) map (
      (a, b, c) => a + b + c
    ))
}

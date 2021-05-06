package com.github.trackiss
package advance.chapter02

object Chukyu {
  class Container[+A](n: A) {
    def put[E >: A](a: E): Container[E] = new Container(a)
    def get(): A = n
  }
}

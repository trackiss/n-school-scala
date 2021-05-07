package com.github.trackiss
package advance.chapter09

import advance.chapter09.Jokyu._

import org.scalatest.Assertions
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StackSpec extends AnyFlatSpec with Assertions with Matchers {
  it should "LIFO" in {
    val stack: Stack[Nothing] = Stack()

    val stackA = stack push 'A'
    val stackAB = stackA push 'B'

    val (b, stackA2) = stackAB.pop
    val (a, _) = stackA2.pop

    assert(a === 'A')
    assert(b === 'B')
  }

  "EmptyStack" should "throw an exception when popping" in {
    an[IllegalArgumentException] should be thrownBy Stack().pop
  }

  "isEmpty method" should "correct" in {
    val stack: Stack[Nothing] = Stack()
    val stackA = stack push 'A'
    val (_, stack2) = stackA.pop

    assert(stack.isEmpty === true)
    assert(stackA.isEmpty === false)
    assert(stack2.isEmpty === true)
  }
}

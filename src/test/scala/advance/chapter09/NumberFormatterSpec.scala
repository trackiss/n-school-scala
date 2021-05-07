package com.github.trackiss
package advance.chapter09

import org.scalatest.Assertions
import org.scalatest.flatspec.AnyFlatSpec

import advance.chapter09.Shokyu.format

class NumberFormatterSpec extends AnyFlatSpec with Assertions {
  it should "format the integers" in {
    assert(format(0) === "0")
    assert(format(1) === "1")
    assert(format(10) === "10")
    assert(format(100) === "100")
    assert(format(1000) === "1,000")
    assert(format(1000000) === "1,000,000")
  }
}

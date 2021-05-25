package com.github.trackiss
package chapter03

object Shokyu {
  def memoryVisibilityProblem1(): Unit = {
    var runner = new AsyncRunner1("Runner 0", () => true)

    for (i <- 1 to 10) {
      runner.asyncRun(name => println(s"$name is finished."))
      runner = new AsyncRunner1(s"Runner $i", runner.canNextStart)
    }

    runner.asyncRun(name => println(s"$name is finished. Totally finished."))
  }

  final class AsyncRunner1(
      private[this] val name: String,
      private[this] val canStart: () => Boolean
  ) {
    @volatile private[this] var isFinished = false

    def asyncRun(f: String => Unit): Unit = {
      new Thread(() => {
        while (!canStart()) Thread.`yield`()
        f(name)
        isFinished = true
      }).start()
    }

    def canNextStart: () => Boolean = () => this.isFinished
  }
}

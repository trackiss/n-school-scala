package com.github.trackiss
package chapter06

import java.util.concurrent.{ForkJoinPool, RecursiveTask}
import scala.util.Random

object Jokyu {
  def forkJoinMergeSort(): Unit = {
    val length = 100
    val randomList = (for (_ <- 1 to length) yield Random.nextInt(100)).toList

    println(randomList)

    val fj = new ForkJoinPool()

    val sortedList = fj.invoke(new MergeSort(randomList))

    println(sortedList)
  }

  final class MergeSort(list: List[Int]) extends RecursiveTask[List[Int]] {
    override def compute(): List[Int] = list match {
      case Nil       => Nil
      case xs :: Nil => List(xs)
      case _ =>
        val (l, r) = list splitAt list.length / 2
        new Merge(
          new MergeSort(l).fork().join(),
          new MergeSort(r).fork().join()
        ).fork().join()
    }
  }

  final class Merge(left: List[Int], right: List[Int])
      extends RecursiveTask[List[Int]] {
    override def compute(): List[Int] = (left, right) match {
      case (_, Nil) => left
      case (Nil, _) => right
      case (l :: ls, r :: rs) =>
        if (l < r)
          l :: new Merge(ls, right).fork().join()
        else
          r :: new Merge(left, rs).fork().join()
    }
  }
}

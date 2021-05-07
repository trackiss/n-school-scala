package com.github.trackiss
package advance.chapter10

import scala.reflect.runtime.universe._

object Chukyu {
  class Issue(private val title: String) {
    private def printTitle(): Unit = println(title)
  }

  def reflectionChallenge(): Unit = {
    val issue = new Issue("不具合1")

    val mirror = runtimeMirror(issue.getClass.getClassLoader)
    val instanceMirror = mirror reflect issue
    val printTitle = typeTag[Issue].tpe.decl(TermName("printTitle")).asMethod

    val _ = (instanceMirror reflectMethod printTitle).apply()
  }
}

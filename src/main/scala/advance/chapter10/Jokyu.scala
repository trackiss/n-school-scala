package com.github.trackiss
package advance.chapter10

import scala.reflect.macros.blackbox

object Jokyu {
  def __FILE__ : String = macro fileLocationImpl

  def fileLocationImpl(c: blackbox.Context): c.Expr[String] = {
    import c.universe._

    c.Expr[String](Literal(Constant(c.enclosingPosition.source.file.name)))
  }

  def __LINE__ : Int = macro lineLocationImpl

  def lineLocationImpl(c: blackbox.Context): c.Expr[Int] = {
    import c.universe._

    c.Expr[Int](Literal(Constant(c.enclosingPosition.line)))
  }
}

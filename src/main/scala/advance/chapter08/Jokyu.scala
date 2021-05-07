package com.github.trackiss
package advance.chapter08

object Jokyu {
  trait Additive[A] {
    def plus(a: A, b: A): A
    def zero: A
  }

  def sum[A](lst: List[A])(implicit m: Additive[A]): A =
    lst.foldLeft(m.zero)((x, y) => m.plus(x, y))

  final case class Point3D(x: Double, y: Double, z: Double)

  object Point3D {
    implicit object Point3DAdditive extends Additive[Point3D] {
      def plus(a: Point3D, b: Point3D): Point3D =
        Point3D(a.x + b.x, a.y + b.y, a.z + b.z)

      def zero: Point3D = Point3D(0, 0, 0)
    }
  }
}

package slack3d.algebra


import spire.math.Numeric

import scala.reflect.ClassTag

sealed trait Isometry3[A] {

  def isometries(): Array[Isometry3.Single[A]]

  def *(vector: Vector3[A]): Vector3[A]

  def *(isometry: Isometry3[A]): Isometry3.Many[A]

//  def perspectiveMultiply(vector: Vector3[A])(implicit num: Numeric[A]): Vector4[A] =
//    this.*(vector).toVector4(num.negate(vector.z))

}

object Isometry3 {

  def apply[A](rotation: Matrix3[A], translation: Vector3[A]): Single[A] =
    new Single[A](rotation, translation)

  def identity[A: ClassTag]()(implicit num: Numeric[A]): Isometry3[A] =
    Single(
      Matrix3.identity[A](),
      Vector3.zeroes[A]()
    )

  case object Single {

    def apply[A](rotation: Matrix3[A], translation: Vector3[A]): Single[A] =
      new Single[A](rotation, translation)

  }

  case class Single[A] private[Isometry3](rotation: Matrix3[A],
                                          translation: Vector3[A]) extends Isometry3[A] {

    def *(vector: Vector3[A]): Vector3[A] =
      (rotation * vector) + translation

    override def isometries(): Array[Single[A]] =
      Array(this)

    override def *(isometry: Isometry3[A]): Isometry3.Many[A] =
      Many(this +: isometry.isometries())
  }

  case class Many[A] private(isometries: Array[Isometry3.Single[A]]) extends Isometry3[A] {
    override def *(vector: Vector3[A]): Vector3[A] =
      isometries.foldLeft(vector) {
        case (vector, iso) =>
          iso * vector
      }

    override def *(isometry: Isometry3[A]): Many[A] =
      Many(this.isometries ++ isometry.isometries())
  }
}

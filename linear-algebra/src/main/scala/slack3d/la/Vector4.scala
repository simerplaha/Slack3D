package slack3d.la

import slack3d.la.util.{ArrayUtil, Maths}
import spire.algebra.Trig
import spire.implicits._
import spire.math._
import slack3d.la.util.NumberUtils._

import scala.reflect.ClassTag

object Vector4 {

  def apply[A: ClassTag](array: Array[A])(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = array(0),
      y = array(1),
      z = array(2),
      w = array(3)
    )

  def fill[A: ClassTag](value: A)(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = value,
      y = value,
      z = value,
      w = value
    )

  def basis[A: ClassTag]()(implicit num: Numeric[A]): Seq[Vector4[A]] =
    Seq(
      Vector4.unitX[A](),
      Vector4.unitX[A]().negate(),
      Vector4.unitY[A](),
      Vector4.unitY[A]().negate(),
      Vector4.unitZ[A](),
      Vector4.unitZ[A]().negate(),
    )

  def unit[A: ClassTag](homogenous: A)(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.one,
      y = num.one,
      z = num.one,
      w = homogenous
    )

  def unitX[A: ClassTag]()(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.one,
      y = num.zero,
      z = num.zero,
      w = num.zero
    )

  def unitY[A: ClassTag]()(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.zero,
      y = num.one,
      z = num.zero,
      w = num.zero
    )

  def unitZ[A: ClassTag]()(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.zero,
      y = num.zero,
      z = num.one,
      w = num.zero
    )

  def zero[A: ClassTag]()(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.zero,
      y = num.zero,
      z = num.zero,
      w = num.zero
    )

  def origin[A: ClassTag]()(implicit num: Numeric[A]): Vector4[A] =
    Vector4(
      x = num.zero,
      y = num.zero,
      z = num.zero,
      w = num.one
    )

  def apply[A: ClassTag](x: A,
                         y: A,
                         z: A)(implicit num: Numeric[A]) =
    new Vector4[A](
      x = x,
      y = y,
      z = z,
      w = num.one
    )

  implicit class Vector4Implicits[A](scalar: A) {
    def *(vec: Vector4[A]): Vector4[A] =
      vec * scalar

    def +(vec: Vector4[A]): Vector4[A] =
      vec + scalar

    def -(vec: Vector4[A]): Vector4[A] =
      vec - scalar

    def /(vec: Vector4[A]): Vector4[A] =
      vec / scalar
  }

}

case class Vector4[A: ClassTag](x: A,
                                y: A,
                                z: A,
                                w: A)(implicit num: Numeric[A]) {

  def apply(index: Int) =
    if (index == 0)
      x
    else if (index == 1)
      y
    else if (index == 2)
      z
    else if (index == 3)
      w
    else
      throw new Exception(s"${classOf[Vector3[A]].getSimpleName}: invalid index $index")

  def -(vec: Vector4[A]): Vector4[A] =
    Vector4(
      x = this.x - vec.x,
      y = this.y - vec.y,
      z = this.z - vec.z,
      w = this.w
    )

  def -(vec: Vector3[A]): Vector4[A] =
    Vector4(
      x = this.x - vec.x,
      y = this.y - vec.y,
      z = this.z - vec.z,
      w = this.w
    )

  def +(scalar: A): Vector4[A] =
    Vector4(
      x = this.x + scalar,
      y = this.y + scalar,
      z = this.z + scalar,
      w = this.w
    )

  def -(scalar: A): Vector4[A] =
    Vector4(
      x = this.x - scalar,
      y = this.y - scalar,
      z = this.z - scalar,
      w = this.w
    )

  def +(vec: Vector4[A]): Vector4[A] =
    Vector4(
      x = this.x + vec.x,
      y = this.y + vec.y,
      z = this.z + vec.z,
      w = this.w
    )

  def +(vec: Vector3[A]): Vector4[A] =
    Vector4(
      x = this.x + vec.x,
      y = this.y + vec.y,
      z = this.z + vec.z,
      w = this.w
    )

  def *(vec: Vector3[A]): Vector4[A] =
    Vector4(
      x = this.x * vec.x,
      y = this.y * vec.y,
      z = this.z * vec.z,
      w = this.w
    )

  def *(vec: Vector4[A]): Vector4[A] =
    Vector4(
      x = this.x * vec.x,
      y = this.y * vec.y,
      z = this.z * vec.z,
      w = this.w * vec.w
    )

  def *(scalar: A): Vector4[A] =
    Vector4(
      x = this.x * scalar,
      y = this.y * scalar,
      z = this.z * scalar,
      w = this.w
    )

  def /(scalar: A): Vector4[A] =
    Vector4(
      x = this.x / scalar,
      y = this.y / scalar,
      z = this.z / scalar,
      w = this.w
    )

  def /(vec: Vector3[A]): Vector4[A] =
    Vector4(
      x = this.x / vec.x,
      y = this.y / vec.y,
      z = this.z / vec.z,
      w = this.w
    )

  def cross(vec: Vector4[A]): Vector4[A] =
    Vector4(
      x = (this.y * vec.z) - (this.z * vec.y),
      y = (this.z * vec.x) - (this.x * vec.z),
      z = (this.x * vec.y) - (this.y * vec.x),
      w = this.w
    )

  def cross(vec: Vector3[A]): Vector4[A] =
    Vector4(
      x = (this.y * vec.z) - (this.z * vec.y),
      y = (this.z * vec.x) - (this.x * vec.z),
      z = (this.x * vec.y) - (this.y * vec.x),
      w = this.w
    )

  def reflect(normal: Vector3[A]): Vector4[A] =
    this.toVector3().reflect(normal).toVector4(this.w)

  def project(normal: Vector4[A]): Vector4[A] =
    this.toVector3().project(normal.toVector3()).toVector4(this.w)

  def negate(): Vector4[A] =
    this * -1

  def doPerspectiveDivision(): Vector4[A] =
    Vector4(
      x = this.x / this.w,
      y = this.y / this.w,
      z = this.z / this.w,
      w = num.one
    )

  def dot(vec: Vector4[A]): A =
    this.x * vec.x + this.y * vec.y + this.z * vec.z

  def dot(vec: Vector3[A]): A =
    this.x * vec.x + this.y * vec.y + this.z * vec.z

  //  def dotW(vec: Vector3[A]): A =
  //    this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w

  //a.b = |a| |b| cosQ
  def angleRadian(vec: Vector4[A])(implicit trig: Trig[A]): A =
    trig.acos(this.dot(vec) / (this.length() * vec.length()))

  @inline def angleDegrees(vec: Vector4[A])(implicit trig: Trig[A]): A =
    trig.toDegrees(angleRadian(vec))

  def lengthSquared(): A =
    this dot this

  def length(): A =
    num.sqrt(this.lengthSquared())

  def normalise() =
    this.toVector3().normalise().toVector4(this.w)

  def min(that: Vector4[A]): Vector4[A] =
    if (this.lengthSquared() < that.lengthSquared())
      this
    else
      that

  def max(that: Vector4[A]): Vector4[A] =
    if (this.lengthSquared() > that.lengthSquared())
      this
    else
      that

  def center(other: Vector4[A]): Vector4[A] =
    (this + other) / (num.one + num.one)

  def distance(other: Vector4[A]): A =
    (this - other).length()

  def center(other: Vector3[A]): Vector4[A] =
    (this + other) / (num.one + num.one)

  def distance(other: Vector3[A]): A =
    (this - other).length()

  def is_zero() =
    isOrigin()

  def isOrigin(): Boolean =
    this.x == num.zero && this.y == num.zero && this.z == num.zero

  def isNotOrigin(): Boolean =
    !isOrigin()

  def toArray(): Array[A] =
    Array(x, y, z, w)

  def toVector3(): Vector3[A] =
    Vector3(x, y, z)

  def round(scale: Int) =
    Vector4(
      x = num.fromBigDecimal(Maths.round(x, scale)),
      y = num.fromBigDecimal(Maths.round(y, scale)),
      z = num.fromBigDecimal(Maths.round(z, scale)),
      w = num.fromBigDecimal(Maths.round(w, scale))
    )

  def infimum(other: Vector4[A]): Vector4[A] =
    Vector4(
      this.x min other.x,
      this.y min other.y,
      this.z min other.z,
      this.w min other.w
    )

  def supremum(other: Vector4[A]): Vector4[A] =
    Vector4(
      this.x max other.x,
      this.y max other.y,
      this.z max other.z,
      this.w max other.w
    )

  def infimum(other: Vector3[A]): Vector4[A] =
    Vector4(
      this.x min other.x,
      this.y min other.y,
      this.z min other.z,
      this.w
    )

  def supremum(other: Vector3[A]): Vector4[A] =
    Vector4(
      this.x max other.x,
      this.y max other.y,
      this.z max other.z,
      this.w
    )

  def smoothStep(v: Vector4[A], t: A) = {
    val t2 = t * t
    val t3 = t2 * t

    val three = num.fromDouble(3.0d)

    val _x = (x + x - v.x - v.x) * t3 + (three * v.x - three * x) * t2 + x * t + x
    val _y = (y + y - v.y - v.y) * t3 + (three * v.y - three * y) * t2 + y * t + y
    val _z = (z + z - v.z - v.z) * t3 + (three * v.z - three * z) * t2 + z * t + z
    val _w = (w + w - v.w - v.w) * t3 + (three * v.w - three * w) * t2 + w * t + w

    Vector4(
      x = _x,
      y = _y,
      z = _z,
      w = _w
    )
  }

  def sum(): A =
    x + y + z + w

  def toTuple(): (A, A, A, A) =
    (x, y, z, w)

  def toTupleRoundedString(scale: Int = 2): String = {
    val xRounded = x.roundNumToString(scale)
    val yRounded = y.roundNumToString(scale)
    val zRounded = z.roundNumToString(scale)
    val wRounded = w.roundNumToString(scale)
    (xRounded, yRounded, zRounded, wRounded).toString()
  }

  def toTuple2(): (A, A) =
    (x, y)

  def toTuple3(): (A, A, A) =
    (x, y, z)

  /**
   * If z is 0 then it returns tuple 2
   */
  def toTuple3Or2String(): String =
    if (z == num.zero)
      toTuple2().toString()
    else
      toTuple3().toString()

  def toTuple3Or2StringRounded(scale: Int = 2): String = {
    val xRounded = x.roundNumToString(scale)
    val yRounded = y.roundNumToString(scale)
    val zRounded = z.roundNumToString(scale)

    if (zRounded == num.zero.toString)
      (xRounded, yRounded).toString()
    else
      (xRounded, yRounded, zRounded).toString()
  }

  override def toString: String =
    ArrayUtil.toString(Array(toArray()))
}

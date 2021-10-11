package slack3d.la

import slack3d.la.util.{ArrayUtil, Maths}
import slack3d.la.util.NumberUtils._
import spire.algebra.Trig
import spire.implicits._
import spire.math._

import scala.reflect.ClassTag

object Vector3 {

  def standardBasis[A: ClassTag]()(implicit num: Numeric[A]): Seq[Vector3[A]] =
    Seq(
      Vector3.unitX[A](),
      Vector3.unitX[A]().negate(),
      Vector3.unitY[A](),
      Vector3.unitY[A]().negate(),
      Vector3.unitZ[A](),
      Vector3.unitZ[A]().negate(),
    )

  def apply[A: ClassTag](array: Array[A])(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = array(0),
      y = array(1),
      z = array(2)
    )

  def apply[A](x: A)(implicit num: Numeric[A],
                     classTag: ClassTag[A]): Vector3[A] =
    Vector3(
      x = x,
      y = num.zero,
      z = num.zero
    )

  def apply[A: ClassTag](x: A, y: A)(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = x,
      y = y,
      z = num.zero
    )

  def unit[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = num.one,
      y = num.one,
      z = num.one
    )

  def unitX[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = num.one,
      y = num.zero,
      z = num.zero
    )

  def unitY[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = num.zero,
      y = num.one,
      z = num.zero
    )

  def unitZ[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    Vector3(
      x = num.zero,
      y = num.zero,
      z = num.one
    )

  def zeroes[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    origin[A]()

  def origin[A: ClassTag]()(implicit num: Numeric[A]): Vector3[A] =
    Vector3[A](
      x = num.zero,
      y = num.zero,
      z = num.zero
    )

  def fill[A: ClassTag](component: A)(implicit num: Numeric[A]): Vector3[A] =
    Vector3[A](
      x = component,
      y = component,
      z = component
    )

  def nearest[A](to: Vector3[A],
                 from: Iterable[Vector3[A]])(implicit num: Numeric[A]): Option[(Vector3[A], A)] =
    from.foldLeft(Option.empty[(Vector3[A], A)]) {
      case (currentNearest, from) =>
        currentNearest match {
          case Some((_, currentNearestLength)) =>
            val distance = (from - to).length()
            if (distance < currentNearestLength)
              Some((from, distance))
            else
              currentNearest

          case None =>
            Some((to, to.length()))
        }
    }

  def furthest[A](to: Vector3[A],
                  from: Iterable[Vector3[A]])(implicit num: Numeric[A]): Option[(Vector3[A], A)] =
    from.foldLeft(Option.empty[(Vector3[A], A)]) {
      case (currentFurthest, from) =>
        currentFurthest match {
          case Some((_, currentFurthestLength)) =>
            val distance = (from - to).length()
            if (distance > currentFurthestLength)
              Some((from, distance))
            else
              currentFurthest

          case None =>
            Some((to, to.length()))
        }
    }

  implicit class Vector3Implicits[A](scalar: A) {
    def *(vec: Vector3[A]): Vector3[A] =
      vec * scalar

    def +(vec: Vector3[A]): Vector3[A] =
      vec + scalar

    def /(vec: Vector3[A]): Vector3[A] =
      vec / scalar
  }
}

case class Vector3[A: ClassTag](x: A,
                                y: A,
                                z: A)(implicit num: Numeric[A]) {

  //  def perpendicular(): Vector3[A] =
  //    Vector3(
  //      x = this.y,
  //      y = -this.x
  //    )

  def apply(index: Int) =
    if (index == 0)
      x
    else if (index == 1)
      y
    else if (index == 2)
      z
    else
      throw new Exception(s"${classOf[Vector3[A]].getSimpleName}: invalid index $index")

  def -(vec: Vector3[A]): Vector3[A] =
    Vector3(
      x = this.x - vec.x,
      y = this.y - vec.y,
      z = this.z - vec.z
    )

  def -(vec: Vector4[A]): Vector3[A] =
    Vector3(
      x = this.x - vec.x,
      y = this.y - vec.y,
      z = this.z - vec.z
    )

  def +(scalar: A): Vector3[A] =
    Vector3(
      x = this.x + scalar,
      y = this.y + scalar,
      z = this.z + scalar
    )

  def -(scalar: A): Vector3[A] =
    Vector3(
      x = this.x - scalar,
      y = this.y - scalar,
      z = this.z - scalar
    )

  def +(vec: Vector3[A]): Vector3[A] =
    Vector3(
      x = this.x + vec.x,
      y = this.y + vec.y,
      z = this.z + vec.z
    )

  def +(vec: Vector4[A]): Vector3[A] =
    Vector3(
      x = this.x + vec.x,
      y = this.y + vec.y,
      z = this.z + vec.z
    )

  def *(scalar: A): Vector3[A] =
    Vector3(
      x = this.x * scalar,
      y = this.y * scalar,
      z = this.z * scalar
    )

  def *(vec: Vector3[A]): Vector3[A] =
    Vector3(
      x = this.x * vec.x,
      y = this.y * vec.y,
      z = this.z * vec.z
    )

  def /(scalar: A): Vector3[A] =
    Vector3(
      x = this.x / scalar,
      y = this.y / scalar,
      z = this.z / scalar
    )

  def /(vec: Vector3[A]): Vector3[A] =
    Vector3(
      x = this.x / vec.x,
      y = this.y / vec.y,
      z = this.z / vec.z
    )

  def cross(vec: Vector3[A]): Vector3[A] =
    Vector3(
      x = (this.y * vec.z) - (this.z * vec.y),
      y = (this.z * vec.x) - (this.x * vec.z),
      z = (this.x * vec.y) - (this.y * vec.x)
    )

  //v1.v2 = ||a|| ||b|| cosθ
  def angleRadian(vec: Vector3[A])(implicit trig: Trig[A]): A =
    trig.acos(this.dot(vec) / (this.length() * vec.length()))

  def angleDegrees(vec: Vector3[A])(implicit trig: Trig[A]): A =
    trig.toDegrees(angleRadian(vec))

  def reflect(normal: Vector3[A]): Vector3[A] =
    (project(normal) * 2) - this

  def reflectOpenGL(normal: Vector3[A]): Vector3[A] =
    this - (normal * ((2: A) * normal.dot(this)))

  def project(normal: Vector3[A]): Vector3[A] =
    normal * this.dot(normal)

  def normalise(): Vector3[A] =
    this / this.length()

  def dot(vec: Vector3[A]): A =
    this.x * vec.x + this.y * vec.y + this.z * vec.z

  def dot(vec: Vector4[A]): A =
    this.x * vec.x + this.y * vec.y + this.z * vec.z

  def lengthSquared(): A =
    this dot this

  def length(): A =
    sqrt(this.lengthSquared())

  def negate(): Vector3[A] =
    this * -1

  def minLength(that: Vector3[A]): Vector3[A] =
    if (this.lengthSquared() < that.lengthSquared())
      this
    else
      that

  def maxLength(that: Vector3[A]): Vector3[A] =
    if (this.lengthSquared() > that.lengthSquared())
      this
    else
      that

  def map(component: A => A): Vector3[A] =
    Vector3(
      x = component(x),
      y = component(y),
      z = component(z)
    )

  def sum(): A =
    x + y + z

  def minComponent(other: Vector3[A]): Vector3[A] =
    Vector3(
      this.x min other.x,
      this.y min other.y,
      this.z min other.z
    )

  def maxComponent(other: Vector3[A]): Vector3[A] =
    Vector3(
      this.x max other.x,
      this.y max other.y,
      this.z max other.z
    )

  def nearest(from: Iterable[Vector3[A]]): Option[(Vector3[A], A)] =
    Vector3.nearest(this, from)

  def furthest(from: Iterable[Vector3[A]]): Option[(Vector3[A], A)] =
    Vector3.furthest(this, from)

  def isOrigin(): Boolean =
    this.x == num.zero && this.y == num.zero && this.z == num.zero

  def isZero() =
    isOrigin()

  def is_not_origin(): Boolean =
    !isOrigin()

  /**
   * Check if a vector equals is almost equal to another one.
   */
  def almostEquals(vector: Vector3[A], precision: A): Boolean =
    if (Maths.abs(this.x - vector.x) > precision ||
      Maths.abs(this.y - vector.y) > precision ||
      Maths.abs(this.z - vector.z) > precision) {
      false
    } else {
      true
    }

  /**
   * Check if a vector is almost zero
   */
  def almostZero(precision: A): Boolean =
    if (Maths.abs(this.x) > precision || Maths.abs(this.y) > precision || Maths.abs(this.z) > precision) {
      false
    } else {
      true
    }

  /**
   * Check if the vector is anti-parallel to another vector.
   *
   * @param precision Set to zero for exact comparisons
   */
  def isAntiParallelTo(vector: Vector3[A], precision: A): Boolean =
    this.negate().almostEquals(vector, precision)

  def toArray(): Array[A] =
    Array(x, y, z)

  def abs(): Vector3[A] =
    Vector3(
      x = Maths.abs(x),
      y = Maths.abs(y),
      z = Maths.abs(z)
    )

  def lerp(a: Vector3[A], t: A): Vector3[A] =
    this + ((a - this) * t)

  def toTuple2(): (A, A) =
    (x, y)

  def toTuple3(): (A, A, A) =
    (x, y, z)

  def center(other: Vector3[A]) =
    (this + other) / (num.one + num.one)

  def distance(other: Vector3[A]) =
    (this - other).length()

  def <(right: Vector3[A])(implicit ordering: Ordering[A]): Boolean =
    this.x < right.x && this.y < right.y && this.z < right.z

  def >(right: Vector3[A])(implicit ordering: Ordering[A]): Boolean =
    this.x > right.x && this.y > right.y && this.z > right.z

  def <=(right: Vector3[A])(implicit ordering: Ordering[A]): Boolean =
    this.x <= right.x && this.y <= right.y && this.z <= right.z

  def >=(right: Vector3[A])(implicit ordering: Ordering[A]): Boolean =
    this.x >= right.x && this.y >= right.y && this.z >= right.z

  //Computes the index of the vector component with the smallest absolute value.
  def iamin()(implicit ordering: Ordering[A]): Int =
    this.toArray().zipWithIndex.minBy {
      case (value, _) =>
        num.abs(value)
    }._2

  def toVector4(w: A): Vector4[A] =
    Vector4(
      x = x,
      y = y,
      z = z,
      w = w
    )

  def round(scale: Int) =
    Vector3(
      x = num.fromBigDecimal(Maths.round(x, scale)),
      y = num.fromBigDecimal(Maths.round(y, scale)),
      z = num.fromBigDecimal(Maths.round(z, scale))
    )

  def toDouble(): Vector3[Double] =
    Vector3(
      x.toDouble(),
      y.toDouble(),
      z.toDouble()
    )

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

/*
 * Copyright 2021 Simer JS Plaha (simer.j@gmail.com - @simerplaha)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package slack3d.algebra

import slack3d.algebra.util.{ArrayUtil, Maths}
import spire.algebra.Trig
import spire.implicits._
import spire.math._

import scala.reflect.ClassTag

object Vector2 {

  def origin[A: ClassTag : Numeric](): Vector2[A] =
    Vector2[A](0, 0)

  def one[A: ClassTag : Numeric](): Vector2[A] =
    Vector2[A](1, 1)

}

case class Vector2[A](x: A,
                      y: A)(implicit classTag: ClassTag[A],
                            num: Numeric[A]) {

  def perpendicularClockwise(): Vector2[A] =
    Vector2(
      x = this.y,
      y = -this.x
    )

  def perpendicularCounterClockwise(): Vector2[A] =
    Vector2(
      x = -this.y,
      y = this.x
    )

  def -(right: Vector2[A]): Vector2[A] =
    Vector2(
      x = this.x - right.x,
      y = this.y - right.y
    )

  def +(right: Vector2[A]) =
    Vector2[A](
      x = this.x + right.x,
      y = this.y + right.y
    )

  def *(scalar: A): Vector2[A] =
    Vector2(
      x = this.x * scalar,
      y = this.y * scalar
    )

  def *(vec: Vector2[A]): Vector2[A] =
    Vector2(
      x = this.x * vec.x,
      y = this.y * vec.y
    )

  def /(scalar: A): Vector2[A] =
    Vector2(
      x = this.x / scalar,
      y = this.y / scalar
    )

  def /(vec: Vector2[A]): Vector2[A] =
    Vector2(
      x = this.x / vec.x,
      y = this.y / vec.y
    )

  def dot(vec: Vector2[A]): A =
    this.x * vec.x + this.y * vec.y

  //v1.v2 = ||a|| ||b|| cosθ
  def angleRadian(vec: Vector2[A])(implicit trig: Trig[A]): A = {
    val dot = this.dot(vec)
    val denominator = this.length() * vec.length()
    val right = dot / denominator
    trig.acos(right)
  }

  def angleDegree(vec: Vector2[A])(implicit trig: Trig[A]): A =
    trig.toDegrees(angleRadian(vec))

  def lengthSquared(): A =
    this.x * this.x + this.y * this.y

  def length(): A =
    spire.math.sqrt(lengthSquared())

  def distanceSquared(vec: Vector2[A]): A =
    (this - vec).lengthSquared()

  def distance(vec: Vector2[A]): A =
    (this - vec).length()

  def normalise(): Vector2[A] =
    this / this.length()

  def negate(): Vector2[A] =
    Vector2[A](
      x = -this.x,
      y = -this.y
    )

  def *(mat: Matrix2[A]): Vector2[A] = {
    val x = mat._00 * this.x + mat._10 * this.y
    val y = mat._01 * this.x + mat._11 * this.y
    Vector2(x, y)
  }


  def lerp(other: Vector2[A], factor: A) = {
    val x = this.x + (other.x - this.x) * factor
    val y = this.y + (other.y - this.y) * factor
    Vector2(x, y)
  }

  def round(): Vector2[A] =
    Vector2(
      x = spire.math.round(this.x),
      y = spire.math.round(this.y)
    )

  def sum(): A =
    x + y

  def absolute(): Vector2[A] =
    Vector2(
      x = spire.math.abs(this.x),
      y = spire.math.abs(this.y)
    )

  def isOrigin(): Boolean =
    this.x == num.zero && this.y == num.zero

  def isNotOrigin(): Boolean =
    !isOrigin()

  def round(scale: Int) =
    Vector2(
      x = num.fromBigDecimal(Maths.round(x, scale)),
      y = num.fromBigDecimal(Maths.round(y, scale))
    )

  def toTuple(): (A, A) =
    (x, y)

  def toArray(): Array[A] =
    Array(x, y)

  override def toString: String =
    ArrayUtil.toString(Array(toArray()))
}

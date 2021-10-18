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

import slack3d.algebra.util.ArrayUtil
import spire.algebra.Trig
import spire.implicits._
import spire.math.Numeric

import scala.reflect.ClassTag

object Matrix2 {

  //@formatter:off
  def identity[A: ClassTag]()(implicit num: Numeric[A]): Matrix2[A] =
    Matrix2[A](
      num.one,  num.zero,
      num.zero, num.one
    )

  def rotator[A: ClassTag](angle: A)(implicit trig: Trig[A],
                                     num: Numeric[A]): Matrix2[A] = {

    val radian = trig.toRadians(angle)

    val sine =   trig.sin(radian)
    val cosine = trig.cos(radian)

    Matrix2(
      cosine, -sine,
      sine,   cosine
    )
  }

  def zero[A: ClassTag](implicit num: Numeric[A]) =
    Matrix2[A](
      num.zero, num.zero,
      num.zero, num.zero
    )

  def scaling[A: ClassTag](factor: A)(implicit num: Numeric[A]) =
    Matrix2[A](
      factor,    num.zero,
      num.zero,  factor
    )

  def scaling[A: ClassTag](vec: Vector2[A])(implicit num: Numeric[A]) =
    Matrix2[A](
      vec.x,     num.zero,
      num.zero,  vec.y
    )

  def scaling[A: ClassTag](x: A, y: A)(implicit num: Numeric[A]) =
    Matrix2[A](
      x,           num.zero,
      num.zero,    y
    )
  //@formatter:on

}

case class Matrix2[A: ClassTag](_00: A, _01: A,
                                _10: A, _11: A)(implicit num: Numeric[A]) {

  def row1 = Vector2(_00, _01)

  def row2 = Vector2(_10, _11)

  //@formatter:off
  def +(mat: Matrix2[A]): Matrix2[A] =
    Matrix2(
      _00 + mat._00,     _10 + mat._10,
      _01 + mat._01,     _11 + mat._11
    )
  //@formatter:on

  //@formatter:off
  def -(mat: Matrix2[A]): Matrix2[A] =
    Matrix2(
      _00 - mat._00,     _10 - mat._10,
      _01 - mat._01,     _11 - mat._11
    )
  //@formatter:on

  //@formatter:off
  def *(scalar: A): Matrix2[A] =
    Matrix2(
      _00 * scalar,     _10 * scalar,
      _01 * scalar,     _11 * scalar
    )
  //@formatter:on

  def *(mat: Matrix2[A]): Matrix2[A] = {
    val new_m00 = _00 * mat._00 + _10 * mat._01
    val new_m01 = _00 * mat._10 + _10 * mat._11
    val new_m10 = _01 * mat._00 + _11 * mat._01
    val new_m11 = _01 * mat._10 + _11 * mat._11

    Matrix2(
      new_m00, new_m01,
      new_m10, new_m11
    )
  }

  def *(vec: Vector2[A]) = {
    val x = _00 * vec.x + _10 * vec.y
    val y = _01 * vec.x + _11 * vec.y

    Vector2(
      x = x,
      y = y
    )
  }

  def determinant(): A =
    _00 * _11 - _01 * _10

  def invert(): Matrix2[A] = {
    val deter = determinant()

    //@formatter:off
    Matrix2[A](
      _11 / deter,    -_10 / deter,
      -_01 / deter,   _00 / deter
    )
    //@formatter:on
  }

  def transpose(): Matrix2[A] =
    Matrix2[A](
      _00, _01,
      _10, _11
    )

  def scale(scalar: Vector2[A]): Matrix2[A] =
    scale(scalar.x, scalar.y)

  def scale(x: A, y: A): Matrix2[A] =
    Matrix2[A](
      _00 * x, _10 * x,
      _01 * y, _11 * y
    )

  def rotate(angle: A)(implicit trig: Trig[A]): Matrix2[A] =
    Matrix2.rotator(angle) * this

  def transform(vec: Vector2[A]): Vector2[A] =
    this * vec

  def normal(): Matrix2[A] =
    invert().transpose()

  def negate() =
    this * -1

  //@formatter:off
  def lerp(other: Matrix2[A], factor: A) =
    Matrix2[A](
      other._00 - this._00 * factor + this._00,      other._10 - this._10 * factor + this._10,
      other._01 - this._01 * factor + this._01,      other._11 - this._11 * factor + this._11
    )
  //@formatter:on

  def solveCramer(result: Vector2[A]): Vector2[A] = {
    val determinant =
      this.determinant()

    if (determinant == num.zero)
      throw new Exception(s"Cannot resolve equation. determinant = $determinant")

    val xMatrix =
      this.copy(
        _00 = result.x,
        _10 = result.y
      )

    val yMatrix =
      this.copy(
        _01 = result.x,
        _11 = result.y
      )

    val xDeterminant = xMatrix.determinant()
    val yDeterminant = yMatrix.determinant()

    Vector2(
      x = xDeterminant / determinant,
      y = yDeterminant / determinant
    )
  }

  def toMatrix3(fill: A): Matrix3[A] =
    Matrix3(
      _00, _10, fill,
      _01, _11, fill,
      fill, fill, fill
    )

  def toArray(): Array[A] =
    Array(
      _00: A, _10: A,
      _01: A, _11: A
    )

  def toRowArray(): Array[Array[A]] =
    Array(
      Array(_00: A, _10: A),
      Array(_01: A, _11: A)
    )

  override def toString: String =
    ArrayUtil.toString(transpose().toRowArray())

}

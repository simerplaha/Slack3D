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

object Matrix3 {

  //@formatter:off
  def identity[A: ClassTag]()(implicit num: Numeric[A]) =
    Matrix3[A](
      num.one,  num.zero, num.zero,
      num.zero, num.one,  num.zero,
      num.zero, num.zero, num.one
    )

  def zeroes[A: ClassTag]()(implicit num: Numeric[A]): Matrix3[A] =
    Matrix3[A](
      num.zero, num.zero, num.zero,
      num.zero, num.zero, num.zero,
      num.zero, num.zero, num.zero
    )

  def translator[A: ClassTag](vec: Vector2[A])(implicit num: Numeric[A]) =
    Matrix3[A](
      num.one,  num.zero, vec.x,
      num.zero, num.one,  vec.y,
      num.zero, num.zero, num.one
    )

  def scalar[A: ClassTag](factor: A)(implicit num: Numeric[A]) =
    Matrix3[A](
      factor,   num.zero, num.zero,
      num.zero, factor,   num.zero,
      num.zero, num.zero, factor
    )

  def rotatorX[A: ClassTag](angle: A)(implicit num: Numeric[A], trig: Trig[A]) = {
    val cosine = trig.cos(angle)
    val sine = trig.sin(angle)

    Matrix3[A](
      num.one,  num.zero, num.zero,
      num.zero, cosine,   -sine,
      num.zero, sine,     cosine
    )
  }

  def rotatorY[A: ClassTag](angle: A)(implicit num: Numeric[A], trig: Trig[A]) = {
    val cosine = trig.cos(angle)
    val sine = trig.sin(angle)

    Matrix3[A](
      cosine,     num.zero,   sine,
      num.zero,   num.one,    num.zero,
      -sine,      num.zero,   cosine
    )
  }

  def rotatorZ[A: ClassTag](angle: A)(implicit num: Numeric[A], trig: Trig[A]) = {
    val cosine = trig.cos(angle)
    val sine = trig.sin(angle)

    Matrix3[A](
      cosine,   -sine,      num.zero,
      sine,     cosine,     num.zero,
      num.zero, num.zero,   num.one
    )
  }

  def rotator[A: ClassTag](angle: A, vec: Vector3[A])(implicit num: Numeric[A], trig: Trig[A]): Matrix3[A] =
    rotator(
      angle = angle,
      x = vec.x,
      y = vec.y,
      z = vec.z
    )

  def rotator[A: ClassTag](angle: A, x: A, y: A, z: A)(implicit num: Numeric[A], trig: Trig[A]): Matrix3[A] = {
    val cos = trig.cos(angle)
    val sin = trig.sin(angle)
    val oneCos = 1 - cos

    val xx = x * x
    val xy = x * y
    val xz = x * z
    val yy = y * y
    val yz = y * z
    val zz = z * z

    Matrix3[A](
      cos + xx * oneCos,          xy * oneCos - z * sin,      xz * oneCos + y * sin,
      xy * oneCos + z * sin,      cos + yy * oneCos,          yz * oneCos - x * sin,
      xz * oneCos - y * sin,      yz * oneCos + x * sin,      cos + zz * oneCos
    )
  }

  def lookAt[A: ClassTag](position: Vector3[A], center: Vector3[A], up: Vector3[A])(implicit num: Numeric[A]): Isometry3[A] = {
    // Compute direction from position to lookAt
    val zAxis = (position - center).normalise()
    val xAxis = (up cross zAxis).normalise()
    val yAxis = (zAxis cross xAxis).normalise()

    val rotate =
      Matrix3[A](
        xAxis.x,   xAxis.y,   xAxis.z,
        yAxis.x,   yAxis.y,   yAxis.z,
        zAxis.x,   zAxis.y,   zAxis.z,
      )

    val translate =
      Vector3[A](
        -(xAxis dot position),
        -(yAxis dot position),
        -(zAxis dot position)
      )

    Isometry3(
      rotation = rotate,
      translation = translate
    )
  }

  def perspective(fov: Double, width: Double, height: Double, zNear: Double, zFar: Double) = {
    val h = Math.tan(fov * 0.5d)
    val _00 = 1.0d / (h * width / height)
    val _11 = 1.0d / h
    val _22 = -(zFar + zNear) / (zFar - zNear)
    val _23 = -2 * zFar * zNear / (zFar - zNear)

    val rotate =
      Matrix3[Double](
        _00,    0,      0,
        0,    _11,      0,
        0,      0,    _22
      )

    val translate =
      Vector3(0, 0, _23)

    Isometry3(
      rotation = rotate,
      translation = translate
    )
  }

  def from[A: ClassTag](matrix: Matrix2[A], fill: A)(implicit num: Numeric[A]): Matrix3[A] =
    Matrix3[A](
      matrix._00, matrix._10, fill,
      matrix._01, matrix._11, fill,
      fill,         fill,     fill
    )
  //@formatter:on
}

case class Matrix3[A: ClassTag](_00: A, _01: A, _02: A,
                                _10: A, _11: A, _12: A,
                                _20: A, _21: A, _22: A)(implicit num: Numeric[A]) {

  def row1 = Vector3(_00, _01, _02)
  def row2 = Vector3(_10, _11, _12)
  def row3 = Vector3(_20, _21, _22)

  def *(vec: Vector3[A]): Vector3[A] =
    Vector3(
      _00 * vec.x + _01 * vec.y + _02 * vec.z,
      _10 * vec.x + _11 * vec.y + _12 * vec.z,
      _20 * vec.x + _21 * vec.y + _22 * vec.z
    )

  def /(scalar: A): Matrix3[A] =
    Matrix3(
      _00 / scalar, _01 / scalar, _02 / scalar,
      _10 / scalar, _11 / scalar, _12 / scalar,
      _20 / scalar, _21 / scalar, _22 / scalar
    )

  def *(mat: Matrix3[A]): Matrix3[A] =
    Matrix3(
      this._00 * mat._00 + this._01 * mat._10 + this._02 * mat._20, /*-------*/ this._00 * mat._01 + this._01 * mat._11 + this._02 * mat._21, /*-------*/ this._00 * mat._02 + this._01 * mat._12 + this._02 * mat._22,
      this._10 * mat._00 + this._11 * mat._10 + this._12 * mat._20, /*-------*/ this._10 * mat._01 + this._11 * mat._11 + this._12 * mat._21, /*-------*/ this._10 * mat._02 + this._11 * mat._12 + this._12 * mat._22,
      this._20 * mat._00 + this._21 * mat._10 + this._22 * mat._20, /*-------*/ this._20 * mat._01 + this._21 * mat._11 + this._22 * mat._21, /*-------*/ this._20 * mat._02 + this._21 * mat._12 + this._22 * mat._22,
    )

  /**
   * 2x2 matrix
   *
   * @param row    row to remove
   * @param column column to remove
   */
  def sub(row: Int, column: Int): Matrix2[A] =
    (row, column) match {
      //row 1
      case (0, 0) =>
        Matrix2(
          _11, _12,
          _21, _22
        )

      case (0, 1) =>
        Matrix2(
          _10, _12,
          _20, _22
        )

      case (0, 2) =>
        Matrix2(
          _10, _11,
          _20, _21
        )

      //row 2
      case (1, 0) =>
        Matrix2(
          _01, _02,
          _21, _22
        )

      case (1, 1) =>
        Matrix2(
          _00, _02,
          _20, _22
        )

      case (1, 2) =>
        Matrix2(
          _00, _01,
          _20, _21
        )

      //row 3
      case (2, 0) =>
        Matrix2(
          _01, _02,
          _11, _12
        )

      case (2, 1) =>
        Matrix2(
          _00, _02,
          _10, _12
        )

      case (2, 2) =>
        Matrix2(
          _00, _01,
          _10, _11
        )
    }

  def reflect(): Matrix3[A] =
    Matrix3(
      _00, _10, _20,
      _01, _11, _21,
      _02, _12, _22
    )

  def invert(): Matrix3[A] = {
    val deter = determinant()
    if (deter == num.zero) {
      throw new Exception(s"Determinant does not exist for matrix: $this")
    } else {

      val matrix =
      //@formatter:off
        Matrix3(
          sub(0, 0).determinant(),      sub(0, 1).determinant() * -1, sub(0, 2).determinant(),
          sub(1, 0).determinant() * -1, sub(1, 1).determinant(),      sub(1, 2).determinant() * -1,
          sub(2, 0).determinant(),      sub(2, 1).determinant() * -1, sub(2, 2).determinant()
        )
      //@formatter:on

      matrix.reflect() / deter
    }
  }

  def transpose(): Matrix3[A] =
    Matrix3[A](
      _00, _10, _20,
      _01, _11, _21,
      _02, _12, _22
    )

  def rotateX(angle: A)(implicit trig: Trig[A]) =
    Matrix3.rotatorX(angle) * this

  def rotateY(angle: A)(implicit trig: Trig[A]) =
    Matrix3.rotatorY(angle) * this

  def rotateZ(angle: A)(implicit trig: Trig[A]) =
    Matrix3.rotatorZ(angle) * this

  /**
   * This can also be written as
   *
   * row1 dot (row2 cross row3)
   */
  //@formatter:off
  def determinant(): A =
    _00 * sub(0, 0).determinant() -
      _01 * sub(0, 1).determinant() +
      _02 * sub(0, 2).determinant()
  //@formatter:on

  def rotate(angle: A, vec: Vector3[A])(implicit trig: Trig[A]) =
    Matrix3.rotator(
      angle = angle,
      x = vec.x,
      y = vec.y,
      z = vec.z
    ) * this

  def solveCramer(result: Vector3[A]): Vector3[A] = {
    val determinant =
      this.determinant()

    if (determinant == num.zero)
      throw new Exception(s"Cannot solve equation. determinant = $determinant")

    val xDeterminant =
      this.copy(
        _00 = result.x,
        _10 = result.y,
        _20 = result.z
      ).determinant()

    val yDeterminant =
      this.copy(
        _01 = result.x,
        _11 = result.y,
        _21 = result.z
      ).determinant()

    val zDeterminant =
      this.copy(
        _02 = result.x,
        _12 = result.y,
        _22 = result.z
      ).determinant()

    Vector3(
      x = xDeterminant / determinant,
      y = yDeterminant / determinant,
      z = zDeterminant / determinant
    )
  }

  def map(f: A => A): Matrix3[A] =
    Matrix3(
      f(_00), f(_01), f(_02),
      f(_10), f(_11), f(_12),
      f(_20), f(_21), f(_22)
    )

  def abs(): Matrix3[A] =
    this.map(Maths.abs)

  def toArray() =
    Array(
      _00, _01, _02,
      _10, _11, _12,
      _20, _21, _22
    )

  def toRowArray(): Array[Array[A]] =
    Array(
      Array(_00, _01, _02),
      Array(_10, _11, _12),
      Array(_20, _21, _22)
    )

  override def toString: String =
    ArrayUtil.toString(this.transpose().toRowArray())

}


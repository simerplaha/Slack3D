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

package slack3d.algebra.util

import spire.algebra.Trig
import spire.math.Numeric
import spire.implicits._

object Maths {

  @inline def round[A](number: A, scale: Int = 6)(implicit num: Numeric[A]): BigDecimal =
    BigDecimal(num.toDouble(number)).setScale(scale, BigDecimal.RoundingMode.HALF_UP)

  def inverseSqrt[A](a: A)(implicit num: Numeric[A]): A =
    num.one / num.sqrt(a)

  def pow[A](a: A, pow: Int)(implicit num: Numeric[A]): A =
    num.pow(a, pow)

  def pow[A](a: A, pow: A)(implicit num: Numeric[A]): A =
    num.fpow(a, pow)

  def sqrt[A](a: A)(implicit num: Numeric[A]): A =
    num.sqrt(a)

  def floor[A](a: A)(implicit num: Numeric[A]): A =
    num.floor(a)

  def exp[A](a: A)(implicit num: Trig[A]): A =
    num.exp(a)

  def min[A](a: A*)(implicit num: Numeric[A]): A =
    a.reduce[A] {
      case (left, right) =>
        left min right
    }

  def max[A](a: A*)(implicit num: Numeric[A]): A =
    a.reduce[A] {
      case (left, right) =>
        left max right
    }

  def toRadians(a: Double): Double =
    implicitly[Trig[Double]].toRadians(a)

  def toDegrees(a: Double): Double =
    implicitly[Trig[Double]].toDegrees(a)

  def toRadians(a: Float): Float =
    implicitly[Trig[Float]].toRadians(a)

  def toDegrees(a: Float): Float =
    implicitly[Trig[Float]].toDegrees(a)

  def cos(a: Float): Float =
    implicitly[Trig[Float]].cos(a)

  def sin(a: Float): Float =
    implicitly[Trig[Float]].sin(a)

  def tan(a: Float): Float =
    implicitly[Trig[Float]].tan(a)

  def cos(a: Double): Double =
    implicitly[Trig[Double]].cos(a)

  def sin(a: Double): Double =
    implicitly[Trig[Double]].sin(a)

  def tan(a: Double): Double =
    implicitly[Trig[Double]].tan(a)

  def abs[A](a: A)(implicit num: Numeric[A]): A =
    num.abs(a)
}

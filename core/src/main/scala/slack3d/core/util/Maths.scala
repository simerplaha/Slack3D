package slack3d.core.util

import spire.algebra.Trig
import spire.implicits._
import spire.math.Numeric

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

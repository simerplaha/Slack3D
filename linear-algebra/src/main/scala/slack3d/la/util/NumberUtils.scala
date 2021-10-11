package slack3d.la.util

import spire.math.Numeric

object NumberUtils {

  implicit class SpireImplicits[A](number: A) {
    def roundNum(scale: Int = 2)(implicit num: Numeric[A]): A =
      num.fromBigDecimal(Maths.round(number = num.toDouble(number), scale = scale))

    def roundNumToString(scale: Int = 2)(implicit num: Numeric[A]): String =
      if(num.toDouble(number).isNaN)
        "NaN"
      else
        this.roundNum(scale).toString
  }

}

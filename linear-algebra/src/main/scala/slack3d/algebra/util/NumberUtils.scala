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

import spire.math.Numeric

object NumberUtils {

  implicit class SpireImplicits[A](number: A) {
    def roundNum(scale: Int = 2)(implicit num: Numeric[A]): A =
      num.fromBigDecimal(Maths.round(number = num.toDouble(number), scale = scale))

    def roundNumToString(scale: Int = 2)(implicit num: Numeric[A]): String =
      if (num.toDouble(number).isNaN)
        "NaN"
      else
        this.roundNum(scale).toString
  }

}

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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Matrix3Spec extends AnyWordSpec with Matchers {

  "inverse" in {
    //some transformation
    val transformation =
      Matrix3(
        1, -1, -1,
        -1, 2, 3,
        1, 1, 4
      )

    //apply transformation to vector
    val originalVector = Vector3(1, 2, 3)

    //transformed vector
    val transformedVector = transformation * originalVector

    //inverse of the above transformation
    val inverseTransformation =
      Matrix3(
        5, 3, -1,
        7, 5, -2,
        -3, -2, 1
      )

    //assert inversion
    transformation.invert() shouldBe inverseTransformation

    //applying inverse result in the original vector
    val inverseResult = inverseTransformation * transformedVector
    inverseResult shouldBe originalVector
  }


  "determinant of a matrix" in {
    val matrix =
      Matrix3(
        1, 1, 1,
        1, 2, 1,
        2, 1, 1
      )

    matrix.determinant() shouldBe -1

    val vec1 = Vector3(1, 1, 1)
    val vec2 = Vector3(1, 2, 1)
    val vec3 = Vector3(2, 1, 1)

    println(vec1 dot vec2.cross(vec3))
  }

  "resolveCramer" should {
    "1" in {
      val matrix =
        Matrix3(
          2, 1, 1,
          0, 3, -1,
          1, 2, 2
        )

      val result = matrix solveCramer Vector3(1, 7, -4)

      println(result)

      result shouldBe Vector3(2, 1, -4)
    }

    "2" in {
      //https://www.youtube.com/watch?v=ZVxvMAexnQ4&list=PLmT2ZkjfaLMhPrDoILSv-OTzMTsZariYX&index=10&ab_channel=Cs184Departmental

      val matrix =
        Matrix3[Double](
          0, 1, 1,
          0, 1, 0,
          1, 1, 1
        )

      val result = matrix solveCramer Vector3[Double](3d / 4d, 1d / 2d, 1)

      result shouldBe Vector3[Double](0.25, 0.5, 0.25)
    }

    "3" in {
      //https://www.youtube.com/watch?v=iMQRo0tHORw&ab_channel=patrickJMT

      val matrix =
        Matrix3[Double](
          1, -1, 1,
          2, 1, 1,
          -1, -2, 2
        )

      val result = matrix solveCramer Vector3[Double](4, 7, -1)

      result shouldBe Vector3[Double](3, 0, 1)
    }

    "4" in {
      val matrix =
        Matrix3[Double](
          0, 2, 3,
          -4, -5, -6,
          7, -8, 9
        )

      val result =
        Vector3[Double](
          x = 0 * 10 + 2 * 11 + 3 * 12,
          y = -4 * 10 + -5 * 11 + -6 * 12,
          z = 7 * 10 + -8 * 11 + 9 * 12
        )

      val solution = matrix solveCramer result

      solution shouldBe Vector3[Double](10, 11, 12)
    }

    "no solution" in {
      val matrix =
        Matrix3[Double](
          1, 2, 3,
          4, 5, 6,
          7, 8, 9
        )

      val result =
        Vector3[Double](
          x = 1 * 10 + 2 * 11 + 3 * 12,
          y = 4 * 10 + 5 * 11 + 6 * 12,
          z = 7 * 10 + 8 * 11 + 9 * 12
        )

      assertThrows[Exception](matrix solveCramer result)
    }
  }
}

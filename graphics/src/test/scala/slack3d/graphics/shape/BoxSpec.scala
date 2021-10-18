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

package slack3d.graphics.shape

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import slack3d.TestData._
import slack3d.algebra.Vector3
import slack3d.algebra.util.NumberUtils._
import slack3d.graphics.Slack3D

class BoxSpec extends AnyWordSpec with Matchers {

  "cube" in {
    Slack3D(Box())
  }

  "unit cube" in {
    def doTest(scale: Double) = {
      val cube = Box() * scale

      cube.center().round(2) shouldBe Vector3.origin[Double]()
      cube.width() shouldBe scale
      cube.height() shouldBe scale
      cube.depth() shouldBe scale
    }

    (1 to 10).map(_.toDouble / 10d) foreach doTest
    (1 to 10).map(_.toDouble) foreach doTest
  }

  "translating should not change cube dimensions" in {
    (1 to 100) foreach {
      i =>
        val cube = Box() + Vector3(randomDoublePosOrNeg(), randomDoublePosOrNeg(), randomDoublePosOrNeg())

        val width = cube.width()
        val height = cube.height()
        val depth = cube.depth()

        width.roundNum() shouldBe 1
        height.roundNum() shouldBe 1
        depth.roundNum() shouldBe 1
    }
  }
}

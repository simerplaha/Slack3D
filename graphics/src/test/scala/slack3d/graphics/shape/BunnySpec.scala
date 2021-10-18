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
import slack3d.graphics.Slack3D

class BunnySpec extends AnyWordSpec with Matchers {

  "bunny" in {
    val bunny = Bunny() * 5

    Slack3D("", enable2DCoordinates = false).foldLeft(bunny) {
      case (_bunny, state) =>
        val bunny = _bunny.rotateY(state.getTime() / 15)
        (bunny, Seq(bunny))
    }
  }
}

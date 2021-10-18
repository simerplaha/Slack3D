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

package slack3d.examples

import slack3d.algebra.Vector3
import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.Line

object LinesAndVectorsExample extends App {

  Slack3D("Lines and vectors") foreach {
    state =>
      //vector1
      val vector1 = Vector3(0.5, -0.5, 0)
      //vector2
      val vector2 = Vector3(0.5, 0.5, 0)
      //cross product
      val cross = vector1 cross vector2

      Seq(
        Line(vector1, Colour.Red),
        Line(vector2, Colour.Yellow),
        Line(cross, Colour.Green)
      )
  }

}

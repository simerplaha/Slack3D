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

package slack3d.graphics.shape.line

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.{Cone, Shape, Text}

trait LineOrRay extends Shape {
  def from: Vector3[Double]
  def to: Vector3[Double]
  def colour: Colour

  def cone: Option[Cone]
  def text: Option[Text]

  def vectors(): Array[Vector3[Double]] =
    Array(from, to)

  def updateColour(colour: Colour): LineOrRay

  def vector(): Vector3[Double] =
    to - from

  def lengthSquared(): Double =
    vector().lengthSquared()

  def length(): Double =
    vector().length()
}

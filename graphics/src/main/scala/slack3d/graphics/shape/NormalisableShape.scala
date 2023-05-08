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

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.Line

trait NormalisableShape extends Shape {

  def normal(): Vector3[Double]

  def center: Vector3[Double]

  def normalLine(colour: Colour,
                 text: String) =
    Line(normal(), colour, text)

  def normalLine(colour: Colour = Colour.next(),
                 text: Option[String] = None) =
    Line(normal(), colour, text)

  def normalLineFromCentre(text: String): Line =
    normalLineFromCentre(Colour.next(), Some(text))

  def normalLineFromCentre(colour: Colour,
                           text: String): Line =
    normalLineFromCentre(colour, Some(text))

  def normalLineFromCentre(colour: Colour = Colour.next(),
                           text: Option[String] = None,
                           showVectorInfo: Boolean = true) = {
    val center = this.center
    val perpendicular = this.normal() + center
    Line(
      from = center,
      to = perpendicular,
      colour = colour,
      showVectorInfo = showVectorInfo,
      label = text,
    )
  }
}

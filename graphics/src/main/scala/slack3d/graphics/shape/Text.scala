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

import slack3d.algebra.{Vector2, Vector3}
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay
import slack3d.algebra.util.NumberUtils._

object Text {

  val DEFAULT_SCALE: Double = 0.02d
  val DEFAULT_X_PADDING: Double = 0.03d
  val DEFAULT_Y_PADDING: Double = 0.02d

  def apply(text: String, colour: Colour): Text =
    Text(text = text, x = 0, y = 0, z = 0, colour = colour, showVectorInfo = false)

  def apply(text: String, position: Vector3[Double], colour: Colour): Text =
    Text(text = text, x = position.x, y = position.y, z = position.z, colour = colour, showVectorInfo = false)

  def apply(text: String, position: Vector3[Double], colour: Colour, showVectorInfo: Boolean): Text =
    Text(text = text, x = position.x, y = position.y, z = position.z, colour = colour, showVectorInfo = showVectorInfo)

  def apply(text: String, position: Vector3[Double]): Text =
    Text(text = text, x = position.x, y = position.y, z = position.z, colour = Colour.next(), showVectorInfo = false)

  def apply(text: String, position: Vector2[Double]): Text =
    Text(text = text, x = position.x, y = position.y, z = 0, colour = Colour.next(), showVectorInfo = false)

  def apply(text: String, position: Vector2[Double], colour: Colour): Text =
    Text(text = text, x = position.x, y = position.y, z = 0, colour = colour, showVectorInfo = false)

  def apply(text: String, position: Vector3[Double], showVectorInfo: Boolean): Text =
    Text(text = text, x = position.x, y = position.y, z = position.z, colour = Colour.next(), showVectorInfo = showVectorInfo)

  def apply(text: String, x: Double, y: Double): Text =
    Text(text = text, x = x, y = y, z = 0.0d, colour = Colour.next(), showVectorInfo = false)

  def apply(text: String, x: Double, y: Double, z: Double): Text =
    Text(text = text, x = x, y = y, z = z, colour = Colour.next(), showVectorInfo = false)

  def apply(text: String, x: Double, y: Double, colour: Colour, showVectorInfo: Boolean): Text =
    Text(text = text, x = x, y = y, z = 0.0d, colour = colour, showVectorInfo = showVectorInfo)

  def apply(text: String, x: Double, y: Double, colour: Colour): Text =
    Text(text = text, x = x, y = y, z = 0.0d, colour = colour, showVectorInfo = false)

  def apply(text: String, x: Double, y: Double, z: Double, colour: Colour): Text =
    Text(text = text, x = x, y = y, z = z, colour = colour, showVectorInfo = false)

  def apply(text: String, x: Double, y: Double, z: Double, colour: Colour, showVectorInfo: Boolean): Text = {
    val position = Vector3[Double](x = x, y = y, z = z)

    val characters =
      Character(
        characters =
          if (showVectorInfo)
            position.toTuple3Or2StringRounded() + s"$text"
          else
            text,
        colour = colour,
        // 0.04d so that the text does not sit on the cone
        position = Vector3(x = position.x + 0.02d, y = position.y, z = position.z),
        scale = DEFAULT_SCALE,
        xPadding = DEFAULT_X_PADDING,
        yPadding = DEFAULT_Y_PADDING
      )

    Text(characters)
  }

  def toVectorInfoString(from: Vector3[Double],
                         to: Vector3[Double],
                         label: Option[String]): String = {
    val positionString = to.toTuple3Or2StringRounded()

    val vectorLength = (to - from).length()

    positionString + "|" + vectorLength.roundNumToString() + "|" + label.getOrElse("")
  }

  def apply(to: Vector3[Double],
            colour: Colour): Text =
    Text(
      from = Vector3.origin[Double](),
      to = to,
      colour = colour,
      showVectorInfo = false,
      label = None
    ).get

  def apply(text: String,
            from: Vector3[Double],
            to: Vector3[Double],
            colour: Colour,
            showVectorInfo: Boolean): Text = {
    val characters =
      if (showVectorInfo) {
        val vectorLength = (to - from).length()
        Character(characters = "|" + vectorLength.roundNumToString() + "|" + text, colour = colour, position = to, scale = DEFAULT_SCALE, xPadding = DEFAULT_X_PADDING, yPadding = DEFAULT_Y_PADDING)
      } else {
        Character(characters = text, colour = colour, position = to, scale = DEFAULT_SCALE, xPadding = DEFAULT_X_PADDING, yPadding = DEFAULT_Y_PADDING)
      }

    Text(characters)
  }

  def apply(from: Vector3[Double],
            to: Vector3[Double],
            colour: Colour,
            showVectorInfo: Boolean,
            label: Option[String]): Option[Text] = {
    // 0.04d so that the text does not sit on the cone
    val position = Vector3(x = to.x + 0.02d, y = to.y, z = to.z)

    val characters =
      if (showVectorInfo) {
        val text = Text.toVectorInfoString(from = from, to = to, label = label)
        Some(Character(characters = text, colour = colour, position = position, scale = DEFAULT_SCALE, xPadding = DEFAULT_X_PADDING, yPadding = DEFAULT_Y_PADDING))
      } else {
        label map {
          label =>
            Character(characters = label, colour = colour, position = position, scale = DEFAULT_SCALE, xPadding = DEFAULT_X_PADDING, yPadding = DEFAULT_Y_PADDING)
        }
      }

    characters.map(Text(_))
  }
}

case class Text(text: Array[Character]) extends Shape {

  override type Self = Text

  override def map(f: Vector3[Double] => Vector3[Double]): Text =
    Text(text.map(_.map(f)))

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    text.foreach(_.buildMesh(mesh))
}

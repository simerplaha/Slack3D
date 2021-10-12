package slack3d.graphics.shape.line

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Shape

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

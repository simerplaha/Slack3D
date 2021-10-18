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
import slack3d.graphics.shape.line.LineOrRay

object Box {

  def apply(x: Double, y: Double, z: Double): Box =
    Box(widths = Vector3(x, y, z))

  def apply(x: Double, y: Double, z: Double, colour: Colour): Box =
    Box(colour = colour, widths = Vector3(x, y, z))

  def apply(colour: Colour = Colour.next(),
            widths: Vector3[Double] = Vector3(0.5d, 0.5d, 0.5d)): Box = {
    val back =
      Plane(
        a = Vector3(-widths.x, -widths.y, -widths.z),
        b = Vector3(widths.x, -widths.y, -widths.z),
        c = Vector3(widths.x, widths.y, -widths.z),
        d = Vector3(-widths.x, widths.y, -widths.z),
        colour = colour,
        flipNormal = true
      )

    val front =
      back.setFlipNormal(false) + Vector3(0, 0, 2 * widths.z)

    val left =
      Plane(
        a = Vector3(-widths.x, -widths.y, -widths.z),
        b = Vector3(-widths.x, -widths.y, widths.z),
        c = Vector3(-widths.x, widths.y, widths.z),
        d = Vector3(-widths.x, widths.y, -widths.z),
        colour = colour,
        flipNormal = false
      )

    val right =
      left.setFlipNormal(true) + Vector3(2 * widths.x, 0, 0)

    val top =
      Plane(
        a = Vector3(-widths.x, widths.y, widths.z),
        b = Vector3(widths.x, widths.y, widths.z),
        c = Vector3(widths.x, widths.y, -widths.z),
        d = Vector3(-widths.x, widths.y, -widths.z),
        colour = colour,
        flipNormal = false
      )

    val bottom =
      top.setFlipNormal(true) + Vector3(0, -(2 * widths.y), 0)

    new Box(
      back = back,
      front = front,
      left = left,
      right = right,
      top = top,
      bottom = bottom
    )
  }

}

case class Box(back: Plane,
               front: Plane,
               left: Plane,
               right: Plane,
               top: Plane,
               bottom: Plane) extends Shape {

  override type Self = Box

  def vectors(): Array[Vector3[Double]] =
    back.vectors() ++ front.vectors() ++ left.vectors() ++ right.vectors() ++ top.vectors() ++ bottom.vectors()

  def vectorsIterator(): Iterator[Vector3[Double]] =
    back.vectors().iterator ++
      front.vectors().iterator ++
      left.vectors().iterator ++
      right.vectors().iterator ++
      top.vectors().iterator ++
      bottom.vectors().iterator

  def width(): Double =
    (left.triangleA.a - right.triangleA.a).length()

  def height(): Double =
    (bottom.triangleA.a - top.triangleA.a).length()

  def depth(): Double =
    (back.triangleA.a - front.triangleA.a).length()

  def planes: Array[Plane] =
    Array(back, front, left, right, top, bottom)

  def center() =
    (back.center() + front.center() + left.center() + right.center() + top.center() + bottom.center()) / 6

  def updateColour(colour: Colour): Box =
    mapPlanes(_.updateColour(colour))

  def mapPlanes(f: Plane => Plane): Box =
    Box(
      back = f(back),
      front = f(front),
      left = f(left),
      right = f(right),
      top = f(top),
      bottom = f(bottom)
    )

  override def map(f: Vector3[Double] => Vector3[Double]): Box =
    Box(
      back = back.map(f),
      front = front.map(f),
      left = left.map(f),
      right = right.map(f),
      top = top.map(f),
      bottom = bottom.map(f)
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    back buildMesh mesh
    front buildMesh mesh
    left buildMesh mesh
    right buildMesh mesh
    top buildMesh mesh
    bottom buildMesh mesh
  }
}

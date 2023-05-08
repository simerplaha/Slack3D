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

object Triangle {

  def apply(vertices: Array[Vector3[Double]],
            colour: Colour): Triangle =
    Triangle(
      vertices = vertices,
      colour = colour,
      flipNormal = false
    )

  def apply(vertices: Array[Vector3[Double]]): Triangle =
    Triangle(
      vertices = vertices,
      colour = Colour.next(),
      flipNormal = false
    )

  def apply(vertices: Array[Vector3[Double]],
            colour: Colour,
            flipNormal: Boolean): Triangle =
    new Triangle(
      a = vertices(0),
      b = vertices(1),
      c = vertices(2),
      colour = colour,
      flipNormal = flipNormal
    )

  def apply(colour: Colour = Colour.next(),
            side: Double = 0.5d,
            flipNormal: Boolean = false): Triangle =
    new Triangle(
      a = Vector3[Double](-side, -side, 0.0d),
      b = Vector3[Double](side, -side, 0.0d),
      c = Vector3[Double](0.0d, side, 0.0d),
      colour = colour,
      flipNormal = flipNormal
    )

  def apply(a: Vector3[Double],
            b: Vector3[Double],
            c: Vector3[Double],
            colour: Colour): Triangle =
    new Triangle(
      a = a,
      b = b,
      c = c,
      colour = colour,
      flipNormal = false
    )
}

case class Triangle(a: Vector3[Double],
                    b: Vector3[Double],
                    c: Vector3[Double],
                    colour: Colour,
                    flipNormal: Boolean) extends NormalisableShape {

  override type Self = Triangle

  def apply(index: Int) =
    if (index == 0)
      a
    else if (index == 1)
      b
    else if (index == 2)
      c
    else
      throw new Exception(s"Invalid index $index for triangle")

  def vectors() =
    Array(a, b, c)

  def center: Vector3[Double] =
    (a + b + c) / 3

  override def normal(): Vector3[Double] =
    if (flipNormal)
      c - a cross b - a
    else
      b - a cross c - a

  override def map(f: Vector3[Double] => Vector3[Double]): Triangle =
    Triangle(
      a = f(a),
      b = f(b),
      c = f(c),
      colour = colour,
      flipNormal = flipNormal
    )

  def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    mesh.triangles += this
}

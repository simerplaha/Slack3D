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

object Tetrahedron {

  def apply(colour: Colour = Colour.next()) = {

    val point =
      Vector3(0d, 0.5d, 0.0d)

    val midFront = Vector3(0d, -0.5d, 0.5d)
    val rightBack = Vector3(0.5d, -0.5d, -0.5d)
    val leftBack = Vector3(-0.5d, -0.5d, -0.5d)

    val base =
      Triangle(
        a = midFront,
        b = rightBack,
        c = leftBack,
        colour = colour,
        flipNormal = false
      )

    val face1: Triangle =
      Triangle(
        a = point,
        b = midFront,
        c = rightBack,
        colour = colour,
        flipNormal = false
      )

    val face2: Triangle =
      Triangle(
        a = point,
        b = rightBack,
        c = leftBack,
        colour = colour,
        flipNormal = false
      )

    val face3: Triangle =
      Triangle(
        a = point,
        b = leftBack,
        c = midFront,
        colour = colour,
        flipNormal = false
      )

    new Tetrahedron(
      point = Point(point),
      face1 = face1,
      face2 = face2,
      face3 = face3,
      base = base
    )
  }
}

case class Tetrahedron private(point: Point,
                               face1: Triangle,
                               face2: Triangle,
                               face3: Triangle,
                               base: Triangle) extends Shape {

  override type Self = Tetrahedron

  def updateColour(colour: Colour) =
    Tetrahedron(
      point = point.copy(colour = colour),
      face1 = face1.copy(colour = colour),
      face2 = face2.copy(colour = colour),
      face3 = face3.copy(colour = colour),
      base = base.copy(colour = colour)
    )

  override def map(f: Vector3[Double] => Vector3[Double]): Tetrahedron =
    Tetrahedron(
      point = point.map(f),
      face1 = face1.map(f),
      face2 = face2.map(f),
      face3 = face3.map(f),
      base = base.map(f)
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.triangles += face1
    mesh.triangles += face2
    mesh.triangles += face3
    mesh.triangles += base
  }
}

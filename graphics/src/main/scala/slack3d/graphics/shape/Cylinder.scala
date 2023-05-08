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

object Cylinder {

  def apply(colour: Colour = Colour.next(),
            radius: Double = 1,
            sectors: Int = 20) = {

    val unitCircle: Circle = Circle.xz(colour, radius, sectors)

    val head = unitCircle + Vector3[Double](0d, 0.5d, 0.0d)
    val base = unitCircle + Vector3[Double](0d, -0.5d, 0.0d)

    val planes =
      head.triangles.zip(base.triangles) map {
        case (headTriangle, baseTriangle) =>
          //remove centroids from head and base triangles
          val headOther = headTriangle.vectors().filter(_.x != 0d)
          val baseOther = baseTriangle.vectors().filter(_.x != 0d)

          val headTrianglePlane =
            Triangle(baseOther.head +: headOther, colour, flipNormal = false)

          val baseTrianglePlane =
            Triangle(headOther.last +: baseOther, colour, flipNormal = true)

          Plane(
            triangleA = headTrianglePlane,
            triangleB = baseTrianglePlane,
            flipNormal = false
          )
      }

    new Cylinder(
      head = head.copy(flipNormal = true),
      base = base.copy(flipNormal = true),
      planes = planes
    )
  }
}

case class Cylinder private(head: Circle,
                            base: Circle,
                            planes: Array[Plane]) extends Shape {

  override type Self = Cylinder

  def radius(): Double =
    head.radius()

  def height(): Double =
    (head.center - base.center).length()

  def volume(): Double =
    head.area() * height()

  def surfaceArea(): Double =
    head.area() + base.area() + (height() * head.circumference())

  override def map(f: Vector3[Double] => Vector3[Double]): Cylinder =
    Cylinder(
      head = head.map(f),
      base = base.map(f),
      planes = planes.map(_.map(f))
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    head buildMesh mesh
    base buildMesh mesh
    planes foreach (_.buildMesh(mesh))
  }
}

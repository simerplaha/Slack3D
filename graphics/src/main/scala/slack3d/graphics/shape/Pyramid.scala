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

object Pyramid {

  def apply(colour: Colour = Colour.next()) = {

    val point = Vector3(0d, 0.5d, 0.0d)

    //base plane coordinates
    val leftFront = Vector3(-0.5d, -0.5d, 0.5d)
    val rightFront = Vector3(0.5d, -0.5d, 0.5d)
    val rightBack = Vector3(0.5d, -0.5d, -0.5d)
    val leftBack = Vector3(-0.5d, -0.5d, -0.5d)

    val base =
      Plane(
        a = leftFront,
        b = rightFront,
        c = rightBack,
        d = leftBack,
        colour = colour,
        flipNormal = false
      )

    val left: Triangle =
      Triangle(
        a = point,
        b = leftBack,
        c = leftFront,
        colour = colour,
        flipNormal = false
      )

    val front: Triangle =
      Triangle(
        a = point,
        b = leftFront,
        c = rightFront,
        colour = colour,
        flipNormal = false
      )

    val right: Triangle =
      Triangle(
        a = point,
        b = rightFront,
        c = rightBack,
        colour = colour,
        flipNormal = false
      )

    val back: Triangle =
      Triangle(
        a = point,
        b = rightBack,
        c = leftBack,
        colour = colour,
        flipNormal = false
      )

    new Pyramid(
      head = Point(point),
      left = left,
      front = front,
      right = right,
      back = back,
      base = base
    )
  }
}

case class Pyramid private(head: Point,
                           left: Triangle,
                           front: Triangle,
                           right: Triangle,
                           back: Triangle,
                           base: Plane) extends Shape {


  override type Self = Pyramid

  def vectors() =
    head.vector +: (left.vectors() ++ front.vectors() ++ right.vectors() ++ back.vectors() ++ base.vectors())

  def height() =
    (head.vector - base.center()).length()

  override def map(f: Vector3[Double] => Vector3[Double]): Pyramid =
    Pyramid(
      head = head.map(f),
      left = left.map(f),
      front = front.map(f),
      right = right.map(f),
      back = back.map(f),
      base = base.map(f)
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.triangles += left
    mesh.triangles += front
    mesh.triangles += right
    mesh.triangles += back
    base buildMesh mesh
  }
}

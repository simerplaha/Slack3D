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

import scala.io.Source

object Bunny {

  def apply(colour: Colour = Colour.next(),
            fileName: String = "bunny.obj"): Bunny = {
    val iterator = Source.fromResource(fileName).getLines().drop(1)
    val vertexCount = iterator.next().filter(_.isDigit).toInt
    val faceCount = iterator.next().filter(_.isDigit).toInt

    var verticesIndex = 0
    val vertices = new Array[Vector3[Double]](vertexCount)

    var facesIndex = 0
    val faces = new Array[Triangle](faceCount)

    iterator foreach {
      string =>
        if (string.startsWith("v ")) {
          val data =
            string
              .drop(2)
              .split("\\s")
              .map(_.toDouble)

          vertices(verticesIndex) = Vector3(data(0), data(1) - 0.1d, data(2))
          verticesIndex += 1
        } else if (string.startsWith("f ")) {
          val data =
            string
              .drop(2)
              .split("\\s")
              .map(_.toInt - 1)

          faces(facesIndex) =
            Triangle(
              a = vertices(data(0)),
              b = vertices(data(1)),
              c = vertices(data(2)),
              colour = colour
            )

          facesIndex += 1
        } else {
          throw new Exception(s"Unknown file format. string = $string")
        }
    }

    new Bunny(faces, colour)
  }
}

case class Bunny(triangles: Array[Triangle],
                 colour: Colour) extends Shape {

  override type Self = Bunny

  override def map(f: Vector3[Double] => Vector3[Double]): Bunny =
    Bunny(triangles.map(_.map(f)), colour)

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    mesh.triangles addAll triangles

}

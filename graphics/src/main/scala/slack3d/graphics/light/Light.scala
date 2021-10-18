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

package slack3d.graphics.light

import slack3d.algebra.Vector3
import slack3d.graphics.camera.Camera
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Triangle
import slack3d.graphics.vertex.Vertex

object Light {

  def apply(colour: Colour = Colour.White,
            position: Vector3[Double] = Vector3[Double](3d, 6.0d, 9.0d),
            ambientStrength: Double = 0.23d,
            specularStrength: Double = 0.5d)(implicit projector: LightProjector) =
    new Light(
      colour = colour,
      position = position,
      ambient = colour * ambientStrength,
      specularStrength = specularStrength
    )
}

class Light private(val colour: Colour,
                    val position: Vector3[Double],
                    val ambient: Colour,
                    val specularStrength: Double)(implicit projector: LightProjector) {

  def apply(camera: Option[Camera],
            triangle: Triangle): Array[Vertex.Triangle[Vector3[Double]]] =
    projector(camera, this, triangle)

}

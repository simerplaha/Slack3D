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

package slack3d.graphics.data

import slack3d.algebra.Vector3
import spire.math.Numeric

import scala.reflect.ClassTag

private[slack3d] object Vec4 {
  def apply[A: ClassTag](vector3: Vector3[A],
                         w: A)(implicit num: Numeric[A]): Vec4[A] =
    Vec4(
      x = vector3.x,
      y = vector3.y,
      z = vector3.z,
      w = w
    )
}

/**
 * OpenGL vector. This is used send data to the GPU
 */
private[slack3d] case class Vec4[A: ClassTag](x: A,
                                              y: A,
                                              z: A,
                                              w: A)(implicit num: Numeric[A])

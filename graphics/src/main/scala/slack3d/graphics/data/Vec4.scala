package slack3d.graphics.data

import slack3d.algebra.Vector3
import spire.math.Numeric

import scala.reflect.ClassTag

object Vec4 {
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
case class Vec4[A: ClassTag](x: A,
                             y: A,
                             z: A,
                             w: A)(implicit num: Numeric[A])

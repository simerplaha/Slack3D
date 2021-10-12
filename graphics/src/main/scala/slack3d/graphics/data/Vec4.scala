package slack3d.graphics.data

import spire.math.Numeric

import scala.reflect.ClassTag

/**
 * OpenGL vector
 */
case class Vec4[A: ClassTag](x: A,
                             y: A,
                             z: A,
                             w: A)(implicit num: Numeric[A])

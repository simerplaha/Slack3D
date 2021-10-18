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

package slack3d.algebra


import spire.math.Numeric

import scala.reflect.ClassTag

sealed trait Isometry3[A] {

  def isometries(): Array[Isometry3.Single[A]]

  def *(vector: Vector3[A]): Vector3[A]

  def *(isometry: Isometry3[A]): Isometry3.Many[A]

}

object Isometry3 {

  def apply[A](rotation: Matrix3[A], translation: Vector3[A]): Single[A] =
    new Single[A](rotation, translation)

  def identity[A: ClassTag]()(implicit num: Numeric[A]): Isometry3[A] =
    Single(
      Matrix3.identity[A](),
      Vector3.zeroes[A]()
    )

  case object Single {

    def apply[A](rotation: Matrix3[A], translation: Vector3[A]): Single[A] =
      new Single[A](rotation, translation)

  }

  case class Single[A] private[Isometry3](rotation: Matrix3[A],
                                          translation: Vector3[A]) extends Isometry3[A] {

    def *(vector: Vector3[A]): Vector3[A] =
      (rotation * vector) + translation

    override def isometries(): Array[Single[A]] =
      Array(this)

    override def *(isometry: Isometry3[A]): Isometry3.Many[A] =
      Many(this +: isometry.isometries())
  }

  case class Many[A] private(isometries: Array[Isometry3.Single[A]]) extends Isometry3[A] {
    override def *(vector: Vector3[A]): Vector3[A] =
      isometries.foldLeft(vector) {
        case (vector, iso) =>
          iso * vector
      }

    override def *(isometry: Isometry3[A]): Many[A] =
      Many(this.isometries ++ isometry.isometries())
  }
}

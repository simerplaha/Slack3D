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

package slack3d


import slack3d.algebra.Vector3
import slack3d.algebra.util.Maths
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Point

import scala.annotation.tailrec
import scala.util.Random

object TestData {

  def randomBoolean(): Boolean =
    Random.nextBoolean()

  def randomIntMax(max: Int = Int.MaxValue) =
    Random.nextInt(max)

  def randomDouble() =
    Random.nextDouble()

  def randomDoublePosOrNeg() =
    Random.nextDouble() * eitherOne(1, -1)

  def randomVector3(): Vector3[Double] =
    Vector3(randomDoublePosOrNeg(), randomDoublePosOrNeg(), randomDoublePosOrNeg())

  def randomPoint(colour: Colour = Colour.next()): Point =
    Point(randomVector3(), colour)

  @tailrec
  def randomIntMaxNonZero(max: Int = Int.MaxValue): Int = {
    val int = randomIntMax(max)
    if (int == 0)
      randomIntMaxNonZero(max)
    else
      int
  }

  def randomIntMin(min: Int) =
    randomIntMax() max min

  def randomIntMaxOption(max: Int = Int.MaxValue) =
    if (randomBoolean())
      Some(randomIntMax(max))
    else
      None

  def randomCoordinate(): Double = {
    val double = if (randomBoolean()) -Random.nextDouble() else Random.nextDouble()
    Maths.round(double, 2).toDouble
  }

  def randomly[T](f: => T): Option[T] =
    if (randomBoolean())
      Some(f)
    else
      None

  def eitherOne[T](left: => T, right: => T): T =
    if (randomBoolean())
      left
    else
      right

  def someOrNone[T](some: => T): Option[T] =
    if (randomBoolean())
      None
    else
      Some(some)

  def orNone[T](option: => Option[T]): Option[T] =
    if (randomBoolean())
      None
    else
      option

  def anyOrder[T](left: => T, right: => T): Unit =
    if (randomBoolean()) {
      left
      right
    } else {
      right
      left
    }

  def eitherOne[T](left: => T, mid: => T, right: => T): T =
    Random.shuffle(Seq(() => left, () => mid, () => right)).head()

  def eitherOne[T](one: => T, two: => T, three: => T, four: => T): T =
    Random.shuffle(Seq(() => one, () => two, () => three, () => four)).head()

  def eitherOne[T](one: => T, two: => T, three: => T, four: => T, five: => T): T =
    Random.shuffle(Seq(() => one, () => two, () => three, () => four, () => five)).head()

  def eitherOne[T](one: => T, two: => T, three: => T, four: => T, five: => T, six: => T): T =
    Random.shuffle(Seq(() => one, () => two, () => three, () => four, () => five, () => six)).head()

}

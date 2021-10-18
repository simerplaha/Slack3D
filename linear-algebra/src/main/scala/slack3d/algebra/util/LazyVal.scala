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

package slack3d.algebra.util

object LazyVal {
  def apply[A](f: => A): LazyVal[A] =
    new LazyVal[A](() => f, None)
}

class LazyVal[A] private(fetch: () => A,
                         private var value: Option[A]) {

  def get(): A =
    this.value match {
      case None =>
        val value = fetch()
        this.value = Some(value)
        value

      case Some(a) =>
        a
    }

  def map[B](f: A => B): LazyVal[B] = {
    val b = f(get())
    new LazyVal[B](() => f(fetch()), Some(b))
  }

  def clear(): Unit =
    value = None

}


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


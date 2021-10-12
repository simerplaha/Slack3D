package slack3d.graphics.shape

import slack3d.algebra.{Vector2, Vector3}
import slack3d.graphics.colour.Colour

object Point {

  val DEFAULT_POINT_SIZE = 6f

  implicit val lexOrdering =
    new Ordering[Point] {
      override def compare(left: Point, right: Point): Int = {
        val xCompare = left.vector.x compareTo right.vector.x
        if (xCompare == 0) {
          val yCompare = left.vector.y compareTo right.vector.y
          if (yCompare == 0)
            left.vector.z compareTo right.vector.z
          else
            yCompare
        }
        else
          xCompare
      }
    }

  def apply(vector: Vector3[Double], colour: Colour): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector3[Double], colour: Colour, label: String): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(vector: Vector3[Double], colour: Colour, showVectorInfo: Boolean): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector3[Double], colour: Colour, showVectorInfo: Boolean, label: Option[String]): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = label)

  def apply(vector: Vector3[Double], colour: Colour, showVectorInfo: Boolean, size: Float): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = showVectorInfo, size = size, label = None)

  def apply(vector: Vector3[Double], colour: Colour, showVectorInfo: Boolean, size: Float, label: String): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = showVectorInfo, size = size, label = Some(label))

  def apply(vector: Vector3[Double]): Point =
    Point(vector.x, vector.y, vector.z, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector3[Double], size: Float): Point =
    Point(vector.x, vector.y, vector.z, Colour.next(), showVectorInfo = true, size = size, label = None)

  def apply(vector: Vector3[Double], showVectorInfo: Boolean): Point =
    Point(vector.x, vector.y, vector.z, Colour.next(), showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector3[Double], size: Float, showVectorInfo: Boolean): Point =
    Point(vector.x, vector.y, vector.z, Colour.next(), showVectorInfo = showVectorInfo, size = size, label = None)

  def apply(vector: Vector3[Double], colour: Colour, size: Float): Point =
    Point(vector.x, vector.y, vector.z, colour, showVectorInfo = true, size = size, label = None)

  def apply(vector: Vector3[Double], label: String): Point =
    Point(vector.x, vector.y, vector.z, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(vector: Vector2[Double]): Point =
    Point(vector.x, vector.y, 0, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector2[Double], label: String): Point =
    Point(vector.x, vector.y, 0, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(vector: Vector2[Double], colour: Colour): Point =
    Point(vector.x, vector.y, 0, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(vector: Vector2[Double], colour: Colour, label: String): Point =
    Point(vector.x, vector.y, 0, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(x: Double, y: Double): Point =
    Point(x, y, 0.0d, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, showVectorInfo: Boolean): Point =
    Point(x, y, 0.0d, Colour.next(), showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, label: String): Point =
    Point(x, y, 0.0d, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(x: Double, y: Double, label: String, pointSize: Float): Point =
    Point(x, y, 0.0d, Colour.next(), showVectorInfo = true, size = pointSize, label = Some(label))

  def apply(x: Double, y: Double, z: Double): Point =
    Point(x, y, z, Colour.next(), showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, colour: Colour, showVectorInfo: Boolean): Point =
    Point(x, y, 0.0d, colour, showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, colour: Colour): Point =
    Point(x, y, 0.0d, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, colour: Colour, text: String): Point =
    Point(x, y, 0.0d, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(text))

  def apply(x: Double, y: Double, z: Double, colour: Colour): Point =
    Point(x, y, z, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, z: Double, showVectorInfo: Boolean): Point =
    Point(x, y, z, Colour.next(), showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, z: Double, size: Float, colour: Colour): Point =
    Point(x, y, z, colour, showVectorInfo = true, size = size, label = None)

  def apply(x: Double, y: Double, z: Double, colour: Colour, showVectorInfo: Boolean): Point =
    Point(x, y, z, colour, showVectorInfo = showVectorInfo, size = DEFAULT_POINT_SIZE, label = None)

  def apply(x: Double, y: Double, z: Double, colour: Colour, label: String): Point =
    Point(x, y, z, colour, showVectorInfo = true, size = DEFAULT_POINT_SIZE, label = Some(label))

  def apply(x: Double, y: Double, z: Double,
            colour: Colour,
            showVectorInfo: Boolean,
            size: Float): Point =
    Point(
      x = x, y = y, z = z,
      colour = colour,
      showVectorInfo = showVectorInfo,
      size = size,
      label = None
    )

  def apply(x: Double, y: Double, z: Double,
            colour: Colour,
            showVectorInfo: Boolean,
            size: Float,
            label: String): Point =
    Point(
      x = x, y = y, z = z,
      colour = colour,
      showVectorInfo = showVectorInfo,
      size = size,
      label = Some(label)
    )

  def apply(x: Double, y: Double, z: Double,
            colour: Colour,
            showVectorInfo: Boolean,
            size: Float,
            label: Option[String]): Point = {
    val to = Vector3[Double](x = x, y = y, z = z)

    val text =
      renderText(
        vector = to,
        size = size,
        colour = colour,
        showVectorInfo = showVectorInfo,
        label = label
      )

    new Point(
      vector = to,
      colour,
      text = text,
      showVectorInfo = showVectorInfo,
      size = size,
      label = label
    )
  }

  def renderText(vector: Vector3[Double],
                 size: Float,
                 colour: Colour,
                 showVectorInfo: Boolean,
                 label: Option[String]): Option[Text] =
    Text(
      from = Vector3.origin[Double](),
      to = vector,
      colour = colour,
      showVectorInfo = showVectorInfo,
      label = label
    ) map {
      text =>
        //move text so that it sits to the exact right of the point
        text + Vector3[Double](size / 800, -0.02d, 0d)
    }
}

case class Point private(vector: Vector3[Double],
                         colour: Colour,
                         size: Float,
                         text: Option[Text],
                         showVectorInfo: Boolean,
                         label: Option[String]) extends Shape {

  override type Self = Point

  def x = vector.x

  def y = vector.y

  def z = vector.z

  def apply(index: Int): Double =
    vector(index)

  override def map(f: Vector3[Double] => Vector3[Double]): Point = {
    val vector = f(this.vector)

    val newCoordinate =
      text flatMap {
        _ =>
          Point.renderText(
            vector = vector,
            size = size,
            colour = colour,
            showVectorInfo = showVectorInfo,
            label = label
          )
      }

    Point(
      vector = vector,
      colour = colour,
      size = size,
      text = newCoordinate,
      showVectorInfo = showVectorInfo,
      label = label
    )
  }

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.points += this
    text.foreach(_.buildMesh(mesh))
  }
}

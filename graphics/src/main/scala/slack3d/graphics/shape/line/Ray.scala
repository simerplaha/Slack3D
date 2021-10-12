package slack3d.graphics.shape.line

import slack3d.graphics.colour.Colour

object Ray {

  def apply(from: Point, to: Point, showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(from = from.vector, to = to.vector, colour = from.colour, showCone = showCone, showVectorInfo = showVectorInfo)

  def apply(from: Point, to: Point): Ray =
    Ray(from.vector, to.vector, from.colour)

  def apply(to: Point): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, to.colour, showCone = true, showVectorInfo = true, label = to.label)

  def apply(from: Vector3[Double], to: Vector3[Double]): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], showCone: Boolean): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showVectorInfo: Boolean): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, label: Option[String]): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = label)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showVectorInfo: Boolean, label: Option[String]): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = label)

  def apply(from: Vector3[Double], to: Vector3[Double], showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, label: String): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double], colour: Colour): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], colour: Colour, label: String): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector3[Double], colour: Colour, showVectorInfo: Boolean, label: String): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = Some(label))

  def apply(to: Vector3[Double], colour: Colour, label: Option[String]): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = label)

  def apply(to: Vector3[Double], colour: Colour, showCone: Boolean): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double]): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double], label: String): Ray =
    Ray(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double]): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector2[Double], label: String): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double], colour: Colour): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double]): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double], colour: Colour): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double], colour: Colour, label: String): Ray =
    Ray(from.x, from.y, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double], colour: Colour, label: String): Ray =
    Ray(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double): Ray =
    Ray(fromX, fromY, 0.0d, toX, toY, 0.0d, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour): Ray =
    Ray(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, label: String): Ray =
    Ray(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, showCone: Boolean): Ray =
    Ray(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = showCone, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Ray =
    Ray(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(x: Double, y: Double, colour: Colour): Ray =
    Ray(0.0d, 0.0d, 0.0d, x, y, 0.0d, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(x: Double, y: Double, colour: Colour, text: String): Ray =
    Ray(0.0d, 0.0d, 0.0d, x, y, 0.0d, colour, showCone = true, showVectorInfo = true, label = Some(text))

  def apply(x: Double, y: Double, z: Double, colour: Colour): Ray =
    Ray(0.0d, 0.0d, 0.0d, x, y, z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(x: Double, y: Double, z: Double, colour: Colour, label: String): Ray =
    Ray(0.0d, 0.0d, 0.0d, x, y, z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, fromZ: Double,
            toX: Double, toY: Double, toZ: Double,
            colour: Colour,
            showCone: Boolean,
            showVectorInfo: Boolean): Ray =
    Ray(
      fromX = fromX, fromY = fromY, fromZ = fromZ,
      toX = toX, toY = toY, toZ = toZ,
      colour = colour,
      showCone = showCone,
      showVectorInfo = showVectorInfo,
      label = None
    )

  def apply(fromX: Double, fromY: Double, fromZ: Double,
            toX: Double, toY: Double, toZ: Double,
            colour: Colour,
            showCone: Boolean,
            showVectorInfo: Boolean,
            label: String): Ray =
    Ray(
      fromX = fromX, fromY = fromY, fromZ = fromZ,
      toX = toX, toY = toY, toZ = toZ,
      colour = colour,
      showCone = showCone,
      showVectorInfo = showVectorInfo,
      label = Some(label)
    )

  def apply(fromX: Double, fromY: Double, fromZ: Double,
            toX: Double, toY: Double, toZ: Double,
            colour: Colour,
            showCone: Boolean,
            showVectorInfo: Boolean,
            label: Option[String]): Ray = {
    val from = Vector3[Double](x = fromX, y = fromY, z = fromZ)
    val to = Vector3[Double](x = toX, y = toY, z = toZ)

    val cone: Option[Cone] =
      if (showCone)
        Some(
          Cone(
            from = from,
            to = to,
            colour = colour
          )
        )
      else
        None

    val text =
      Text(
        from = from,
        to = to,
        colour = colour,
        showVectorInfo = showVectorInfo,
        label = label
      )

    new Ray(
      from = from,
      to = to,
      colour = colour,
      cone = cone,
      text = text,
      showVectorInfo = showVectorInfo,
      label = label
    )
  }

}

case class Ray private(from: Vector3[Double],
                       to: Vector3[Double],
                       colour: Colour,
                       cone: Option[Cone],
                       text: Option[Text],
                       showVectorInfo: Boolean,
                       label: Option[String]) extends LineOrRay {

  override type Self = Ray

  val origin =
    from

  val direction =
    to.normalise()

  def updateColour(colour: Colour): Ray = {
    val text =
      this.text flatMap {
        _ =>
          Text(
            from = from,
            to = to,
            colour = colour,
            showVectorInfo = showVectorInfo,
            label = label
          )
      }

    this.copy(colour = colour, text = text)
  }

  override def map(f: Vector3[Double] => Vector3[Double]): Ray = {
    val to = f(this.to)

    val newCoordinate =
      text flatMap {
        _ =>
          Text(
            from = from,
            to = to,
            colour = colour,
            showVectorInfo = showVectorInfo,
            label = label
          )
      }

    Ray(
      from = f(from),
      to = to,
      colour = colour,
      cone = cone.map(_.map(f)),
      text = newCoordinate,
      showVectorInfo = showVectorInfo,
      label = label
    )
  }

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.lines += this
    cone.foreach(_.buildMesh(mesh))
    text.foreach(_.buildMesh(mesh))
  }
}

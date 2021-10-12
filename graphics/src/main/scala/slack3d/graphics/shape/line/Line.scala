package slack3d.graphics.shape.line


import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object Line {

  def apply(from: Point, to: Point, showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(from = from.vector, to = to.vector, colour = from.colour, showCone = showCone, showVectorInfo = showVectorInfo)

  def apply(from: Point, to: Point): Line =
    Line(from.vector, to.vector, from.colour)

  def apply(to: Point): Line =
    Line(0, 0, 0, to.x, to.y, to.z, to.colour, showCone = true, showVectorInfo = true, label = to.label)

  def apply(from: Vector3[Double], to: Vector3[Double]): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], label: String): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = Some(label))

  def apply(from: Vector3[Double], to: Vector3[Double], showCone: Boolean): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showVectorInfo: Boolean): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, label: Option[String]): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = label)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showVectorInfo: Boolean, label: Option[String]): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = label)

  def apply(from: Vector3[Double], to: Vector3[Double], showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, label: String): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(from.x, from.y, from.z, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double], colour: Colour): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], colour: Colour, label: String): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector3[Double], colour: Colour, showVectorInfo: Boolean, label: String): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = showVectorInfo, label = Some(label))

  def apply(to: Vector3[Double], colour: Colour, label: Option[String]): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = true, showVectorInfo = true, label = label)

  def apply(to: Vector3[Double], colour: Colour, showCone: Boolean): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(0, 0, 0, to.x, to.y, to.z, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double]): Line =
    Line(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector3[Double], showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(to: Vector3[Double], label: String): Line =
    Line(0, 0, 0, to.x, to.y, to.z, Colour.next(), showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double]): Line =
    Line(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(to: Vector2[Double], label: String): Line =
    Line(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double], colour: Colour): Line =
    Line(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double]): Line =
    Line(0, 0, 0, to.x, to.y, 0, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double], colour: Colour): Line =
    Line(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(from: Vector2[Double], to: Vector2[Double], colour: Colour, label: String): Line =
    Line(from.x, from.y, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(to: Vector2[Double], colour: Colour, label: String): Line =
    Line(0, 0, 0, to.x, to.y, 0, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double): Line =
    Line(fromX, fromY, 0.0d, toX, toY, 0.0d, Colour.next(), showCone = true, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour): Line =
    Line(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, label: String): Line =
    Line(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, showCone: Boolean): Line =
    Line(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = showCone, showVectorInfo = true, label = None)

  def apply(fromX: Double, fromY: Double, toX: Double, toY: Double, colour: Colour, showCone: Boolean, showVectorInfo: Boolean): Line =
    Line(fromX, fromY, 0.0d, toX, toY, 0.0d, colour, showCone = showCone, showVectorInfo = showVectorInfo, label = None)

  def apply(x: Double, y: Double, colour: Colour): Line =
    Line(0.0d, 0.0d, 0.0d, x, y, 0.0d, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(x: Double, y: Double, colour: Colour, text: String): Line =
    Line(0.0d, 0.0d, 0.0d, x, y, 0.0d, colour, showCone = true, showVectorInfo = true, label = Some(text))

  def apply(x: Double, y: Double, z: Double, colour: Colour): Line =
    Line(0.0d, 0.0d, 0.0d, x, y, z, colour, showCone = true, showVectorInfo = true, label = None)

  def apply(x: Double, y: Double, z: Double, colour: Colour, label: String): Line =
    Line(0.0d, 0.0d, 0.0d, x, y, z, colour, showCone = true, showVectorInfo = true, label = Some(label))

  def apply(fromX: Double, fromY: Double, fromZ: Double,
            toX: Double, toY: Double, toZ: Double,
            colour: Colour,
            showCone: Boolean,
            showVectorInfo: Boolean): Line =
    Line(
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
            label: String): Line =
    Line(
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
            label: Option[String]): Line = {
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

    new Line(
      from = from,
      to = to,
      colour = colour,
      cone = cone,
      text = text,
      showVectorInfo = showVectorInfo,
      label = label
    )
  }

  /**
   * Builds a list of lines from the given points.
   *
   * @param joinEnd if true adds a line for the head and last point
   */
  def join(points: Iterator[Point],
           joinEnd: Boolean,
           showCone: Boolean = true,
           showVectorInfo: Boolean = true): ListBuffer[Line] = {

    @tailrec
    def run(lines: ListBuffer[Line]): ListBuffer[Line] =
      lines.lastOption match {
        case Some(line) =>
          points.nextOption() match {
            case Some(point) =>
              lines +=
                Line(
                  from = line.to,
                  to = point.vector,
                  showCone = showCone,
                  showVectorInfo = showVectorInfo
                )
              run(lines)

            case None =>
              if (joinEnd)
                lines +=
                  Line(
                    from = line.to,
                    to = lines.head.from,
                    showCone = showCone,
                    showVectorInfo = showVectorInfo
                  )
              else
                lines
          }

        case None =>
          val line =
            for {
              first <- points.nextOption()
              second <- points.nextOption()
            } yield {
              Line(first, second, showCone, showVectorInfo)
            }

          line match {
            case Some(line) =>
              lines += line
              run(lines)

            case None =>
              throw new Exception(s"Points should have at least 2 element. Size: ${points.size}")
          }

        case None =>
          throw new Exception(s"Points should have at least 2 element. Size: ${points.size}")
      }

    run(ListBuffer.empty)
  }
}

case class Line private(from: Vector3[Double],
                        to: Vector3[Double],
                        colour: Colour,
                        cone: Option[Cone],
                        text: Option[Text],
                        showVectorInfo: Boolean,
                        label: Option[String]) extends LineOrRay {
  override type Self = Line

  def updateColour(colour: Colour): Line = {
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

  override def map(f: Vector3[Double] => Vector3[Double]): Line = {
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

    Line(
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

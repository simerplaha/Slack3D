package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay

object Cylinder {

  def apply(colour: Colour = Colour.next(),
            radius: Double = 1,
            sectors: Int = 20) = {

    val unitCircle: Circle = Circle.xz(colour, radius, sectors)

    val head = unitCircle + Vector3(0d, 0.5d, 0.0d)
    val base = unitCircle + Vector3(0d, -0.5d, 0.0d)

    val planes =
      head.triangles.zip(base.triangles) map {
        case (headTriangle, baseTriangle) =>
          //remove centroids from head and base triangles
          val headOther = headTriangle.vectors().filter(_.x != 0d)
          val baseOther = baseTriangle.vectors().filter(_.x != 0d)

          val headTrianglePlane =
            Triangle(baseOther.head +: headOther, colour, flipNormal = false)

          val baseTrianglePlane =
            Triangle(headOther.last +: baseOther, colour, flipNormal = true)

          Plane(
            triangleA = headTrianglePlane,
            triangleB = baseTrianglePlane,
            flipNormal = false
          )
      }

    new Cylinder(
      head = head.copy(flipNormal = true),
      base = base.copy(flipNormal = true),
      planes = planes
    )
  }
}

case class Cylinder private(head: Circle,
                            base: Circle,
                            planes: Array[Plane]) extends Shape {

  override type Self = Cylinder

  def radius(): Double =
    head.radius()

  def height(): Double =
    (head.center - base.center).length()

  def volume(): Double =
    head.area() * height()

  def surfaceArea(): Double =
    head.area() + base.area() + (height() * head.circumference())

  override def map(f: Vector3[Double] => Vector3[Double]): Cylinder =
    Cylinder(
      head = head.map(f),
      base = base.map(f),
      planes = planes.map(_.map(f))
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    head buildMesh mesh
    base buildMesh mesh
    planes foreach (_.buildMesh(mesh))
  }
}

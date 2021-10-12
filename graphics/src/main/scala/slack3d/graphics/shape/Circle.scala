package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import spire.implicits._

object Circle {

  def xy(colour: Colour = Colour.next(),
         radius: Double = 1,
         triangles: Int = 20,
         flipNormal: Boolean = false) = {
    val anglePerTriangle = (2 * Math.PI) / triangles

    val center = Vector3.origin[Double]()

    var lastVector: Vector3[Double] = null

    val vertices: Array[Vector3[Double]] =
      (0 to triangles).toArray flatMap {
        count =>
          val angle = count * anglePerTriangle
          val x = radius * Math.cos(angle)
          val y = radius * Math.sin(angle)
          val vector = Vector3(x, y, 0d)

          val array =
            if (count == 0)
              Array(center, vector)
            else if (count == 1)
              Array(vector)
            else
              Array(center, lastVector, vector)

          lastVector = vector

          array
      }

    val triangleShapes =
      vertices.grouped(3).toArray.map(Triangle(_, colour, flipNormal = flipNormal))

    new Circle(
      center = center,
      triangles = triangleShapes,
      flipNormal = flipNormal
    )
  }

  def xz(colour: Colour = Colour.next(),
         radius: Double = 1,
         triangles: Int = 20): Circle =
    Matrix3.rotatorX[Double](Math.toRadians(90d)) * Circle.xy(colour, radius, triangles, flipNormal = true)

  def yz(colour: Colour = Colour.next(),
         radius: Double = 1,
         triangles: Int = 20): Circle =
    Matrix3.rotatorY[Double](Math.toRadians(90d)) * Circle.xy(colour, radius, triangles)
}

case class Circle private(center: Vector3[Double],
                          triangles: Array[Triangle],
                          flipNormal: Boolean) extends NormalisableShape {

  override type Self = Circle

  def vectors(): Array[Vector3[Double]] =
    triangles.flatMap(_.vectors())

  def area(): Double =
    Maths.PI_FLOAT * Math.pow(radius(), 2)

  def circumference(): Double =
    2 * Maths.PI_FLOAT * radius()

  def radius(): Double = {
    val nonCenterVertices = center furthest triangles(0).vectors()
    (center - nonCenterVertices.get._1).length()
  }

  override def map(f: Vector3[Double] => Vector3[Double]): Circle =
    Circle(
      center = f(center),
      triangles = this.triangles.map(_.map(f)),
      flipNormal = flipNormal
    )

  override def normal(): Vector3[Double] =
    triangles.head.normal()

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    mesh.triangles ++= triangles
}

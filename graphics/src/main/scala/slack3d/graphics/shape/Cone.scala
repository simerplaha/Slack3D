package slack3d.graphics.shape

import slack3d.algebra.{Matrix3, Vector3}
import slack3d.algebra.util.LazyVal
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay
import spire.implicits._

object Cone {

  def apply(colour: Colour = Colour.next(),
            radius: Double = 1,
            sectors: Int = 20,
            yPoint: Double = 0.5d): Cone = {

    val unitCircle: Circle = Circle.xz(colour, radius, sectors)

    val point = Vector3[Double](0d, yPoint, 0.0d)
    val circle = unitCircle + Vector3[Double](0d, yPoint - 1, 0.0d)

    def planes() =
      circle.triangles map {
        triangle =>
          val baseOther = triangle.vectors().filter(_.x != 0d)
          Triangle(point +: baseOther, colour, flipNormal = true)
      }

    new Cone(Point(point), circle, LazyVal(planes()))
  }

  //render cone at direction - (from, to) is the direction vector
  def apply(from: Vector3[Double], to: Vector3[Double], colour: Colour): Cone = {
    //initial cone scale it down
    //yPoint = 0.0001d so that cone ends at the tip of the line. Cannot use 0 because rotator fails.
    val initialCone: Cone = Cone(sectors = 4, colour = colour, yPoint = 0.0001d) / 40

    //move the line to origin so there exist a vector in the same direction but sits at the origin.
    val originLine = to - from

    //find the rotation matrix matrix of the cones tip to origin vector's head
    val angle = initialCone.point.vector angleRadian originLine

    val rotation =
      if (angle == 0) { //collinear vectors
        Matrix3.identity[Double]()
      } else {
        val axis = initialCone.point.vector cross originLine
        if (axis.isOrigin())
          Matrix3.rotator(angle, axis)
        else
          Matrix3.rotator(angle, axis.normalise())
      }

    //perform rotation on the origin and shift the cone to the tip.
    (rotation * initialCone) + to
  }
}

case class Cone private(point: Point,
                        circle: Circle,
                        triangles: LazyVal[Array[Triangle]]) extends Shape {

  override type Self = Cone

  def radius() =
    circle.radius()

  override def map(f: Vector3[Double] => Vector3[Double]): Cone =
    Cone(
      point = point.map(f),
      circle = circle.map(f),
      triangles = triangles.map(_.map(_.map(f)))
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    circle buildMesh mesh
    mesh.triangles ++= triangles.get()
  }

}

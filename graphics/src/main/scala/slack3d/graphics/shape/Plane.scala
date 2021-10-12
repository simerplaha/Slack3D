package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay

object Plane {
  val Epsilon = 1e-8

  def xy(colour: Colour = Colour.next(),
         flipNormal: Boolean = false): Plane =
    new Plane(
      Triangle(
        a = Vector3[Double](-0.5d, -0.5d, 0.0d),
        b = Vector3[Double](0.5d, -0.5d, 0.0d),
        c = Vector3[Double](0.5d, 0.5d, 0.0d),
        colour = colour,
        flipNormal = flipNormal
      ),
      Triangle(
        a = Vector3[Double](0.5d, 0.5d, 0.0d),
        b = Vector3[Double](-0.5d, 0.5d, 0.0d),
        c = Vector3[Double](-0.5d, -0.5d, 0.0d),
        colour = colour,
        flipNormal = flipNormal
      ),
      flipNormal = flipNormal
    )

  def yz(colour: Colour = Colour.next(),
         flipNormal: Boolean = false): Plane =
    new Plane(
      Triangle(
        a = Vector3[Double](0.0d, 0.5d, 0.5d),
        b = Vector3[Double](0.0d, 0.5d, -0.5d),
        c = Vector3[Double](0.0d, -0.5d, -0.5d),
        colour = colour,
        flipNormal = flipNormal
      ),
      Triangle(
        a = Vector3[Double](0.0d, -0.5d, -0.5d),
        b = Vector3[Double](0.0d, -0.5d, 0.5d),
        c = Vector3[Double](0.0d, 0.5d, 0.5d),
        colour = colour,
        flipNormal = flipNormal
      ),
      flipNormal = flipNormal
    )

  def xz(colour: Colour = Colour.next(),
         flipNormal: Boolean = true): Plane =
    new Plane(
      Triangle(
        a = Vector3[Double](-0.5d, 0.0d, -0.5d),
        b = Vector3[Double](0.5d, 0.0d, -0.5d),
        c = Vector3[Double](0.5d, 0.0d, 0.5d),
        colour = colour,
        flipNormal = flipNormal
      ),
      Triangle(
        a = Vector3[Double](0.5d, 0.0d, 0.5d),
        b = Vector3[Double](-0.5d, 0.0d, 0.5d),
        c = Vector3[Double](-0.5d, 0.0d, -0.5d),
        colour = colour,
        flipNormal = flipNormal
      ),
      flipNormal = flipNormal
    )

  def apply(vectors: Array[Vector3[Double]]): Plane =
    Plane(
      vectors = vectors,
      colour = Colour.next(),
      flipNormal = false
    )

  def apply(vectors: Array[Vector3[Double]],
            colour: Colour): Plane =
    Plane(
      vectors = vectors,
      colour = colour,
      flipNormal = false
    )

  def apply(vectors: Array[Vector3[Double]],
            flipNormal: Boolean): Plane =
    Plane(
      vectors = vectors,
      colour = Colour.next(),
      flipNormal = flipNormal
    )

  def apply(vectors: Array[Vector3[Double]],
            colour: Colour,
            flipNormal: Boolean): Plane =
    if (vectors.length == 3)
      Plane(
        a = vectors(0),
        b = vectors(1),
        c = vectors(2),
        colour = colour,
        flipNormal = flipNormal
      )
    else if (vectors.length == 4)
      Plane(
        a = vectors(0),
        b = vectors(1),
        c = vectors(2),
        d = vectors(3),
        colour = colour,
        flipNormal = flipNormal
      )
    else
      throw new Exception(s"Invalid vectors: ${vectors.length}")

  def apply(a: Vector3[Double],
            b: Vector3[Double],
            c: Vector3[Double],
            d: Vector3[Double],
            colour: Colour,
            flipNormal: Boolean): Plane = {
    val triangle1 = Triangle(a, b, c, colour, flipNormal)
    val triangle2 = Triangle(c, d, a, colour, flipNormal)

    Plane(
      triangleA = triangle1,
      triangleB = triangle2,
      flipNormal = flipNormal
    )
  }

  def apply(triangle: Triangle,
            flipNormal: Boolean): Plane =
    Plane(
      a = triangle.a,
      b = triangle.b,
      c = triangle.c,
      colour = triangle.colour,
      flipNormal = flipNormal
    )

  def apply(a: Vector3[Double],
            b: Vector3[Double],
            c: Vector3[Double],
            colour: Colour,
            flipNormal: Boolean): Plane = {
    val vectorA = b - a
    val vectorB = c - a

    val triangle2sA = a + vectorA + vectorB

    val triangle1 = Triangle(a, b, c, colour, flipNormal)
    val triangle2 = Triangle(triangle2sA, b, c, colour, flipNormal)

    Plane(
      triangleA = triangle1,
      triangleB = triangle2,
      flipNormal = flipNormal
    )
  }
}

case class Plane(triangleA: Triangle,
                 triangleB: Triangle,
                 flipNormal: Boolean) extends NormalisableShape {

  private def copy(): Unit = ???

  override type Self = Plane

  def triangles(): Array[Triangle] =
    Array(triangleA, triangleB)

  def vectors(): Array[Vector3[Double]] =
    triangleA.vectors() ++ triangleB.vectors()

  override def normal(): Vector3[Double] =
    triangleA.normal()

  def center(): Vector3[Double] =
    (triangleA.center() + triangleB.center()) / 2d

  def distance(): Double =
    this.normal() dot triangleB.a

  def distance(normal: Vector3[Double]): Double =
    normal dot triangleB.a

  def distanceNegate(): Double =
    -distance()

  def distanceExact(): Double =
    this.normal().normalise() dot triangleB.a

  def distanceApplyOrigin(): Double =
    this.distance() / this.normal().length()

  override def map(f: Vector3[Double] => Vector3[Double]): Plane =
    Plane(
      triangleA = triangleA.map(f),
      triangleB = triangleB.map(f),
      flipNormal = flipNormal
    )

  def updateColour(colour: Colour): Plane =
    Plane(
      triangleA = triangleA.copy(colour = colour),
      triangleB = triangleB.copy(colour = colour),
      flipNormal = flipNormal
    )

  def setFlipNormal(flip: Boolean): Plane =
    Plane(
      triangleA = triangleA.copy(flipNormal = flip),
      triangleB = triangleB.copy(flipNormal = flip),
      flipNormal = flip
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.triangles += triangleA
    mesh.triangles += triangleB
  }
}

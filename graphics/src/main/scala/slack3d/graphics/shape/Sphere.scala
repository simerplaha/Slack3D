package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.algebra.util.{LazyVal, Maths}
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay

import scala.collection.mutable.ListBuffer

object Sphere {

  def apply(radius: Double,
            center: Vector3[Double] = Vector3.origin(),
            colour: Colour = Colour.next(),
            sectors: Int = 16,
            stacks: Int = 8) = {

    /**
     * Find the point in the stack and sector
     */
    def vectorFor(stack: Double, sector: Double): Vector3[Double] = {
      val wideAngle = (sector / sectors) * Math.PI * 2
      val heightAngle = (stack / stacks) * Math.PI
      Vector3[Double](
        x = center.x + radius * Math.cos(wideAngle) * Math.sin(heightAngle),
        y = center.y + radius * Math.cos(heightAngle),
        z = center.z + radius * Math.sin(wideAngle) * Math.sin(heightAngle)
      )
    }

    val north: Vector3[Double] = vectorFor(0, 0) //north pone
    val south: Vector3[Double] = vectorFor(stacks, sectors) //south pole

    def lazyTriangles(): Iterable[Triangle] = {
      val triangles = ListBuffer.empty[Triangle]

      (0 until stacks) foreach {
        stack =>
          (0 until sectors) foreach {
            sector =>
              if (stack == stacks - 1) { //handle last stack (south pole)
                val headRight = vectorFor(stack, sector)
                val headLeft = vectorFor(stack, sector + 1)

                triangles += Triangle(south, headLeft, headRight, colour, flipNormal = true)
              } else {
                val head = vectorFor(stack, sector)
                val bottomRight = vectorFor(stack + 1, sector)
                val bottomLeft = vectorFor(stack + 1, sector + 1)
                val triangle1 = Triangle(head, bottomLeft, bottomRight, colour, flipNormal = false)

                //head stack
                triangles += triangle1

                //if it's not the north pole then create a parallelogram
                if (stack != 0) {
                  val nextSectorHead = vectorFor(stack, sector + 1)

                  triangles += Triangle(bottomLeft, head, nextSectorHead, colour, flipNormal = false)
                }
              }
          }
      }

      triangles
    }

    new Sphere(
      center = center,
      north = Point(vector = north, colour = colour),
      south = Point(vector = south, colour = colour),
      triangles = LazyVal(lazyTriangles()),
      colour = colour,
      sectors = sectors,
      stacks = stacks
    )
  }
}

class Sphere private(val center: Vector3[Double],
                     val north: Point,
                     val south: Point,
                     val triangles: LazyVal[Iterable[Triangle]],
                     val colour: Colour,
                     val sectors: Int,
                     val stacks: Int) extends Shape { self =>

  override type Self = Sphere

  val diameter: Double =
    Maths.abs((north.vector - south.vector).length())

  val radius: Double =
    diameter / 2

  def merge(other: Sphere): Sphere = {
    val dir = other.center - self.center
    val norm = dir.normalise()

    if (norm.isZero()) {
      if (other.radius > self.radius)
        Sphere(other.radius, this.center)
      else
        this
    } else {
      val s_center_dir = self.center.dot(dir)
      val o_center_dir = other.center.dot(dir)

      val right =
        if (s_center_dir + self.radius > o_center_dir + other.radius)
          self.center + dir * self.radius
        else
          other.center + dir * other.radius

      val left =
        if (-s_center_dir + self.radius > -o_center_dir + other.radius)
          self.center - dir * self.radius
        else
          other.center - dir * other.radius

      Sphere(
        right.distance(self.center),
        left.center(right),
      )
    }
  }

  override def map(f: Vector3[Double] => Vector3[Double]): Sphere =
    new Sphere(
      center = f(center),
      north = south.map(f),
      south = north.map(f),
      triangles = this.triangles.map(_.map(_.map(f))),
      colour = colour,
      sectors = sectors,
      stacks = stacks
    )

  def updateColour(colour: Colour): Sphere =
    Sphere(
      radius = radius,
      center = center,
      colour = colour,
      sectors = sectors,
      stacks = stacks
    )

  def update(radius: Double = self.radius, center: Vector3[Double] = self.center): Sphere =
    Sphere(
      radius = radius,
      center = center,
      colour = colour,
      sectors = sectors,
      stacks = stacks
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    mesh.triangles ++= this.triangles.get()
}

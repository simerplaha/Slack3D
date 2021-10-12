package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.algebra.util.LazyVal
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.LineOrRay

object HeightField {

  /**
   *
   * @param heights An array of numbers, or height values, that are spread out along the x axis.
   * @param space   World spacing between the data points in X direction.
   */
  def getTriangle(x: Int,
                  y: Int,
                  upper: Boolean,
                  colour: Colour,
                  flipNormal: Boolean,
                  heights: Array[Array[Double]],
                  space: Int): Triangle =
    if (upper)
      Triangle(
        a = Vector3((x + 1) * space, heights(x + 1)(y + 1), (y + 1) * space * -1),
        b = Vector3(x * space, heights(x)(y + 1), (y + 1) * space * -1),
        c = Vector3((x + 1) * space, heights(x + 1)(y), y * space * -1),
        colour = colour,
        flipNormal = flipNormal
      )
    else
      Triangle(
        a = Vector3(x * space, heights(x)(y), y * space * -1),
        b = Vector3((x + 1) * space, heights(x + 1)(y), y * space * -1),
        c = Vector3(x * space, heights(x)(y + 1), (y + 1) * space * -1),
        colour = colour,
        flipNormal = flipNormal
      )

  def apply(sizeX: Int = 15,
            sizeZ: Int = 15,
            space: Int = 1,
            colour: Colour = Colour.next(),
            flipNormal: Boolean = false): HeightField = {

    val heights = Array.ofDim[Double](sizeX, sizeZ)

    for (i <- 0 until sizeX)
      for (j <- 0 until sizeZ)
        if (i == 0 || i == sizeX - 1 || j == 0 || j == sizeZ - 1)
          heights(i)(j) = 0
        else
          heights(i)(j) = Math.cos((i.toDouble / sizeX) * Math.PI * 2) * Math.cos((j.toDouble / sizeZ) * Math.PI * 2) - 1

    def triangles(): Array[Triangle] = {
      val triangles = new Array[Triangle](((sizeX - 1) * (sizeZ - 1)) * 2)
      var index = 0

      for (i <- 0 until sizeX - 1)
        for (j <- 0 until sizeZ - 1) {
          triangles(index) = getTriangle(i, j, true, colour, flipNormal, heights, space)
          triangles(index + 1) = getTriangle(i, j, false, colour, flipNormal, heights, space)
          index += 2
        }

      triangles
    }

    HeightField(
      heights = heights,
      triangles = LazyVal(triangles())
    )
  }
}

case class HeightField(heights: Array[Array[Double]],
                       triangles: LazyVal[Array[Triangle]]) extends Shape {

  override type Self = HeightField

  override def map(f: Vector3[Double] => Vector3[Double]): HeightField =
    HeightField(heights, triangles.map(_.map(_.map(f))))

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit =
    mesh.triangles addAll triangles.get()
}

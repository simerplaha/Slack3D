package slack3d.graphics


import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoxSpec extends AnyWordSpec with Matchers {

  "cube" in {
    Play3D(Box())
  }

  "unit cube" in {
    def doTest(scale: Double) = {
      val cube = Box() * scale

      cube.center().round(2) shouldBe Vector3.origin[Double]()
      cube.width() shouldBe scale
      cube.height() shouldBe scale
      cube.depth() shouldBe scale
    }

    (1 to 10).map(_.toDouble / 10d) foreach doTest
    (1 to 10).map(_.toDouble) foreach doTest
  }

  "translating should not change cube dimensions" in {
    (1 to 100) foreach {
      i =>
        val cube = Box() + Vector3(randomDoublePosOrNeg(), randomDoublePosOrNeg(), randomDoublePosOrNeg())

        val width = cube.width()
        val height = cube.height()
        val depth = cube.depth()

        width.roundNum() shouldBe 1
        height.roundNum() shouldBe 1
        depth.roundNum() shouldBe 1
    }
  }

  "half widths" in {
    val box =
      Box(widths = Vector3(0.5d, 0.5d, 0.5d))

    val normals =
      Seq(
        box.left.triangleA.normalLineFromCentre("Left A"),
        box.left.triangleB.normalLineFromCentre("Left B"),
        box.right.triangleA.normalLineFromCentre("Right A"),
        box.right.triangleB.normalLineFromCentre("Right B"),
        box.front.triangleA.normalLineFromCentre("Front A"),
        box.front.triangleB.normalLineFromCentre("Front B"),
        box.back.triangleA.normalLineFromCentre("Back A"),
        box.back.triangleB.normalLineFromCentre("Back B"),
        box.top.triangleA.normalLineFromCentre("Top A"),
        box.top.triangleB.normalLineFromCentre("Top B"),
        box.bottom.triangleA.normalLineFromCentre("Bottom A"),
        box.bottom.triangleB.normalLineFromCentre("Bottom B"),
      )

    val shapes = normals ++ Seq(box)
    Play3D(shapes: _*)


  }
}

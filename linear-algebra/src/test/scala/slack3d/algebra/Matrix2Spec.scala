package slack3d.algebra

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import spire.implicits._

class Matrix2Spec extends AnyWordSpec with Matchers {

  "resolveCramer" in {
    val matrix =
      Matrix2(
        1, 3,
        2, 2
      )

    val result = matrix solveCramer Vector2(5, 6)

    result shouldBe Vector2(2, 1)
  }
}

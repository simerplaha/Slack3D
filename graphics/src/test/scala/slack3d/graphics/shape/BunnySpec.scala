package slack3d.graphics.shape

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import slack3d.graphics.Slack3D

class BunnySpec extends AnyWordSpec with Matchers {

  "bunny" in {
    val bunny = Bunny() * 5

    Slack3D("", enable2DCoordinates = false).foldLeft(bunny) {
      case (_bunny, state) =>
        val bunny = _bunny.rotateY(state.getTime() / 15)
        (bunny, Seq(bunny))
    }
  }
}

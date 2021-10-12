package slack3d.graphics.shape

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import slack3d.graphics.Slack3D

class BunnySpec extends AnyWordSpec with Matchers {

  "bunny" in {
    val bunny = Bunny() * 20

    Slack3D("").foldLeft(bunny) {
      case (_bunny, state) =>
        val bunny = _bunny.rotatable(state.window, None, 0.6d)
        (bunny, Seq(bunny))
    }
  }
}

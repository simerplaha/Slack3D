package slack3d.examples

import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Bunny

object BunnyRotateExample extends App {

  Slack3D("Bunny") foreach {
    state =>
      val bunny = Bunny(Colour.Purple).rotateY(state.getTime() * 20) * 10
      Seq(bunny)
  }

}

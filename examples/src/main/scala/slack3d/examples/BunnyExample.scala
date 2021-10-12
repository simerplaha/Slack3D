package slack3d.examples

import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Bunny

object BunnyExample extends App {

  Slack3D("Bunny") foreach {
    state =>
      //render a scaled bunny
      Seq(Bunny(Colour.Purple) * 10)
  }

}

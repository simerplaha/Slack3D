package slack3d.examples

import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Box

object RotatingBoxExample extends App{

  Slack3D("Rotating Box") foreach {
    state =>
      //same code as above but with added rotation at Y axis
      val box = Box(Colour.Red).rotateY(state.getTime() * 50)
      Seq(box)
  }

}

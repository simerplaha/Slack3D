package slack3d.examples

import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.Sphere

import scala.concurrent.duration.DurationInt

object InternalRenderingExample extends App {

  Slack3D("Interval").foreach(interval = 1.second) {
    state =>
      Seq(Sphere(0.5, colour = Colour.next()))
  }

}

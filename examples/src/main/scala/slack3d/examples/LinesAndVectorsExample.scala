package slack3d.examples

import slack3d.algebra.Vector3
import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.Line

object LinesAndVectorsExample extends App {

  Slack3D("Lines and vectors") foreach {
    state =>
      //vector1
      val vector1 = Vector3(0.5, -0.5, 0)
      //vector2
      val vector2 = Vector3(0.5, 0.5, 0)
      //cross product
      val cross = vector1 cross vector2

      Seq(
        Line(vector1, Colour.Red),
        Line(vector2, Colour.Yellow),
        Line(cross, Colour.Green)
      )
  }

}

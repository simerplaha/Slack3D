package slack3d.examples

import slack3d.algebra.Vector3
import slack3d.graphics.Slack3D
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.{Cylinder, Mesh, Point, Shape, Text, Triangle}
import slack3d.graphics.shape.line.LineOrRay

object CustomShapesExample extends App {

  /**
   * A custom shape that render text and a line
   */
  case class MyCustomShape(text: Text,
                           line: Cylinder) extends Shape {

    override type Self = MyCustomShape

    //function to apply to each vector on this shape.
    //this function is used for applying perspective, rotation & translation
    override def map(f: Vector3[Double] => Vector3[Double]): MyCustomShape =
      MyCustomShape(
        text = text.map(f),
        line = line.map(f)
      )

    //expands each shape into points, lines and triangles
    override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
      text buildMesh mesh
      line buildMesh mesh
    }
  }

  //Start Slack3D instance and render the above custom shape
  Slack3D("My custom shape") foreach {
    state =>
      //create my custom shape instance
      val myShape =
        MyCustomShape(
          text = Text("My custom shape text", Colour.White) * 2 + Vector3(-0.6, 0.4), //a scaled & translated custom text
          line = Cylinder(Colour.Purple) / 2 //A scaled down cylinder
        )

      Seq(myShape)
  }

}

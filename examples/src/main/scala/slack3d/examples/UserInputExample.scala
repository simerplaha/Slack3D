package slack3d.examples

import org.lwjgl.glfw.GLFW
import slack3d.graphics.Slack3D
import slack3d.graphics.shape.Box

object UserInputExample extends App {

  Slack3D("Custom rotation").foldLeft(Box()) {
    case (_box, state) =>
      val box =
        _box
          .translatable(state.window, GLFW.GLFW_KEY_Z) //translate box when Z key is pressed
          .rotatable(state.window, GLFW.GLFW_KEY_X) //rotate box when X key is pressed

      (box, Seq(box))
  }

}

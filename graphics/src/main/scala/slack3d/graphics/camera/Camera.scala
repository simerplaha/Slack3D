/*
 * Copyright 2021 Simer JS Plaha (simer.j@gmail.com - @simerplaha)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package slack3d.graphics.camera

import org.lwjgl.glfw.GLFW
import slack3d.algebra.Vector3
import slack3d.graphics.window.Window

case class Camera(up: Vector3[Double] = Vector3(0.0d, 1.0d, 0.0d),
                  var position: Vector3[Double] = Vector3(0.0d, 0.0d, 3.0d),
                  var front: Vector3[Double] = Vector3(0.0d, 0.0d, -1.0d),
                  var right: Vector3[Double] = Vector3(0.0d, 0.0d, 0.0d),
                  var firstMouseInput: Boolean = true,
                  private var yaw: Double = -90.0d,
                  private var pitch: Double = 0.0d,
                  private var speed: Double = 2.5d,
                  private var sensitivity: Double = 0.1d,
                  private var lastX: Double = 0,
                  private var lastY: Double = 0) {

  private def resetMouseInput() =
    firstMouseInput = true

  private def resetCamera() = {
    yaw = -90.0d
    pitch = 0.0d
    position = Vector3(0.0d, 0.0d, 3.0d)
    front = Vector3(0.0d, 0.0d, -1.0d)
    right = Vector3(0.0d, 0.0d, 0.0d)
    resetMouseInput()
  }

  def attachCursorCallback(window: Window): Camera = {
    GLFW.glfwSetCursorPosCallback(
      window.id,
      (window: Long, xpos: Double, ypos: Double) =>
        if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS)
          mouseMoved(xPos = xpos, yPos = ypos)
        else
          resetMouseInput()
    )

    this
  }

  def processKeyboardInput(deltaTime: Double,
                           window: Window) = {
    var cameraSpeed = speed * deltaTime
    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS)
      cameraSpeed *= 2.0d

    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
      position = position + (front * cameraSpeed)

    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
      position = position - (front * cameraSpeed)

    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
      val pos = front.cross(up).normalise()
      position = position - (pos * cameraSpeed)
    }

    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
      val pos = front.cross(up).normalise()
      position = position + (pos * cameraSpeed)
    }

    if (GLFW.glfwGetKey(window.id, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS)
      resetCamera()
  }

  def mouseMoved(xPos: Double, yPos: Double): Unit = {
    if (firstMouseInput) {
      lastX = xPos
      lastY = yPos
      firstMouseInput = false
    }

    var xOffset = xPos - lastX
    var yOffset = lastY - yPos
    lastX = xPos
    lastY = yPos

    val sensitivity = 0.7d
    xOffset *= sensitivity
    yOffset *= sensitivity

    yaw += xOffset
    pitch += yOffset

    if (pitch > 89.0d)
      pitch = 89.0d
    else if (pitch < -89.0d)
      pitch = -89.0d

    val x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
    val y = Math.sin(Math.toRadians(pitch))
    val z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
    this.front = Vector3(x, y, z).normalise()
  }
}

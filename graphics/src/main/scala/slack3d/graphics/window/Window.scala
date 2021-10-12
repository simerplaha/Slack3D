package slack3d.graphics.window

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.{GL, GL11}
import org.lwjgl.system.{MemoryUtil, Platform}

object Window {

  def apply(title: String,
            width: Int,
            height: Int) = {
    if (!GLFW.glfwInit())
      throw new Exception("Failed to start GLFW")

    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3)
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
    GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 0)

    if (Platform.get() == Platform.MACOSX)
      GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)

    val windowId = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)

    if (windowId == MemoryUtil.NULL) {
      GLFW.glfwTerminate()
      throw new Exception("Failed to create GLFW Window")
    } else {
      GLFW.glfwMakeContextCurrent(windowId)

      val gl = GL.createCapabilities()

      if (gl == null) {
        GLFW.glfwTerminate()
        throw new Exception("Failed to initialise OpenGL")
      } else {
        new Window(
          id = windowId,
          width = width,
          height = height
        )
      }
    }
  }
}

private[play3d] class Window private(val id: Long,
                                     var width: Int,
                                     var height: Int,
                                     var paused: Boolean = false,
                                     private var pauseTime: Double = 0) extends AutoCloseable {

  GLFW.glfwSetFramebufferSizeCallback(
    id,
    (window: Long, width: Int, height: Int) => {
      this.width = width
      this.height = height
      GL11.glViewport(0, 0, width, height)
    }
  )

  def mousePosition: (Double, Double) = {
    val x = new Array[Double](1)
    val y = new Array[Double](1)
    GLFW.glfwGetCursorPos(this.id, x, y)
    (x.head, y.head)
  }

  def keyPressed(glfwKey: Int): Boolean =
    GLFW.glfwGetKey(this.id, glfwKey) == GLFW.GLFW_PRESS

  def leftPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS

  def rightPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS

  def upPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS

  def downPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS

  def shiftUpPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS &&
      GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS

  def shiftDownPressed(): Boolean =
    GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS &&
      GLFW.glfwGetKey(this.id, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS


  def processKeyboardInput() = {
    if (GLFW.glfwGetKey(id, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
      GLFW.glfwSetWindowShouldClose(id, true)

    val space = GLFW.glfwGetKey(id, GLFW.GLFW_KEY_SPACE)

    if (!paused && space == GLFW.GLFW_PRESS) {
      paused = true
      pauseTime = GLFW.glfwGetTime()
    } else if (paused && space == GLFW.GLFW_RELEASE) {
      paused = false
      GLFW.glfwSetTime(pauseTime)
    }
  }

  def setWindowShouldClose(bool: Boolean): Unit =
    GLFW.glfwSetWindowShouldClose(id, bool)

  override def close(): Unit =
    GLFW.glfwTerminate()
}

package slack3d.graphics.shader

import com.typesafe.scalalogging.LazyLogging
import org.lwjgl.opengl.{GL11, GL20}

object ShaderProgram extends LazyLogging {

  def apply(shaders: Shader*): ShaderProgram = {
    val programId = GL20.glCreateProgram()

    shaders foreach {
      shader =>
        GL20.glAttachShader(programId, shader.id)
    }

    GL20.glLinkProgram(programId)

    try {
      fetchLinkingErrors(programId) match {
        case Some(value) =>
          throw new Exception(value)

        case None =>
          shaders.foreach(_.delete())

          new ShaderProgram(
            id = programId,
            shaders = shaders
          )
      }
    } catch {
      case throwable: Throwable =>
        shaders.foreach(_.delete())
        throw throwable
    }
  }

  private def fetchLinkingErrors(programId: Int): Option[String] = {
    val success = new Array[Int](1)
    GL20.glGetProgramiv(programId, GL20.GL_LINK_STATUS, success)

    if (success.head == GL11.GL_FALSE) {
      val log = GL20.glGetProgramInfoLog(programId)
      val error = s"Shader linking error failed: $log"
      logger.error(error)
      Some(error)
    } else {
      None
    }
  }
}

class ShaderProgram(val id: Int,
                    shaders: Seq[Shader]) {

  def use(): Unit =
    GL20.glUseProgram(id)
}

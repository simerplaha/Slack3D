package slack3d.graphics.shader


import com.typesafe.scalalogging.LazyLogging
import org.lwjgl.opengl.{GL11, GL20}

import scala.collection.IndexedSeqView.Slice
import scala.util.{Failure, Success}

object Shader extends LazyLogging {

  def apply(shaderType: ShaderType, shaderSources: String*): IO[Throwable, Slice[Shader]] =
    shaderSources
      .mapRecoverIO[Shader](
        block =
          source =>
            compile(
              shaderType = shaderType,
              source = source
            ),
        recover =
          (slice, _) =>
            slice.foreach(_.delete())
      )

  private def compile(shaderType: ShaderType, source: String): IO[Throwable, Shader] = {
    val shaderGLType =
      shaderType match {
        case ShaderType.Vertex =>
          GL20.GL_VERTEX_SHADER

        case ShaderType.Fragment =>
          GL20.GL_FRAGMENT_SHADER
      }

    val shaderId = GL20.glCreateShader(shaderGLType)

    IO.fromTry {
      GL20.glShaderSource(shaderId, source)
      GL20.glCompileShader(shaderId)
      fetchCompileErrors(shaderId) match {
        case Some(value) =>
          Failure(new Exception(value))

        case None =>
          Success(new Shader(shaderType, shaderId))
      }
    } onLeftSideEffect {
      _ =>
        GL20.glDeleteShader(shaderId)
    }
  }

  private def fetchCompileErrors(shaderId: Int): Option[String] = {
    val success = new Array[Int](1)
    GL20.glGetShaderiv(shaderId, GL20.GL_COMPILE_STATUS, success)

    if (success.head == GL11.GL_FALSE) {
      val log = GL20.glGetShaderInfoLog(shaderId)
      val error = s"Shader compilation failed: $log"
      logger.error(error)
      Some(error)
    } else {
      None
    }
  }
}

class Shader(val shaderType: ShaderType, val id: Int) extends AutoCloseable {
  override def close(): Unit =
    delete()

  def delete() =
    GL20.glDeleteShader(id)
}

package slack3d.graphics.shader


import com.typesafe.scalalogging.LazyLogging
import org.lwjgl.opengl.{GL11, GL20}

import scala.collection.IndexedSeqView.Slice
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

object Shader extends LazyLogging {

  def apply(shaderType: ShaderType, shaderSources: String*): Try[List[Shader]] = {
    val compiled = ListBuffer.empty[Shader]

    Try {
      shaderSources foreach {
        source =>
          compiled +=
            compile(
              shaderType = shaderType,
              source = source
            )
      }
      compiled.toList

    } recoverWith {
      error =>
        compiled.foreach(_.delete())
        Failure(error)
    }
  }

  private def compile(shaderType: ShaderType, source: String): Shader = {
    val shaderGLType =
      shaderType match {
        case ShaderType.Vertex =>
          GL20.GL_VERTEX_SHADER

        case ShaderType.Fragment =>
          GL20.GL_FRAGMENT_SHADER
      }

    val shaderId = GL20.glCreateShader(shaderGLType)

    try {
      GL20.glShaderSource(shaderId, source)
      GL20.glCompileShader(shaderId)
      fetchCompileErrors(shaderId) match {
        case Some(value) =>
          throw new Exception(value)

        case None =>
          new Shader(shaderType, shaderId)
      }
    } catch {
      case exception: Throwable =>
        GL20.glDeleteShader(shaderId)
        throw exception
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

package slack3d

import slack3d.graphics.shader.{Shader, ShaderDefaults, ShaderProgram, ShaderType}

import scala.util.Try

object Core {

  def initialiseDefaultShaders(): Try[ShaderProgram] =
    buildShaderProgram(
      vertexShader = ShaderDefaults.vertex,
      fragmentShader = ShaderDefaults.fragment
    ).map(_.use())

  def buildShaderProgram(vertexShader: String,
                         fragmentShader: String): Try[ShaderProgram] =
    Shader(
      shaderType = ShaderType.Vertex,
      shaderSources = vertexShader
    ) flatMap {
      vertexShader =>
        Shader(
          shaderType = ShaderType.Fragment,
          shaderSources = fragmentShader
        ) map {
          fragmentShader =>
            vertexShader ++ fragmentShader
        }
    } flatMap {
      shaders =>
        ShaderProgram(shaders: _*)
    }

  def buildShaderProgram(shaderType: ShaderType, sources: String*): Try[ShaderProgram] =
    Shader(
      shaderType = shaderType,
      shaderSources = sources: _*
    ) flatMap {
      shaders =>
        ShaderProgram(shaders: _*)
    }
}

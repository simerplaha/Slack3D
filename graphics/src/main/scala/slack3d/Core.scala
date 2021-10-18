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

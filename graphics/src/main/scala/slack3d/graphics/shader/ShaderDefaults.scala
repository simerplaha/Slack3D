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

package slack3d.graphics.shader

object ShaderDefaults {

  val vertex =
    s"""#version 330 core
       |layout (location = 0) in vec4 aPos;
       |layout (location = 1) in vec4 aColour;
       |
       |out vec4 outColour;
       |
       |void main() {
       |    gl_Position = aPos;
       |    outColour = aColour;
       |}
       |""".stripMargin


  val fragment =
    s"""#version 330 core
       |
       |out vec4 FragColor;
       |in vec4 outColour;
       |
       |void main() {
       |  FragColor = outColour;
       |}
       |""".stripMargin

}

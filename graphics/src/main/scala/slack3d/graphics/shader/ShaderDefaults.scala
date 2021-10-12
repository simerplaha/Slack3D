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

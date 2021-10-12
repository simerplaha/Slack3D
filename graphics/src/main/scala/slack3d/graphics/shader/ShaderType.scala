package slack3d.graphics.shader

sealed trait ShaderType
object ShaderType {
  sealed trait Vertex extends ShaderType
  case object Vertex extends Vertex

  sealed trait Fragment extends ShaderType
  case object Fragment extends Fragment
}

package slack3d.graphics.light

import slack3d.algebra.Vector3
import slack3d.graphics.camera.Camera
import slack3d.graphics.shape.Triangle
import slack3d.graphics.vertex.Vertex

trait LightProjector {

  def apply(camera: Option[Camera],
            light: Light,
            triangle: Triangle): Array[Vertex.Triangle[Vector3[Double]]]

}

object LightProjector {

  case object PhongLightProjector extends LightProjector {

    def apply(camera: Option[Camera],
              light: Light,
              triangle: Triangle): Array[Vertex.Triangle[Vector3[Double]]] = {
      val normal = triangle.normal().normalise()

      triangle.vectors() map {
        vector =>
          //diffuse
          val lightDirection = (light.position - vector).normalise()
          val diff = (normal dot lightDirection) max 0.0d
          val diffuse = light.colour * diff

          camera match {
            case Some(camera) =>
              //specular
              val viewDir = (camera.position - vector).normalise()
              val reflectionDir = lightDirection.negate() reflectOpenGL normal
              val spec = Math.pow((viewDir dot reflectionDir) max 0.0d, 32)
              val specular = light.specularStrength * spec * light.colour

              val result = (light.ambient + diffuse + specular) * triangle.colour
              Vertex.Triangle(vector, result)

            case None =>
              val result = triangle.colour * (light.ambient + diffuse)
              Vertex.Triangle(vector, result)
          }
      }
    }
  }
}

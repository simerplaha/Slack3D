package slack3d.graphics.camera

case class Perspective(fov: Double = 45.0d,
                       zNear: Double = 0.1d,
                       zFar: Double = 100.0d)

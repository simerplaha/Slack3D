package slack3d.graphics.shape

import slack3d.algebra.{Isometry3, Matrix3, Vector3}
import slack3d.graphics.window.Window
import spire.implicits._

object Shape {

  implicit class Matrix3Implicits(matrix: Matrix3[Double]) {
    @inline def *[S <: Shape](shape: S): S =
      shape.map {
        vector =>
          matrix * vector
      }.asInstanceOf[S]
  }

  implicit class Isometry3Implicits(isometry: Isometry3[Double]) {
    @inline def *[S <: Shape](shape: S): S =
      shape.map {
        vec =>
          isometry.isometries().foldLeft(vec) {
            (vector, isometry) =>
              (isometry.rotation * vector) + isometry.translation
          }
      }.asInstanceOf[S]
  }
}

trait Shape extends Meshable {

  type Self <: Shape

  def map(f: Vector3[Double] => Vector3[Double]): Self

  def *(scalar: Double) =
    map(_ * scalar)

  def /(scalar: Double) =
    map(_ / scalar)

  def /(vec: Vector3[Double]) =
    map(_ / vec)

  def +(scalar: Double) =
    map(_ + scalar)

  def +(vec: Vector3[Double]): Self =
    map(_ + vec)

  def -(vec: Vector3[Double]): Self =
    map(_ - vec)

  def rotateRadianZ(angle: Double): Self =
    Matrix3.rotatorZ[Double](angle) * this.asInstanceOf[Self]

  def rotateRadianX(angle: Double): Self =
    Matrix3.rotatorX[Double](angle) * this.asInstanceOf[Self]

  def rotateRadianY(angle: Double): Self =
    Matrix3.rotatorY[Double](angle) * this.asInstanceOf[Self]

  def rotateZ(angle: Double): Self =
    rotateRadianZ(Math.toRadians(angle))

  def rotateX(angle: Double): Self =
    rotateRadianX(Math.toRadians(angle))

  def rotateY(angle: Double): Self =
    rotateRadianY(Math.toRadians(angle))

  def rotateRadian(angle: Double, axis: Vector3[Double]): Self =
    Matrix3.rotator[Double](angle, axis) * this.asInstanceOf[Self]

  def rotate(angle: Double, axis: Vector3[Double]): Self =
    rotateRadian(Math.toRadians(angle), axis)

  def translatable(window: Window,
                   optionalKey: Int): Self =
    translatable(
      window = window,
      optionalKey = Some(optionalKey)
    )

  def translatable(window: Window,
                   optionalKey: Option[Int] = None,
                   translateFactor: Double = 0.0006d): Self =
    move(window = window, optionalKey = optionalKey, translateFactor = translateFactor) match {
      case Some(vector) =>
        this + vector

      case None =>
        this.asInstanceOf[Self]
    }

  def rotatable(window: Window,
                optionalKey: Int): Self =
    rotatable(window, Some(optionalKey))

  def rotatable(window: Window,
                optionalKey: Option[Int] = None,
                rotateFactor: Double = 0.1d): Self =
    move(window = window, optionalKey = optionalKey, translateFactor = rotateFactor) match {
      case Some(vector) =>
        if (vector.isOrigin()) {
          this.asInstanceOf[Self]
        } else {
          val rotationX = Matrix3.rotatorX[Double](Math.toRadians(-vector.y))
          val rotationZ = Matrix3.rotatorZ[Double](Math.toRadians(-vector.x))
          (rotationX * rotationZ * this).asInstanceOf[Self]
        }

      case None =>
        this.asInstanceOf[Self]
    }

  /**
   * Sets this shape to be movable. Always input previous frame's rendered shape
   * for the move to occur.
   *
   * @param window          GLFW window
   * @param optionalKey     the key required to be pressed for this move to occur
   *                        Eg: setting this to GLFW.GLFW_KEY_LEFT_ALT will enable
   *                        move only if option is pressed
   * @param translateFactor how much to move by
   * @return
   */
  private def move(window: Window,
                   optionalKey: Option[Int],
                   translateFactor: Double): Option[Vector3[Double]] = {
    def move() = {
      val x =
        if (window.leftPressed())
          -translateFactor
        else if (window.rightPressed())
          translateFactor
        else
          0

      val (y, z) =
        if (window.shiftUpPressed())
          (0d, -translateFactor)
        else if (window.shiftDownPressed())
          (0d, translateFactor)
        else if (window.upPressed())
          (translateFactor, 0d)
        else if (window.downPressed())
          (-translateFactor, 0d)
        else
          (0d, 0d)

      Vector3[Double](x, y, z)
    }

    optionalKey match {
      case Some(value) =>
        //if optional key is set, run move only if it's pressed
        if (window.keyPressed(value))
          Some(move())
        else
          None

      case None =>
        Some(move())
    }
  }
}

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

package slack3d.graphics

import com.typesafe.scalalogging.LazyLogging
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import slack3d.Core
import slack3d.algebra.util.Maths
import slack3d.algebra.{Isometry3, Matrix3, Vector3}
import slack3d.graphics.camera.{Camera, Perspective}
import slack3d.graphics.colour.Colour
import slack3d.graphics.data.Vec4
import slack3d.graphics.light.{Light, LightProjector}
import slack3d.graphics.shader.ShaderProgram
import slack3d.graphics.shape.line.{Line, LineOrRay}
import slack3d.graphics.shape.{Mesh, Meshable, Point, Triangle}
import slack3d.graphics.vertex.{Vertex, VertexArrayObject}
import slack3d.graphics.window.Window
import spire.ClassTag
import spire.math.Numeric

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.{Deadline, Duration, FiniteDuration}

case object Slack3D extends LazyLogging {

  implicit class Isometry3Slack3DImplicits[A: ClassTag](isometry: Isometry3[A]) {
    //performs perspective multiplication
    def perspectiveMultiply(vector: Vector3[A])(implicit num: Numeric[A]): Vec4[A] =
      Vec4(
        vector3 = isometry * vector,
        w = num.negate(vector.z)
      )
  }

  sealed trait State {
    def width: Int
    def height: Int
    def frameCount: Int
    def camera: Option[Camera]
    def window: Window
    def deltaTime: Double
    def perspective: Option[Perspective]

    def getTime(): Double
  }

  def apply(shapes: Meshable*): Unit =
    Slack3D(this.productPrefix) forOnce {
      _ =>
        shapes
    }

  def apply(shapes: Iterable[Meshable]): Unit =
    Slack3D(this.productPrefix, enable2DCoordinates = false) forOnce {
      _ =>
        shapes
    }

  def apply(title: String,
            width: Int = 1600,
            height: Int = 1000,
            backgroundColor: Colour = Colour(0.1d, 0.1d, 0.1d, 1.0d),
            enableWireframes: Boolean = false,
            enable2DCoordinates: Boolean = true,
            camera: Option[Camera] = Some(Camera()),
            perspective: Option[Perspective] = Some(Perspective()),
            light: Option[Light] = Some(Light()(LightProjector.PhongLightProjector))): Slack3D = {

    val window =
      Window(
        title = title,
        width = width,
        height = height
      )

    //OpenGL Global state
    GL11.glEnable(GL11.GL_LINE_SMOOTH)
    //    GL11.glEnable(GL11.GL_POINT_SMOOTH)
    //    GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST)
    GL11.glEnable(GL11.GL_POINT_SIZE)
    GL11.glEnable(GL11.GL_DEPTH_TEST)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

    val vao = VertexArrayObject.bindVAO()

    val shaderProgram: ShaderProgram = Core.initialiseDefaultShaders().get

    new Slack3D(
      window = window,
      shaderProgram = shaderProgram,
      backgroundColor = backgroundColor,
      enable2DCoordinates = enable2DCoordinates,
      enableWireframes = enableWireframes,
      vao = vao,
      camera = camera.map(_.attachCursorCallback(window)),
      perspective = perspective,
      light = light
    )
  }

  private def applyPerspectiveCamera(points: ListBuffer[Vertex.Point[Vector3[Double]]],
                                     lines: ListBuffer[Vertex.Line[Vector3[Double]]],
                                     triangles: ListBuffer[Vertex.Triangle[Vector3[Double]]])(implicit state: State): Mesh[Vertex.Point[Vec4[Double]], Vertex.Line[Vec4[Double]], Vertex.Triangle[Vec4[Double]]] = {
    val view =
      state.camera match {
        case Some(camera) =>
          Matrix3.lookAt(
            position = camera.position,
            center = camera.position + camera.front,
            up = camera.up
          )

        case None =>
          Isometry3.identity[Double]()
      }

    val perspective =
      state.perspective match {
        case Some(perspective) =>
          Matrix3.perspective(
            fov = Math.toRadians(perspective.fov),
            width = state.window.width,
            height = state.window.height,
            zNear = perspective.zNear,
            zFar = perspective.zFar
          )

        case None =>
          Isometry3.identity[Double]()
      }

    val _points =
      points map {
        vertex =>
          val perspectiveVector = perspective perspectiveMultiply view * vertex.vertex
          Vertex.Point(vertex.size, perspectiveVector, colour = vertex.colour)
      }

    val _triangles =
      triangles map {
        vertex =>
          val perspectiveVector = perspective perspectiveMultiply view * vertex.vertex
          Vertex.Triangle(perspectiveVector, colour = vertex.colour)
      }

    val _lines =
      lines map {
        vertex =>
          val perspectiveVector = perspective perspectiveMultiply view * vertex.vertex
          Vertex.Line(perspectiveVector, colour = vertex.colour)
      }

    Mesh(
      points = _points,
      lines = _lines,
      triangles = _triangles
    )
  }

  private def sendMesh(mesh: Mesh[Vertex.Point[Vec4[Double]], Vertex.Line[Vec4[Double]], Vertex.Triangle[Vec4[Double]]]): Unit = {
    @inline def bufferLines(vertices: ListBuffer[Vertex.Line[Vec4[Double]]]): Unit = {
      val vertexBuffer = VertexArrayObject.bufferData(vertices = vertices, verticesCount = vertices.size)
      GL11.glDrawArrays(GL11.GL_LINES, 0, vertexBuffer.length / VertexArrayObject.VERTEX_BUFFER_SIZE)
    }

    @inline def bufferPoints(vertices: Map[Float, ListBuffer[Vertex.Point[Vec4[Double]]]]): Unit =
      vertices foreach {
        case (pointSize, vertices) =>
          GL11.glPointSize(pointSize)
          val vertexBuffer = VertexArrayObject.bufferData(vertices = vertices, vertices.length)
          GL11.glDrawArrays(GL11.GL_POINTS, 0, vertexBuffer.length / VertexArrayObject.VERTEX_BUFFER_SIZE)
      }

    @inline def bufferTriangles(vertices: ListBuffer[Vertex.Triangle[Vec4[Double]]]): Unit = {
      val verticesArray = VertexArrayObject.bufferData(vertices = vertices, vertices.length)
      GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verticesArray.length / VertexArrayObject.VERTEX_BUFFER_SIZE)
    }

    bufferLines(mesh.lines)
    bufferPoints(mesh.points.groupBy(_.size))
    bufferTriangles(mesh.triangles)
  }

  private def sendVertices(points: ListBuffer[Vertex.Point[Vector3[Double]]],
                           lines: ListBuffer[Vertex.Line[Vector3[Double]]],
                           triangles: ListBuffer[Vertex.Triangle[Vector3[Double]]])(implicit state: State): Unit =
    sendMesh(applyPerspectiveCamera(points, lines, triangles))

  private def build2DCoordinates(shapes: Mesh[Point, LineOrRay, Triangle]): Unit = {
    shapes.lines addOne Line(fromX = -1.0d, fromY = 0.0d, fromZ = 0, toX = 1.0d, toY = 0.0d, toZ = 0, colour = Colour.defaultCoordinateColor, showCone = false, showVectorInfo = false)
    shapes.lines addOne Line(fromX = 0.0d, fromY = -1.0d, fromZ = 0, toX = 0.0d, toY = 1.0d, toZ = 0, colour = Colour.defaultCoordinateColor, showCone = false, showVectorInfo = false)
    //    shapes addOne Line(fromX = 0.0d, fromY = 0.0d, fromZ = 1d, toX = 0.0d, toY = 0.0d, toZ = -1d, colour = Colour.defaultCoordinateColor, showCone = false)
    build2DCoordinatesPoints(shapes)
  }

  private def build2DCoordinatesPoints(shapes: Mesh[Point, LineOrRay, Triangle]): Unit =
    (-10 to 10)
      .map(_ * 0.1d)
      .foreach {
        point =>
          val pointRounded =
            if (point == 0.0)
              None
            else
              Some(Maths.round(point, 1).toString())

          shapes.points addOne Point(x = point, y = 0, z = 0, size = 5, colour = Colour.defaultCoordinateColor, showVectorInfo = false)
          shapes.points addOne Point(x = 0, y = point, z = 0, size = 5, colour = Colour.defaultCoordinateColor, showVectorInfo = false, label = pointRounded)
      }
}

class Slack3D(val window: Window,
              shaderProgram: ShaderProgram,
              backgroundColor: Colour,
              enable2DCoordinates: Boolean,
              enableWireframes: Boolean,
              vao: VertexArrayObject,
              val camera: Option[Camera],
              val perspective: Option[Perspective],
              val light: Option[Light],
              var deltaTime: Double = 0.0d, // Time between current frame and last frame
              var lastFrame: Double = 0.0d,
              var frameCount: Int = 0) extends AutoCloseable with Slack3D.State {

  private implicit val state: Slack3D = this

  if (enableWireframes)
    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
  else
    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL)

  def getTime(): Double =
    GLFW.glfwGetTime()

  override def width: Int =
    window.width

  override def height: Int =
    window.height

  override def close(): Unit =
    window.close()

  def forOnce(renderer: Slack3D.State => Iterable[Meshable]): Unit =
    foreach(1)(renderer)

  def foreach(interval: FiniteDuration)(renderer: Slack3D.State => Iterable[Meshable]): Unit =
    foreach(Int.MaxValue, interval)(renderer)

  def foreach(maxFrames: Int)(renderer: Slack3D.State => Iterable[Meshable]): Unit =
    foreach(maxFrames, Duration.Zero)(renderer)

  def foreach(maxFrames: Int, interval: FiniteDuration)(renderer: Slack3D.State => Iterable[Meshable]): Unit =
    fold(maxFrames, (), interval) {
      case (_, state) =>
        ((), renderer(state))
    }

  def foreach(renderer: Slack3D.State => Iterable[Meshable]): Unit =
    foldLeft(()) {
      case (_, state) =>
        ((), renderer(state))
    }

  def foldLeft[A](input: A)(renderer: (A, Slack3D.State) => (A, Iterable[Meshable])): A =
    fold[A](Int.MaxValue, input, Duration.Zero)(renderer)

  def foldLeft[A](input: A, interval: FiniteDuration)(renderer: (A, Slack3D.State) => (A, Iterable[Meshable])): A =
    fold[A](Int.MaxValue, input, interval)(renderer)

  def fold[A](maxFrames: Int, input: A, interval: FiniteDuration)(renderer: (A, Slack3D.State) => (A, Iterable[Meshable])): A = {
    var frame = 0
    frameCount = frame
    var result: A = input

    var previousShapes: Iterable[Meshable] = null

    var deadline = Deadline.now

    while (!GLFW.glfwWindowShouldClose(window.id)) {
      val currentFrame = this.getTime()
      deltaTime = currentFrame - lastFrame
      lastFrame = currentFrame

      GL11.glClearColor(backgroundColor.x.toFloat, backgroundColor.y.toFloat, backgroundColor.z.toFloat, backgroundColor.w.toFloat)
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

      window.processKeyboardInput()
      camera.foreach(_.processKeyboardInput(deltaTime, window))

      shaderProgram.use()

      val (updateInput, newShapes) =
        if (!window.paused && frame < maxFrames && deadline.isOverdue()) {
          deadline = interval.fromNow
          renderer(result, this)
        } else {
          //use previous frames shapes if maxFrames are complete
          (result, previousShapes)
        }

      result = updateInput
      previousShapes = newShapes

      val mesh = Mesh[Point, LineOrRay, Triangle]()
      newShapes foreach (_.buildMesh(mesh))
      if (enable2DCoordinates) Slack3D.build2DCoordinates(mesh)

      val triangleVertices =
        light match {
          case Some(light) =>
            mesh.triangles.foldLeft(ListBuffer.empty[Vertex.Triangle[Vector3[Double]]]) {
              case (buffer, triangle) =>
                buffer ++= light(camera, triangle)
            }

          case None =>
            mesh.triangles.foldLeft(ListBuffer.empty[Vertex.Triangle[Vector3[Double]]]) {
              case (vertices, triangle) =>
                vertices addOne Vertex.Triangle(triangle.a, triangle.colour)
                vertices addOne Vertex.Triangle(triangle.b, triangle.colour)
                vertices addOne Vertex.Triangle(triangle.c, triangle.colour)
            }
        }

      val pointVertices =
        mesh.points map {
          point =>
            Vertex.Point(point.size, point.vector, point.colour)
        }

      val lineVertices =
        mesh.lines.foldLeft(ListBuffer.empty[Vertex.Line[Vector3[Double]]]) {
          case (vertices, line) =>
            vertices addOne Vertex.Line(line.from, line.colour)
            vertices addOne Vertex.Line(line.to, line.colour)
        }

      Slack3D.sendVertices(
        points = pointVertices,
        lines = lineVertices,
        triangles = triangleVertices
      )

      GLFW.glfwSwapBuffers(window.id)

      frame += 1
      this.frameCount = frame
      GLFW.glfwPollEvents()
    }

    result
  }
}

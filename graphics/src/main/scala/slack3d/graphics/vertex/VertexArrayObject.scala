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

package slack3d.graphics.vertex

import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.system.MemoryUtil
import slack3d.graphics.data.Vec4

object VertexArrayObject {

  val VERTEX_BUFFER_SIZE = 8

  def bindVAO(): VertexArrayObject = {
    val vertexArrayObject = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vertexArrayObject)

    val vertexBufferObject = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObject)

    GL20.glVertexAttribPointer(0, 4, GL11.GL_DOUBLE, false, VERTEX_BUFFER_SIZE * java.lang.Double.BYTES, MemoryUtil.NULL)
    GL20.glEnableVertexAttribArray(0)

    //Color
    GL20.glVertexAttribPointer(1, 4, GL11.GL_DOUBLE, false, VERTEX_BUFFER_SIZE * java.lang.Double.BYTES, 4 * java.lang.Double.BYTES)
    GL20.glEnableVertexAttribArray(1)

    new VertexArrayObject(
      vertexArrayObjectId = vertexArrayObject,
      vertexBufferObjectId = vertexBufferObject
    )
  }

  //adds the vertex data to the array starting from the index and returns the next index
  private def addToArray(index: Int, vertex: Vertex[Vec4[Double]], array: Array[Double]): Int = {
    array(index) = vertex.vertex.x
    array(index + 1) = vertex.vertex.y
    array(index + 2) = vertex.vertex.z
    array(index + 3) = vertex.vertex.w
    array(index + 4) = vertex.colour.x
    array(index + 5) = vertex.colour.y
    array(index + 6) = vertex.colour.z
    array(index + 7) = vertex.colour.w
    index + VERTEX_BUFFER_SIZE
  }

  def bufferData(vertices: Iterable[Vertex[Vec4[Double]]], verticesCount: Int): Array[Double] = {
    val vertexArray = new Array[Double](verticesCount * VERTEX_BUFFER_SIZE)

    vertices.foldLeft(0) {
      case (index, vertex) =>
        addToArray(
          index = index,
          vertex = vertex,
          array = vertexArray
        )
    }

    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexArray, GL15.GL_STATIC_DRAW)
    vertexArray
  }

}

class VertexArrayObject(val vertexArrayObjectId: Int,
                        val vertexBufferObjectId: Int)

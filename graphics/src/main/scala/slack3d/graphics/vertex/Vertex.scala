package slack3d.graphics.vertex

import slack3d.graphics.colour.Colour

/**
 * Type of data buffered to GPU
 */
sealed trait Vertex[V] {
  def vertex: V
  def colour: Colour
}

object Vertex {

  case class Line[V](vertex: V,
                     colour: Colour) extends Vertex[V]

  case class Point[V](size: Float,
                      vertex: V,
                      colour: Colour) extends Vertex[V]

  case class Triangle[V](vertex: V,
                         colour: Colour) extends Vertex[V]

}

//package slack3d.graphics.shape
//
//import slack3d.graphics.vertex.Vertex.Triangle
//
//import scala.collection.mutable.ListBuffer
//
//trait Meshable {
//
//  def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit
//
//}
//
//case class Mesh[P, L, T](points: ListBuffer[P] = ListBuffer.empty[P],
//                         lines: ListBuffer[L] = ListBuffer.empty[L],
//                         triangles: ListBuffer[T] = ListBuffer.empty[T])

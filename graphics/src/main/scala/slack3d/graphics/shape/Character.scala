package slack3d.graphics.shape

import slack3d.algebra.Vector3
import slack3d.graphics.colour.Colour
import slack3d.graphics.shape.line.{Line, LineOrRay}

/**
 * NOTE: MAKE SURE ALL LINES HAVE 'showCoordinate' DISABLED OTHERWISE IT RESULTS IN NESTED LOOP CAUSING STACKOVERFLOW.
 */
object Character {

  def apply(characters: String,
            colour: Colour,
            position: Vector3[Double] = Vector3.origin(),
            scale: Double = 0.01d,
            xPadding: Double = 0.015d,
            yPadding: Double = 0.02d): Array[Character] =
    characters.zipWithIndex.toArray map {
      case (character, index) =>
        //creates the character at the origin and then scales to reduce to or increase size and then padds it
        val translator = Vector3(index * xPadding, yPadding, 0) + position

        character match {
          case '1' =>
            (one(colour) * scale) + translator

          case '2' =>
            (two(colour) * scale) + translator

          case '3' =>
            (three(colour) * scale) + translator

          case '4' =>
            (four(colour) * scale) + translator

          case '5' =>
            (five(colour) * scale) + translator

          case '6' =>
            (six(colour) * scale) + translator

          case '7' =>
            (seven(colour) * scale) + translator

          case '8' =>
            (eight(colour) * scale) + translator

          case '9' =>
            (nine(colour) * scale) + translator

          case '0' =>
            (zero(colour) * scale) + translator

          case ',' =>
            (comma(colour) * scale) + translator

          case '-' =>
            (negative(colour) * scale) + translator

          case '=' =>
            (equalTo(colour) * scale) + translator

          case '[' | '(' =>
            (leftBracket(colour) * scale) + translator

          case ']' | ')' =>
            (rightBracket(colour) * scale) + translator

          case '.' =>
            //do get a special treatment. It does not need scaling just padding.
            dot(translator, colour)

          case ':' =>
            //do get a special treatment. It does not need scaling just padding.
            semiColon(translator, colour)

          case 'A' | 'a' =>
            (A(colour) * scale) + translator

          case 'B' | 'b' =>
            (B(colour) * scale) + translator

          case 'C' | 'c' =>
            (C(colour) * scale) + translator

          case 'D' | 'd' =>
            (D(colour) * scale) + translator

          case 'E' | 'e' =>
            (E(colour) * scale) + translator

          case 'F' | 'f' =>
            (F(colour) * scale) + translator

          case 'G' | 'g' =>
            (G(colour) * scale) + translator

          case 'H' | 'h' =>
            (H(colour) * scale) + translator

          case 'I' | 'i' =>
            (I(colour) * scale) + translator

          case 'J' | 'j' =>
            (J(colour) * scale) + translator

          case 'K' | 'k' =>
            (K(colour) * scale) + translator

          case 'L' | 'l' =>
            (L(colour) * scale) + translator

          case 'M' | 'm' =>
            (M(colour) * scale) + translator

          case 'N' | 'n' =>
            (N(colour) * scale) + translator

          case 'O' | 'o' =>
            (O(colour) * scale) + translator

          case 'P' | 'p' =>
            (P(colour) * scale) + translator

          case 'Q' | 'q' =>
            (Q(colour) * scale) + translator

          case 'R' | 'r' =>
            (R(colour) * scale) + translator

          case 'S' | 's' =>
            (S(colour) * scale) + translator

          case 'T' | 't' =>
            (T(colour) * scale) + translator

          case 'U' | 'u' =>
            (U(colour) * scale) + translator

          case 'V' | 'v' =>
            (V(colour) * scale) + translator

          case 'W' | 'w' =>
            (W(colour) * scale) + translator

          case 'X' | 'x' =>
            (X(colour) * scale) + translator

          case 'Y' | 'y' =>
            (Y(colour) * scale) + translator

          case 'Z' | 'z' =>
            (Z(colour) * scale) + translator

          case '|' =>
            (line(colour) * scale) + translator

          case ' ' =>
            Character(lines = Array.empty, points = Array.empty)

        }
    }

  def one(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0, fromY = 0.5d, toX = 0, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0, fromY = 0.5d, toX = -0.5d, toY = 0.25d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = -0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      ),
      points = Array.empty
    )

  def two(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0, toX = 0.5d, toY = 0, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = -0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def three(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def four(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0, toX = 0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      ),
      points = Array.empty
    )

  def five(colour: Colour): Character =
    Character(
      lines =
        Array(
          Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
          Line(fromX = -0.5d, fromY = 0d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
          Line(fromX = -0.5d, fromY = 0d, toX = 0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
          Line(fromX = 0.5d, fromY = 0d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
          Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        ),
      points = Array.empty
    )

  def six(colour: Colour): Character =
    Character(
      five(colour).lines :+ Line(fromX = -0.5d, fromY = 0d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      points = Array.empty
    )

  def seven(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      ),
      points = Array.empty
    )

  def eight(colour: Colour): Character =
    Character(
      three(colour).lines :+ Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      points = Array.empty
    )

  def nine(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def zero(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = -0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
        //        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showCoordinate = false), //slash
      ),
      points = Array.empty
    )

  def comma(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.0d, fromY = -0.50d, toX = -0.25d, toY = -1d, colour = colour, showCone = false, showVectorInfo = false),
      ),
      points = Array.empty
    )

  def dot(position: Vector3[Double], colour: Colour): Character =
    Character(
      lines = Array.empty,
      points = Array(Point(vector = position, colour = colour, showVectorInfo = false, size = 2))
    )

  def semiColon(position: Vector3[Double], colour: Colour): Character =
    Character(
      lines = Array.empty,
      points =
        Array(
          Point(vector = position - Vector3(0d, 0.005d, 0), colour = colour, showVectorInfo = false, size = 2),
          Point(vector = position + Vector3(0d, 0.006d, 0), colour = colour, showVectorInfo = false, size = 2)
        )
    )

  def leftBracket(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.4d, fromY = 0.8d, toX = 0.25d, toY = 0.8d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.4d, fromY = 0.8d, toX = -0.4d, toY = -0.8d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.4d, fromY = -0.8d, toX = 0.25d, toY = -0.8d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def rightBracket(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.4d, fromY = 0.8d, toX = 0.25d, toY = 0.8d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.25d, fromY = 0.8d, toX = 0.25d, toY = -0.8d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.4d, fromY = -0.8d, toX = 0.25d, toY = -0.8d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def equalTo(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.2d, toX = -0.5d, toY = 0.2d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = -0.2d, toX = -0.5d, toY = -0.2d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def negative(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def A(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.3d, fromY = -0.1d, toX = 0.3d, toY = -0.1d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def B(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.4d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = 0.3d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = 0.5d, fromY = 0.3d, toX = 0.5d, toY = 0.1d, colour = colour, showCone = false, showVectorInfo = false), //right up
        Line(fromX = 0.5d, fromY = -0.1d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //right bottom
        Line(fromX = 0.5d, fromY = 0.1d, toX = 0.4d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //b small cut top
        Line(fromX = 0.4d, fromY = 0d, toX = 0.5d, toY = -0.1d, colour = colour, showCone = false, showVectorInfo = false), //b small cut bottom
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //slit up
        Line(fromX = 0.3d, fromY = -0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false) //slit down
      ),
      points = Array.empty
    )

  def C(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.3d, fromY = 0.5d, toX = 0.3d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to right
        Line(fromX = -0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to left
        Line(fromX = -0.5d, fromY = 0.3d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = -0.3d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left to bottom
        Line(fromX = 0.3d, fromY = -0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //bottom to right
        Line(fromX = -0.3d, fromY = -0.5d, toX = 0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
      ),
      points = Array.empty
    )

  def D(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.3d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = 0.5d, fromY = 0.3d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //smaller cut top
        Line(fromX = 0.3d, fromY = -0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //smaller cut bottom
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def E(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def F(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false),
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false)
      ),
      points = Array.empty
    )

  def G(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.3d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to right
        Line(fromX = -0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to left
        Line(fromX = 0.5d, fromY = 0d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = 0d, fromY = 0d, toX = 0d, toY = -0.2d, colour = colour, showCone = false, showVectorInfo = false), //mid cut
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = -0.3d, fromY = -0.5d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //bottom to left
        Line(fromX = 0.5d, fromY = 0d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.3d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def H(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def I(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0d, fromY = 0.5d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
      ),
      points = Array.empty
    )

  def J(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.35d, fromY = 0.5d, toX = -0.4d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.1d, fromY = -0.5d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = 0.1d, fromY = 0.5d, toX = 0.1d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = -0.5d, fromY = 0d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def K(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = -0.5d, fromY = 0d, toX = 0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //upper
        Line(fromX = -0.5d, fromY = 0d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //lower
      ),
      points = Array.empty
    )

  def L(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def M(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid left
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid right
      ),
      points = Array.empty
    )

  def N(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
      ),
      points = Array.empty
    )

  def O(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.3d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = -0.5d, fromY = 0.3d, toX = -0.3d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //left to top
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to right
        Line(fromX = 0.3d, fromY = -0.5d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = -0.5d, fromY = -0.3d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left to bottom
        Line(fromX = 0.5d, fromY = 0.3d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.3d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = 0.3d, fromY = -0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false) //bottom to right
      ),
      points = Array.empty
    )

  def P(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.3d, colour = colour, showCone = false, showVectorInfo = false), //top to right
        Line(fromX = 0.3d, fromY = 0d, toX = -0.5d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = 0.3d, fromY = 0d, toX = 0.5d, toY = 0.2d, colour = colour, showCone = false, showVectorInfo = false), //mid to right
        Line(fromX = 0.5d, fromY = 0.3d, toX = 0.5d, toY = 0.2d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def Q(colour: Colour): Character =
    Character(
      O(colour).lines :+ Line(fromX = 0.2d, fromY = -0.2d, toX = 0.7d, toY = -0.7d, colour = colour, showCone = false, showVectorInfo = false), //comma
      points = Array.empty
    )

  def R(colour: Colour): Character =
    Character(
      P(colour).lines :+ Line(fromX = 0d, fromY = 0d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom cut
      points = Array.empty
    )

  def S(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = 0.5d, toX = -0.3d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.3d, fromY = 0.5d, toX = 0.5d, toY = 0.4d, colour = colour, showCone = false, showVectorInfo = false), //top to right
        Line(fromX = -0.3d, fromY = 0.5d, toX = -0.5d, toY = 0.4d, colour = colour, showCone = false, showVectorInfo = false), //top to left
        Line(fromX = 0.3d, fromY = 0d, toX = -0.3d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //mid
        Line(fromX = -0.5d, fromY = 0.1d, toX = -0.3d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //left to mid
        Line(fromX = 0.3d, fromY = -0.5d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = -0.3d, fromY = -0.5d, toX = -0.5d, toY = -0.4d, colour = colour, showCone = false, showVectorInfo = false), //bottom kick
        Line(fromX = 0.5d, fromY = -0.1d, toX = 0.5d, toY = -0.4d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = 0.3d, fromY = 0d, toX = 0.5d, toY = -0.1d, colour = colour, showCone = false, showVectorInfo = false), //mid to right
        Line(fromX = -0.5d, fromY = 0.1d, toX = -0.5d, toY = 0.4d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = 0.5d, fromY = -0.4d, toX = 0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right to bottom
      ),
      points = Array.empty
    )

  def T(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0d, fromY = 0.5d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //mid
      ),
      points = Array.empty
    )

  def U(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.3d, fromY = -0.5d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = -0.5d, fromY = -0.3d, toX = -0.3d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left to bottom
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = 0.3d, fromY = -0.5d, toX = 0.5d, toY = -0.3d, colour = colour, showCone = false, showVectorInfo = false) //bottom to right
      ),
      points = Array.empty
    )

  def V(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false) //left
      ),
      points = Array.empty
    )

  def W(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = -0.25d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left
        Line(fromX = -0.25d, fromY = -0.5d, toX = 0, toY = 0, colour = colour, showCone = false, showVectorInfo = false), //left to mid
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0.25d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right
        Line(fromX = 0.25d, fromY = -0.5d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //right to mid
      ),
      points = Array.empty
    )

  def X(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //left to right
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right to left
      ),
      points = Array.empty
    )

  def Y(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = -0.5d, fromY = 0.5d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.5d, fromY = 0.5d, toX = 0d, toY = 0d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0d, fromY = 0d, toX = 0d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
      ),
      points = Array.empty
    )

  def Z(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = 0.5d, colour = colour, showCone = false, showVectorInfo = false), //top
        Line(fromX = 0.5d, fromY = -0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //bottom
        Line(fromX = 0.5d, fromY = 0.5d, toX = -0.5d, toY = -0.5d, colour = colour, showCone = false, showVectorInfo = false), //right to left
      ),
      points = Array.empty
    )

  def line(colour: Colour): Character =
    Character(
      Array(
        Line(fromX = 0d, fromY = 1d, toX = 0d, toY = -1d, colour = colour, showCone = false, showVectorInfo = false), //mid
      ),
      points = Array.empty
    )
}

case class Character(lines: Array[Line],
                     points: Array[Point]) extends Shape {

  override type Self = Character

  override def map(f: Vector3[Double] => Vector3[Double]): Character =
    Character(
      lines = lines.map(_.map(f)),
      points = points.map(_.map(f))
    )

  override def buildMesh(mesh: Mesh[Point, LineOrRay, Triangle]): Unit = {
    mesh.points ++= this.points
    mesh.lines ++= this.lines
  }
}

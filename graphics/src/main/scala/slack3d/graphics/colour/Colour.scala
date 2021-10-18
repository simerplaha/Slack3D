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

package slack3d.graphics.colour

import java.util.concurrent.ConcurrentLinkedQueue
import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

object Colour {

  val IndianRed = Colour(0.804d, 0.361d, 0.361d, 1.0d)
  val LightCoral = Colour(0.941d, 0.502d, 0.502d, 1.0d)
  val Salmon = Colour(0.980d, 0.502d, 0.447d, 1.0d)
  val DarkSalmon = Colour(0.914d, 0.588d, 0.478d, 1.0d)
  val LightSalmon = Colour(1.000d, 0.627d, 0.478d, 1.0d)
  val Crimson = Colour(0.863d, 0.078d, 0.235d, 1.0d)
  val Red = Colour(1.000d, 0.000d, 0.000d, 1.0d)
  val FireBrick = Colour(0.698d, 0.133d, 0.133d, 1.0d)
  val DarkRed = Colour(0.545d, 0.000d, 0.000d, 1.0d)
  val Pink = Colour(1.000d, 0.753d, 0.796d, 1.0d)
  val LightPink = Colour(1.000d, 0.714d, 0.757d, 1.0d)
  val HotPink = Colour(1.000d, 0.412d, 0.706d, 1.0d)
  val DeepPink = Colour(1.000d, 0.078d, 0.576d, 1.0d)
  val MediumVioletRed = Colour(0.780d, 0.082d, 0.522d, 1.0d)
  val PaleVioletRed = Colour(0.859d, 0.439d, 0.576d, 1.0d)
  val Coral = Colour(1.000d, 0.498d, 0.314d, 1.0d)
  val Tomato = Colour(1.000d, 0.388d, 0.278d, 1.0d)
  val OrangeRed = Colour(1.000d, 0.271d, 0.000d, 1.0d)
  val DarkOrange = Colour(1.000d, 0.549d, 0.000d, 1.0d)
  val Orange = Colour(1.000d, 0.647d, 0.000d, 1.0d)
  val Gold = Colour(1.000d, 0.843d, 0.000d, 1.0d)
  val Yellow = Colour(1.000d, 1.000d, 0.000d, 1.0d)
  val LightYellow = Colour(1.000d, 1.000d, 0.878d, 1.0d)
  val LemonChion = Colour(1.000d, 0.980d, 0.804d, 1.0d)
  val LightGoldenrodYellow = Colour(0.980d, 0.980d, 0.824d, 1.0d)
  val PapayaWhip = Colour(1.000d, 0.937d, 0.835d, 1.0d)
  val Moccasin = Colour(1.000d, 0.894d, 0.710d, 1.0d)
  val PeachPu = Colour(1.000d, 0.855d, 0.725d, 1.0d)
  val PaleGoldenrod = Colour(0.933d, 0.910d, 0.667d, 1.0d)
  val Khaki = Colour(0.941d, 0.902d, 0.549d, 1.0d)
  val DarkKhaki = Colour(0.741d, 0.718d, 0.420d, 1.0d)
  val Lavender = Colour(0.902d, 0.902d, 0.980d, 1.0d)
  val Thistle = Colour(0.847d, 0.749d, 0.847d, 1.0d)
  val Plum = Colour(0.867d, 0.627d, 0.867d, 1.0d)
  val Violet = Colour(0.933d, 0.510d, 0.933d, 1.0d)
  val Orchid = Colour(0.855d, 0.439d, 0.839d, 1.0d)
  val Fuchsia = Colour(1.000d, 0.000d, 1.000d, 1.0d)
  val Magenta = Colour(1.000d, 0.000d, 1.000d, 1.0d)
  val MediumOrchid = Colour(0.729d, 0.333d, 0.827d, 1.0d)
  val MediumPurple = Colour(0.576d, 0.439d, 0.859d, 1.0d)
  val BlueViolet = Colour(0.541d, 0.169d, 0.886d, 1.0d)
  val DarkViolet = Colour(0.580d, 0.000d, 0.827d, 1.0d)
  val DarkOrchid = Colour(0.600d, 0.196d, 0.800d, 1.0d)
  val DarkMagenta = Colour(0.545d, 0.000d, 0.545d, 1.0d)
  val Purple = Colour(0.502d, 0.000d, 0.502d, 1.0d)
  val Indigo = Colour(0.294d, 0.000d, 0.510d, 1.0d)
  val SlateBlue = Colour(0.416d, 0.353d, 0.804d, 1.0d)
  val DarkSlateBlue = Colour(0.282d, 0.239d, 0.545d, 1.0d)
  val GreenYellow = Colour(0.678d, 1.000d, 0.184d, 1.0d)
  val Chartreuse = Colour(0.498d, 1.000d, 0.000d, 1.0d)
  val LawnGreen = Colour(0.486d, 0.988d, 0.000d, 1.0d)
  val Lime = Colour(0.000d, 1.000d, 0.000d, 1.0d)
  val LimeGreen = Colour(0.196d, 0.804d, 0.196d, 1.0d)
  val PaleGreen = Colour(0.596d, 0.984d, 0.596d, 1.0d)
  val LightGreen = Colour(0.565d, 0.933d, 0.565d, 1.0d)
  val MediumSpringGreen = Colour(0.000d, 0.980d, 0.604d, 1.0d)
  val SpringGreen = Colour(0.000d, 1.000d, 0.498d, 1.0d)
  val MediumSeaGreen = Colour(0.235d, 0.702d, 0.443d, 1.0d)
  val SeaGreen = Colour(0.180d, 0.545d, 0.341d, 1.0d)
  val ForestGreen = Colour(0.133d, 0.545d, 0.133d, 1.0d)
  val Green = Colour(0.000d, 0.502d, 0.000d, 1.0d)
  val DarkGreen = Colour(0.000d, 0.392d, 0.000d, 1.0d)
  val YellowGreen = Colour(0.604d, 0.804d, 0.196d, 1.0d)
  val OliveDrab = Colour(0.420d, 0.557d, 0.137d, 1.0d)
  val Olive = Colour(0.502d, 0.502d, 0.000d, 1.0d)
  val DarkOliveGreen = Colour(0.333d, 0.420d, 0.184d, 1.0d)
  val MediumAquamarine = Colour(0.400d, 0.804d, 0.667d, 1.0d)
  val DarkSeaGreen = Colour(0.561d, 0.737d, 0.561d, 1.0d)
  val LightSeaGreen = Colour(0.125d, 0.698d, 0.667d, 1.0d)
  val DarkCyan = Colour(0.000d, 0.545d, 0.545d, 1.0d)
  val Teal = Colour(0.000d, 0.502d, 0.502d, 1.0d)
  val Aqua = Colour(0.000d, 1.000d, 1.000d, 1.0d)
  val Cyan = Colour(0.000d, 1.000d, 1.000d, 1.0d)
  val LightCyan = Colour(0.878d, 1.000d, 1.000d, 1.0d)
  val PaleTurquoise = Colour(0.686d, 0.933d, 0.933d, 1.0d)
  val Aquamarine = Colour(0.498d, 1.000d, 0.831d, 1.0d)
  val Turquoise = Colour(0.251d, 0.878d, 0.816d, 1.0d)
  val MediumTurquoise = Colour(0.282d, 0.820d, 0.800d, 1.0d)
  val DarkTurquoise = Colour(0.000d, 0.808d, 0.820d, 1.0d)
  val CadetBlue = Colour(0.373d, 0.620d, 0.627d, 1.0d)
  val SteelBlue = Colour(0.275d, 0.510d, 0.706d, 1.0d)
  val LightSteelBlue = Colour(0.690d, 0.769d, 0.871d, 1.0d)
  val PowderBlue = Colour(0.690d, 0.878d, 0.902d, 1.0d)
  val LightBlue = Colour(0.678d, 0.847d, 0.902d, 1.0d)
  val SkyBlue = Colour(0.529d, 0.808d, 0.922d, 1.0d)
  val LightSkyBlue = Colour(0.529d, 0.808d, 0.980d, 1.0d)
  val DeepSkyBlue = Colour(0.000d, 0.749d, 1.000d, 1.0d)
  val DodgerBlue = Colour(0.118d, 0.565d, 1.000d, 1.0d)
  val CornlowerBlue = Colour(0.392d, 0.584d, 0.929d, 1.0d)
  val MediumSlateBlue = Colour(0.482d, 0.408d, 0.933d, 1.0d)
  val RoyalBlue = Colour(0.255d, 0.412d, 0.882d, 1.0d)
  val Blue = Colour(0.000d, 0.000d, 1.000d, 1.0d)
  val MediumBlue = Colour(0.000d, 0.000d, 0.804d, 1.0d)
  val DarkBlue = Colour(0.000d, 0.000d, 0.545d, 1.0d)
  val Navy = Colour(0.000d, 0.000d, 0.502d, 1.0d)
  val MidnightBlue = Colour(0.098d, 0.098d, 0.439d, 1.0d)
  val Cornsilk = Colour(1.000d, 0.973d, 0.863d, 1.0d)
  val BlanchedAlmond = Colour(1.000d, 0.922d, 0.804d, 1.0d)
  val Bisque = Colour(1.000d, 0.894d, 0.769d, 1.0d)
  val NavajoWhite = Colour(1.000d, 0.871d, 0.678d, 1.0d)
  val Wheat = Colour(0.961d, 0.871d, 0.702d, 1.0d)
  val BurlyWood = Colour(0.871d, 0.722d, 0.529d, 1.0d)
  val Tan = Colour(0.824d, 0.706d, 0.549d, 1.0d)
  val RosyBrown = Colour(0.737d, 0.561d, 0.561d, 1.0d)
  val SandyBrown = Colour(0.957d, 0.643d, 0.376d, 1.0d)
  val Goldenrod = Colour(0.855d, 0.647d, 0.125d, 1.0d)
  val DarkGoldenrod = Colour(0.722d, 0.525d, 0.043d, 1.0d)
  val Peru = Colour(0.804d, 0.522d, 0.247d, 1.0d)
  val Chocolate = Colour(0.824d, 0.412d, 0.118d, 1.0d)
  val SaddleBrown = Colour(0.545d, 0.271d, 0.075d, 1.0d)
  val Sienna = Colour(0.627d, 0.322d, 0.176d, 1.0d)
  val Brown = Colour(0.647d, 0.165d, 0.165d, 1.0d)
  val Maroon = Colour(0.502d, 0.000d, 0.000d, 1.0d)
  val White = Colour(1.000d, 1.000d, 1.000d, 1.0d)
  val Snow = Colour(1.000d, 0.980d, 0.980d, 1.0d)
  val Honeydew = Colour(0.941d, 1.000d, 0.941d, 1.0d)
  val MintCream = Colour(0.961d, 1.000d, 0.980d, 1.0d)
  val Azure = Colour(0.941d, 1.000d, 1.000d, 1.0d)
  val AliceBlue = Colour(0.941d, 0.973d, 1.000d, 1.0d)
  val GhostWhite = Colour(0.973d, 0.973d, 1.000d, 1.0d)
  val WhiteSmoke = Colour(0.961d, 0.961d, 0.961d, 1.0d)
  val Seashell = Colour(1.000d, 0.961d, 0.933d, 1.0d)
  val Beige = Colour(0.961d, 0.961d, 0.863d, 1.0d)
  val OldLace = Colour(0.992d, 0.961d, 0.902d, 1.0d)
  val FloralWhite = Colour(1.000d, 0.980d, 0.941d, 1.0d)
  val Ivory = Colour(1.000d, 1.000d, 0.941d, 1.0d)
  val AntiqueWhite = Colour(0.980d, 0.922d, 0.843d, 1.0d)
  val Linen = Colour(0.980d, 0.941d, 0.902d, 1.0d)
  val LavenderBlush = Colour(1.000d, 0.941d, 0.961d, 1.0d)
  val MistyRose = Colour(1.000d, 0.894d, 0.882d, 1.0d)
  val Gainsboro = Colour(0.863d, 0.863d, 0.863d, 1.0d)
  val LightGrey = Colour(0.827d, 0.827d, 0.827d, 1.0d)
  val Silver = Colour(0.753d, 0.753d, 0.753d, 1.0d)
  val DarkGray = Colour(0.663d, 0.663d, 0.663d, 1.0d)
  val Gray = Colour(0.502d, 0.502d, 0.502d, 1.0d)
  val DimGray = Colour(0.412d, 0.412d, 0.412d, 1.0d)
  val LightSlateGray = Colour(0.467d, 0.533d, 0.600d, 1.0d)
  val SlateGray = Colour(0.439d, 0.502d, 0.565d, 1.0d)
  val DarkSlateGray = Colour(0.184d, 0.310d, 0.310d, 1.0d)
  val Black = Colour(0.000d, 0.000d, 0.000d, 1.0d)

  val colours: Array[Colour] =
    Array(
      Colour.IndianRed,
      Colour.LightCoral,
      Colour.Salmon,
      Colour.DarkSalmon,
      Colour.LightSalmon,
      Colour.Crimson,
      Colour.Red,
      Colour.FireBrick,
      Colour.DarkRed,
      Colour.Pink,
      Colour.LightPink,
      Colour.HotPink,
      Colour.DeepPink,
      Colour.MediumVioletRed,
      Colour.PaleVioletRed,
      Colour.LightSalmon,
      Colour.Coral,
      Colour.Tomato,
      Colour.OrangeRed,
      Colour.DarkOrange,
      Colour.Orange,
      Colour.Gold,
      Colour.Yellow,
      Colour.LightYellow,
      Colour.LemonChion,
      Colour.LightGoldenrodYellow,
      Colour.PapayaWhip,
      Colour.Moccasin,
      Colour.PeachPu,
      Colour.PaleGoldenrod,
      Colour.Khaki,
      Colour.DarkKhaki,
      Colour.Lavender,
      Colour.Thistle,
      Colour.Plum,
      Colour.Violet,
      Colour.Orchid,
      Colour.Fuchsia,
      Colour.Magenta,
      Colour.MediumOrchid,
      Colour.MediumPurple,
      Colour.BlueViolet,
      Colour.DarkViolet,
      Colour.DarkOrchid,
      Colour.DarkMagenta,
      Colour.Purple,
      Colour.Indigo,
      Colour.SlateBlue,
      Colour.DarkSlateBlue,
      Colour.GreenYellow,
      Colour.Chartreuse,
      Colour.LawnGreen,
      Colour.Lime,
      Colour.LimeGreen,
      Colour.PaleGreen,
      Colour.LightGreen,
      Colour.MediumSpringGreen,
      Colour.SpringGreen,
      Colour.MediumSeaGreen,
      Colour.SeaGreen,
      Colour.ForestGreen,
      Colour.Green,
      Colour.DarkGreen,
      Colour.YellowGreen,
      Colour.OliveDrab,
      Colour.Olive,
      Colour.DarkOliveGreen,
      Colour.MediumAquamarine,
      Colour.DarkSeaGreen,
      Colour.LightSeaGreen,
      Colour.DarkCyan,
      Colour.Teal,
      Colour.Aqua,
      Colour.Cyan,
      Colour.LightCyan,
      Colour.PaleTurquoise,
      Colour.Aquamarine,
      Colour.Turquoise,
      Colour.MediumTurquoise,
      Colour.DarkTurquoise,
      Colour.CadetBlue,
      Colour.SteelBlue,
      Colour.LightSteelBlue,
      Colour.PowderBlue,
      Colour.LightBlue,
      Colour.SkyBlue,
      Colour.LightSkyBlue,
      Colour.DeepSkyBlue,
      Colour.DodgerBlue,
      Colour.CornlowerBlue,
      Colour.MediumSlateBlue,
      Colour.RoyalBlue,
      Colour.Blue,
      Colour.MediumBlue,
      Colour.DarkBlue,
      Colour.Navy,
      Colour.MidnightBlue,
      Colour.Cornsilk,
      Colour.BlanchedAlmond,
      Colour.Bisque,
      Colour.NavajoWhite,
      Colour.Wheat,
      Colour.BurlyWood,
      Colour.Tan,
      Colour.RosyBrown,
      Colour.SandyBrown,
      Colour.Goldenrod,
      Colour.DarkGoldenrod,
      Colour.Peru,
      Colour.Chocolate,
      Colour.SaddleBrown,
      Colour.Sienna,
      Colour.Brown,
      Colour.Maroon,
      Colour.White,
      Colour.Snow,
      Colour.Honeydew,
      Colour.MintCream,
      Colour.Azure,
      Colour.AliceBlue,
      Colour.GhostWhite,
      Colour.WhiteSmoke,
      Colour.Seashell,
      Colour.Beige,
      Colour.OldLace,
      Colour.FloralWhite,
      Colour.Ivory,
      Colour.AntiqueWhite,
      Colour.Linen,
      Colour.LavenderBlush,
      Colour.MistyRose,
      Colour.Gainsboro,
      Colour.LightGrey,
      Colour.Silver,
      Colour.DarkGray,
      Colour.Gray,
      Colour.DimGray,
      Colour.LightSlateGray,
      Colour.SlateGray,
      Colour.DarkSlateGray
      //      Colour.Black //background is black
    )

  val defaultCoordinateColor = Colour.DimGray.copy(w = 0.2d)

  val shuffledColours =
    List(Colour.DarkMagenta, Colour.Honeydew, Colour.LimeGreen, Colour.Orange, Colour.LightSkyBlue, Colour.LightGoldenrodYellow, Colour.FireBrick, Colour.MediumAquamarine, Colour.Yellow, Colour.SkyBlue, Colour.MediumBlue, Colour.WhiteSmoke, Colour.Indigo, Colour.LightCoral, Colour.MediumSeaGreen, Colour.GreenYellow, Colour.Olive, Colour.DarkRed, Colour.Peru, Colour.DarkGreen, Colour.Goldenrod, Colour.BlanchedAlmond, Colour.DodgerBlue, Colour.SlateBlue, Colour.DarkOliveGreen, Colour.DarkTurquoise, Colour.Snow, Colour.MidnightBlue, Colour.Turquoise, Colour.Linen, Colour.Coral, Colour.YellowGreen, Colour.LightSeaGreen, Colour.Azure, Colour.SaddleBrown, Colour.Bisque, Colour.Thistle, Colour.MediumSlateBlue, Colour.DarkSeaGreen, Colour.Green, Colour.Cornsilk, Colour.LightGrey, Colour.PaleGoldenrod, Colour.DarkOrchid, Colour.Navy, Colour.Plum, Colour.DeepPink, Colour.RosyBrown, Colour.Violet, Colour.Tomato, Colour.LawnGreen, Colour.FloralWhite, Colour.PapayaWhip, Colour.Purple, Colour.Beige, Colour.DarkOrange, Colour.SeaGreen, Colour.DarkSlateBlue, Colour.DarkKhaki, Colour.OldLace, Colour.LavenderBlush, Colour.MistyRose, Colour.AliceBlue, Colour.LightPink, Colour.DimGray, Colour.Lime, Colour.LightSteelBlue, Colour.Wheat, Colour.GhostWhite, Colour.White, Colour.DarkGray, Colour.Silver, Colour.Sienna, Colour.IndianRed, Colour.Chocolate, Colour.LightSalmon, Colour.CornlowerBlue, Colour.Blue, Colour.DarkViolet, Colour.Red, Colour.DarkCyan, Colour.Ivory, Colour.BurlyWood, Colour.DeepSkyBlue, Colour.Gold, Colour.AntiqueWhite, Colour.PowderBlue, Colour.HotPink, Colour.NavajoWhite, Colour.PaleGreen, Colour.Lavender, Colour.Pink, Colour.Maroon, Colour.PaleVioletRed, Colour.Brown, Colour.SlateGray, Colour.Aquamarine, Colour.LightGreen, Colour.OliveDrab, Colour.Gray, Colour.Moccasin, Colour.OrangeRed, Colour.Gainsboro, Colour.MediumSpringGreen, Colour.DarkGoldenrod, Colour.Salmon, Colour.MediumVioletRed, Colour.MediumOrchid, Colour.MintCream, Colour.Chartreuse, Colour.SteelBlue, Colour.LightCyan, Colour.Seashell, Colour.BlueViolet, Colour.DarkSalmon, Colour.DarkBlue, Colour.Magenta, Colour.SpringGreen, Colour.LightYellow, Colour.Cyan, Colour.LightSlateGray, Colour.LightSalmon, Colour.DarkSlateGray, Colour.Orchid, Colour.SandyBrown, Colour.RoyalBlue, Colour.Fuchsia, Colour.Teal, Colour.PeachPu, Colour.LemonChion, Colour.ForestGreen, Colour.MediumPurple, Colour.MediumTurquoise, Colour.PaleTurquoise, Colour.CadetBlue, Colour.Khaki, Colour.Tan, Colour.LightBlue, Colour.Aqua, Colour.Crimson)

  private var coloursQueue: ConcurrentLinkedQueue[Colour] = buildColourQueue(drop = 0)

  private def buildColourQueue(drop: Int): ConcurrentLinkedQueue[Colour] =
    new ConcurrentLinkedQueue(Colour.shuffledColours.drop(drop).asJava)

  def resetQueue(drop: Int = 0): Unit =
    coloursQueue = buildColourQueue(drop)

  @tailrec
  def next(w: Double = 1.0d,
           filter: Array[Colour] = Array.empty): Colour = {
    val colour = coloursQueue.poll()
    if (colour == null) {
      resetQueue()
      next()
    } else if (filter.contains(colour)) {
      next()
    } else {
      if (w != colour.w)
        colour.copy(w = w)
      else
        colour
    }
  }

  implicit class ColourImplicits(float: Double) {

    def *(colour: Colour): Colour =
      colour * float

  }

}

case class Colour(x: Double,
                  y: Double,
                  z: Double,
                  w: Double) {

  def copy(x: Double = x,
           y: Double = y,
           z: Double = z,
           w: Double = w) =
    new Colour(x = x, y = y, z = z, w = w)

  def *(scalar: Double): Colour =
    Colour(
      x = this.x * scalar,
      y = this.y * scalar,
      z = this.z * scalar,
      w = this.w
    )

  def /(scalar: Double): Colour =
    Colour(
      x = this.x / scalar,
      y = this.y / scalar,
      z = this.z / scalar,
      w = this.w
    )

  def +(scalar: Double): Colour =
    Colour(
      x = this.x + scalar,
      y = this.y + scalar,
      z = this.z + scalar,
      w = this.w
    )

  def +(colour: Colour): Colour =
    Colour(
      x = this.x + colour.x,
      y = this.y + colour.y,
      z = this.z + colour.z,
      w = this.w
    )

  def *(colour: Colour): Colour =
    Colour(
      x = this.x * colour.x,
      y = this.y * colour.y,
      z = this.z * colour.z,
      w = this.w * colour.w
    )
}

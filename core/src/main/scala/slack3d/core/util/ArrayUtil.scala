package slack3d.core.util

object ArrayUtil {

  /**
   * Input should be column major.
   *
   * @formatter:off
   * If matrix is
   * ┌             ┐
   * │ 1.0     2.0 │
   * │ 3.0     4.0 │
   * └             ┘
   * Input should be Array of follow two rows
   *
   * 0: Array(1.0, 3.0)
   * 1: Array(2.0, 4.0)
   * @formatter:on
   */
  def toString[T](columns: Array[Array[T]]): String = {
    val columnComponents: Array[Array[String]] =
      columns
        .map {
          column =>
            val maxLength = column.foldLeft(0)(_ max _.toString.length)

            column map {
              component =>
                val componentString = component.toString
                val shortBy = maxLength - componentString.length

                (" " * shortBy) + componentString
            }
        }

    val transposed = columnComponents.transpose

    transposed
      .zipWithIndex
      .flatMap {
        case (row, index) =>
          val midRow = row.mkString(" " * 4)

          val rowString = "│ " + midRow + " │"

          val head =
            if (index == 0)
              "┌ " + (" " * midRow.length) + " ┐"
            else
              ""

          val last =
            if (index == transposed.length - 1)
              "└ " + (" " * midRow.length) + " ┘"
            else
              ""

          Array(head, rowString, last).filter(_.nonEmpty)

      }
      .mkString("\n", "\n", "\n")
  }

}

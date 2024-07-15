case class Cube(faces: Array[Array[Array[Char]]]) {
  def this() = this(Array(
    Array.fill(3, 3)('W'), // White
    Array.fill(3, 3)('R'), // Red
    Array.fill(3, 3)('B'), // Blue
    Array.fill(3, 3)('O'), // Orange
    Array.fill(3, 3)('G'), // Green
    Array.fill(3, 3)('Y')  // Yellow
  ))

  def printCube(): Unit = {
    val faceNames = Array("Up", "Front", "Right", "Back", "Left", "Down")

    for (i <- faces.indices) {
      println(s"${faceNames(i)} Face:")
      faces(i).foreach { row =>
        println(row.mkString(" "))
      }
      println()
    }
  }

  def isSolved: Boolean = faces.forall(face => face.flatten.distinct.length == 1)
}

object CubeOperations {
  def applyMove(cube: Cube, move: String): Cube = {
    move match {
      case "U"  => rotateUp(cube)
      case "U'" => rotateUp(rotateUp(rotateUp(cube))) // counterclockwise
      case "D"  => rotateDown(cube)
      case "D'" => rotateDown(rotateDown(rotateDown(cube)))
      case "L"  => rotateLeft(cube)
      case "L'" => rotateLeft(rotateLeft(rotateLeft(cube)))
      case "R"  => rotateRight(cube)
      case "R'" => rotateRight(rotateRight(rotateRight(cube)))
      case "F"  => rotateFront(cube)
      case "F'" => rotateFront(rotateFront(rotateFront(cube)))
      case "B"  => rotateBack(cube)
      case "B'" => rotateBack(rotateBack(rotateBack(cube)))
      case _    => cube
    }
  }

  val moves = List("U", "U'", "D", "D'", "L", "L'", "R", "R'", "F", "F'", "B", "B'")

  // 0 = Up
  // 1 = front
  // 2 = right
  // 3 = back
  // 4 = left
  // 5 = down
  private def rotateFace(face: Array[Array[Char]]): Array[Array[Char]] = {
    Array(
      Array(face(2)(0), face(1)(0), face(0)(0)),
      Array(face(2)(1), face(1)(1), face(0)(1)),
      Array(face(2)(2), face(1)(2), face(0)(2))
    )
  }

  private def rotateUp(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(0) = rotateFace(cube.faces(0))
    for (i <- 0 to 2) {
      val temp = newFaces(1)(0)(i)
      newFaces(1)(0)(i) = newFaces(2)(0)(i)
      newFaces(2)(0)(i) = newFaces(3)(0)(i)
      newFaces(3)(0)(i) = newFaces(4)(0)(i)
      newFaces(4)(0)(i) = temp
    }
    Cube(newFaces)
  }

  private def rotateDown(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(5) = rotateFace(cube.faces(5))
    for (i <- 0 to 2) {
      val temp = newFaces(1)(2)(i)
      newFaces(1)(2)(i) = newFaces(4)(2)(i)
      newFaces(4)(2)(i) = newFaces(3)(2)(i)
      newFaces(3)(2)(i) = newFaces(2)(2)(i)
      newFaces(2)(2)(i) = temp
    }
    Cube(newFaces)
  }

  private def rotateLeft(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(4) = rotateFace(cube.faces(4))
    for (i <- 0 to 2) {
      val temp = newFaces(0)(i)(0)
      newFaces(0)(i)(0) = newFaces(3)(2 - i)(2)
      newFaces(3)(2 - i)(2) = newFaces(5)(i)(0)
      newFaces(5)(i)(0) = newFaces(1)(i)(0)
      newFaces(1)(i)(0) = temp
    }
    Cube(newFaces)
  }

  private def rotateRight(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(2) = rotateFace(cube.faces(2))
    for (i <- 0 to 2) {
      val temp = newFaces(0)(i)(2)
      newFaces(0)(i)(2) = newFaces(1)(i)(2)
      newFaces(1)(i)(2) = newFaces(5)(i)(2)
      newFaces(5)(i)(2) = newFaces(3)(2 - i)(0)
      newFaces(3)(2 - i)(0) = temp
    }
    Cube(newFaces)
  }

  private def rotateFront(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(1) = rotateFace(cube.faces(1))
    for (i <- 0 to 2) {
      val temp = newFaces(0)(2)(i)
      newFaces(0)(2)(i) = newFaces(4)(2 - i)(2)
      newFaces(4)(2 - i)(2) = newFaces(5)(0)(2 - i)
      newFaces(5)(0)(2 - i) = newFaces(2)(i)(0)
      newFaces(2)(i)(0) = temp
    }
    Cube(newFaces)
  }

  private def rotateBack(cube: Cube): Cube = {
    val newFaces = cube.faces.map(_.map(_.clone()))
    newFaces(3) = rotateFace(cube.faces(3))
    for (i <- 0 to 2) {
      val temp = newFaces(0)(0)(i)
      newFaces(0)(0)(i) = newFaces(2)(i)(2)
      newFaces(2)(i)(2) = newFaces(5)(2)(2 - i)
      newFaces(5)(2)(2 - i) = newFaces(4)(2 - i)(0)
      newFaces(4)(2 - i)(0) = temp
    }
    Cube(newFaces)
  }

  def scrambleCube(cube: Cube, movesCount: Int): Cube = {
    val randomMoves = CubeOperations.moves
    (1 to movesCount).foldLeft(cube) { (c, _) =>
      val move = randomMoves(scala.util.Random.nextInt(randomMoves.length))
      CubeOperations.applyMove(c, move)
    }
  }

  def preScrambleCube(cube: Cube, moves: List[String]): Cube = {
    moves.foldLeft(cube)((currentCube, move) => applyMove(currentCube, move))
  }
}

object Main extends App {
  val Unscrambled_Cube = new Cube()

//  println("Unscrambled Cube:")
//  Unscrambled_Cube.printCube()

  def scrambleCube(cube: Cube, movesCount: Int): Cube = {
    val randomMoves = Cube_Operations.moves
    (1 to movesCount).foldLeft(cube) { (c, _) =>
      val move = randomMoves(scala.util.Random.nextInt(randomMoves.length))
      Cube_Operations.applyMove(c, move)
    }
  }

  val scrambledCube = scrambleCube(Unscrambled_Cube, 20)

  println("Scrambled Cube:")
  scrambledCube.printCube()
}

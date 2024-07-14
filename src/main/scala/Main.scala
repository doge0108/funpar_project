import CubeOperations.{preScrambleCube, scrambleCube}

object Main extends App {
  val Unscrambled_Cube = new Cube()

//  println("Unscrambled Cube:")
//  Unscrambled_Cube.printCube()

//  val movesCount = 8
//  val scrambledCube = scrambleCube(Unscrambled_Cube, movesCount)
//  println("Scrambled Cube:")
//  scrambledCube.printCube()

  val predefinedMoves = List("U", "R", "F", "B", "L","D","U'","R'")
  val preScrambledCube = preScrambleCube(Unscrambled_Cube, predefinedMoves)
  println("Predefined Scrambled Cube:")
  preScrambledCube.printCube()


}

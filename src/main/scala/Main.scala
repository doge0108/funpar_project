import CubeOperations.{preScrambleCube, scrambleCube}

object Main extends App {
  val Unscrambled_Cube = new Cube()

//  println("Unscrambled Cube:")
//  Unscrambled_Cube.printCube()

//  val movesCount = 11
//  val scrambledCube = scrambleCube(Unscrambled_Cube, movesCount)
//  println("Scrambled Cube:")
//  scrambledCube.printCube()
//
//  println("Solving the cube")
//  val startTime = System.currentTimeMillis()
//  val solved = SequentialBacktrackingSolver.solve(scrambledCube)
//  if (solved) {
//    val endTime = System.currentTimeMillis()
//    val solvingTime = (endTime - startTime) / 1000.0
//    println("Cube solved")
//    println("Solved with moves: " + SequentialBacktrackingSolver.moves.reverse.mkString(", "))
//    println(f"It took: $solvingTime%.2f seconds to solve the cube")
//  } else {
//    println("Could not solve the cube.")
//  }

  val predefinedMoves = List("U", "R", "F","B","L","R'","F'","D")
  val preScrambledCube = preScrambleCube(Unscrambled_Cube, predefinedMoves)
  println("Predefined Scrambled Cube:")
  preScrambledCube.printCube()

  println("Solving the cube")
  val startTime = System.currentTimeMillis()
  val solvedPre = SequentialBacktrackingSolver.solve(preScrambledCube)
  if (solvedPre) {
    val endTime = System.currentTimeMillis()
    val solvingTime = (endTime - startTime) / 1000.0
    println("Predefined Scrambled Cube solved")
    println("Solved with moves: " + SequentialBacktrackingSolver.moves.reverse.mkString(", "))
    println(f"It took: $solvingTime%.2f seconds to solve the cube")
  } else {
    println("Could not solve the predefined scrambled cube.")
  }
}

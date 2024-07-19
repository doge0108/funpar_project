import CubeOperations.{preScrambleCube, scrambleCube}

object Main extends App {
  val Unscrambled_Cube = new Cube()
//  println("Unscrambled Cube:")
//  Unscrambled_Cube.printCube()

  val movesCount = 10
  val scrambledCube = scrambleCube(Unscrambled_Cube, movesCount)
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

//  println("Solving the cube")
//  val solvedPre = ParallelBacktrackingSolver.solve(scrambledCube)
//  if (solvedPre) {
//    println("Cube solved")
//  } else {
//    println("Could not solve the predefined scrambled cube.")
//  }

  val predefinedMoves = List("U", "R","D'","L","F'")
  val preScrambledCube = preScrambleCube(Unscrambled_Cube, predefinedMoves)
  println("Predefined Scrambled Cube:")
  preScrambledCube.printCube()

//  println("Solving the cube")
//  val startTime = System.currentTimeMillis()
//  val solvedPre = SequentialBacktrackingSolver.solve(preScrambledCube)
//  if (solvedPre) {
//    val endTime = System.currentTimeMillis()
//    val solvingTime = (endTime - startTime) / 1000.0
//    println("Predefined Scrambled Cube solved")
//    println("Solved with moves: " + SequentialBacktrackingSolver.moves.reverse.mkString(", "))
//    println(f"It took: $solvingTime%.2f seconds to solve the cube")
//  } else {
//    println("Could not solve the predefined scrambled cube.")
//  }

  println("Solving the cube")
  val (solved, solvedCube) = ParallelBacktrackingSolver.solve(preScrambledCube)
  if (solved) {
    println("Solved Cube:")
    solvedCube.printCube()
    println("Predefined cube solved")
  } else {
    println("Could not solve the predefined scrambled cube.")
  }
}

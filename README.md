# funpar_project
 
## Description
This project is a parallel backtracking solver for the Rubik's Cube. It uses both sequential and parallel approaches to solve the  rubik's cube efficiently.

## How it work
- The cube can be scrambled with predefined moves or randomly.
- The heuristic function estimates the minimum number of moves required to solve the cube from its current state.
- It uses the precomputed pattern databases from the BFS to get heuristic values for corner and edge configurations and combines them to guide the search algorithm efficiently.
- Sequential Search: This approach explores all possible moves at each depth level, backtracking when a bound is exceeded or a solution is found.
- Parallel Search: High-level parallelization is used for the initial few depth levels to leverage multiple processors, reducing the overall search time. As the search goes deeper, it
  switches to sequential search to minimize overhead.
- The solver then prints the sequence of moves required to solve the cube and the time taken.
- It can handle both simple and complex scrambles, with performance optimized for larger problems using parallelization.

## Try running it!
You have two choice to choose from the sequential and parallel version and also choose whether you want to use random scrambling or the predefined moves.

## Random scrambling
```scala
  val movesCount = 10
  val scrambledCube = scrambleCube(Unscrambled_Cube, movesCount)
  println("Scrambled Cube:")
  scrambledCube.printCube()
```
You can change the movescount to however much you want the cube to move. I would recommend to not go over 10 for the sequential version and 12 for the parallel version unless you want to keep it running till foreverland.

## Sequential for random scrambling
```scala
  println("Solving the cube")
  val startTime = System.currentTimeMillis()
  val solved = SequentialBacktrackingSolver.solve(scrambledCube)
  if (solved) {
    val endTime = System.currentTimeMillis()
    val solvingTime = (endTime - startTime) / 1000.0
    println("Cube solved")
    println("Solved with moves: " + SequentialBacktrackingSolver.moves.reverse.mkString(", "))
    println(f"It took: $solvingTime%.2f seconds to solve the cube")
  } else {
    println("Could not solve the cube.")
```

## Parallel for random scrambling
```scala
  println("Solving the cube")
  val solvedPre = ParallelBacktrackingSolver.solve(scrambledCube)
  if (solvedPre) {
    println("Cube solved")
  } else {
    println("Could not solve the predefined scrambled cube.")
  }
```

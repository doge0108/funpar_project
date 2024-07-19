# Parallel RUbik's Cube solver
 
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
## Predefined moves
```scala
  val predefinedMoves = List("U", "R","D'","L","F'","B","F","R'","U'","D","U","R")
  val preScrambledCube = preScrambleCube(Unscrambled_Cube, predefinedMoves)
  println("Predefined Scrambled Cube:")
  preScrambledCube.printCube()
```
These are the moves `val moves = List("U", "U'", "D", "D'", "L", "L'", "R", "R'", "F", "F'", "B", "B'")`
- U = up face
- D = down face
- L = left face
- R = right face
- F = front face
- B = back face
  
The ' behind the letter means that it would turn counterclockwise
These moves would turn the corresponding face that you choose.

## Sequential for predefined moves
```scala
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
```

## Parallel for predefined moves
```scala
  println("Solving the cube")
  val solvedPre = ParallelBacktrackingSolver.solve(preScrambledCube)
  if (solvedPre) {
    println("Predefined cube solved")
  } else {
    println("Could not solve the predefined scrambled cube.")
  }
```
## On to the results and comparison

|                     Predefined Cube                              | Sequential Time (seconds) | Parallel Time (seconds) |
|------------------------------------------------------------------|---------------------------|-------------------------|
| List("U", "R","D'","L","F'","B","F","R'","U'","D")               | 76.07 seconds             | 109.64 seconds          |
| List("U", "R","D'","L","F'","B","F","R'","U'","D","R'","D'")     | N/A                       | 147.49 seconds          |
| List("U", "R","D'","L","F'")                                     | 0.93 seconds              | 1.42 seconds            |
| List("U", "R","D'","L","F'","B'","R'")                           | 36.62 seconds             | 14.91 seconds           |
| List("U", "R","D'","L","F'","B'","R'","L","U'")                  | 950.05 seconds            | 1669.89 seconds         |
| List("U'", "R'","D'")                                            | 0.14 seconds              | 0.65 seconds            |
| List("U", "R","D'","L","F'","B","F","R'","U'","D","L")           | N/A                       | 1614.05 seconds         |
| List("R'","F'","L'","U'","B'","D'")                              | 2.21 seconds              | 3.09 seconds            |
| List("R'","F'","L'","U'","B'","D'","F","D")                      | 280.14 seconds            | 153.93 seconds          |

Regrettably after trying many things I still can't optimize the parallel further and have it be clearly the more efficient version. Although it is able to solve more moves, it takes longer on the simpler moves. 
 
## Poster
[Link text](Parallel-2.pdf)

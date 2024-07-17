import scala.concurrent._
import scala.concurrent.duration._
import java.util.concurrent.Executors
import scala.collection.parallel.CollectionConverters._
import scala.collection.concurrent.TrieMap

object ParallelBacktrackingSolver {
  private var moves: List[String] = List()
  val maxDepth: Int = 40
  val visited = new TrieMap[String, Boolean]()

  val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(threadPool)

  def heuristic(state: Cube): Int = {
    val cornerStr = ParallelHeuristic.serializeCorners(state)
    val edgeStr = ParallelHeuristic.serializeEdges(state)
    val cornerHeuristic = ParallelHeuristic.cornerPatternDB.getOrElse(cornerStr, 20)
    val edgeHeuristic = ParallelHeuristic.edgePatternDB.getOrElse(edgeStr, 20)
    Math.max(cornerHeuristic, edgeHeuristic)
  }

  def sequentialSearch(state: Cube, depth: Int, bound: Int, previousMove: String = "", path: List[String] = Nil): (Boolean, Int, List[String]) = {
    if (state.isSolved) {
      (true, depth, path.reverse)
    } else if (depth >= maxDepth) {
      (false, Int.MaxValue, path)
    } else {
      val fScore = depth + heuristic(state)
      if (fScore > bound) {
        (false, fScore, path)
      } else {
        var minVal = Int.MaxValue
        for (move <- CubeOperations.moves if move != previousMove.reverse) {
          val (found, newBound, newPath) = sequentialSearch(CubeOperations.applyMove(state, move), depth + 1, bound, move, move :: path)
          if (found) return (true, newBound, newPath)
          if (newBound < minVal) minVal = newBound
        }
        (false, minVal, path)
      }
    }
  }

  def parallelSearch(state: Cube, depth: Int, bound: Int, previousMove: String = "", path: List[String] = Nil): Future[(Boolean, Int, List[String])] = {
    if (depth <= 2) {
      val moveFutures = CubeOperations.moves.par.filter(_ != previousMove.reverse).map { move =>
        Future {
          sequentialSearch(CubeOperations.applyMove(state, move), depth + 1, bound, move, move :: path)
        }
      }.toList

      Future.sequence(moveFutures).map { results =>
        if (results.exists(_._1)) {
          val foundResult = results.find(_._1).get
          (true, foundResult._2, foundResult._3)
        } else {
          val minVal = results.map(_._2).min
          (false, minVal, path)
        }
      }
    } else {
      Future {
        sequentialSearch(state, depth, bound, previousMove, path)
      }
    }
  }

  def solve(initialState: Cube): Boolean = {
    val startTime = System.currentTimeMillis()
    moves = List()
    var bound = heuristic(initialState)
    while (true) {
      visited.clear()
      val result = Await.result(parallelSearch(initialState, 0, bound), Duration.Inf)
      result match {
        case (found, _, solutionPath) if found =>
          threadPool.shutdown()
          val endTime = System.currentTimeMillis()
          val solvingTime = (endTime - startTime) / 1000.0
          moves = solutionPath
          println(s"Solved with moves: ${moves.reverse.mkString(", ")}")
          println(f"It took: $solvingTime%.2f seconds to solve the cube")
          return true
        case (_, newBound, _) if newBound == Int.MaxValue =>
          threadPool.shutdown()
          println("No solution found, returning false")
          return false
        case (_, newBound, _) =>
          bound = newBound
      }
    }
    threadPool.shutdown()
    false
  }
}

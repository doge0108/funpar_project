import scala.collection.mutable

object SequentialBacktrackingSolver {
  var moves: List[String] = List()
  val maxDepth: Int = 40
  val visited = mutable.Set[String]()

  def heuristic(state: Cube): Int = {
//    val cornerStr = Heuristic.serializeCorners(state)
//    val edgeStr = Heuristic.serializeEdges(state)
//    val cornerHeuristic = Heuristic.cornerPatternDB.getOrElse(cornerStr, 20)
//    val edgeHeuristic = Heuristic.edgePatternDB.getOrElse(edgeStr, 20)
    val cornerStr = ParallelHeuristic.serializeCorners(state)
    val edgeStr = ParallelHeuristic.serializeEdges(state)
    val cornerHeuristic = ParallelHeuristic.cornerPatternDB.getOrElse(cornerStr, 20)
    val edgeHeuristic = ParallelHeuristic.edgePatternDB.getOrElse(edgeStr, 20)
    Math.max(cornerHeuristic, edgeHeuristic)
  }

  def search(state: Cube, depth: Int, bound: Int, previousMove: String = ""): (Boolean, Int) = {
    if (state.isSolved) return (true, depth)
    if (depth >= maxDepth) return (false, Int.MaxValue)

    val fScore = depth + heuristic(state)
    if (fScore > bound) return (false, fScore)

    var minVal = Int.MaxValue
    for (move <- CubeOperations.moves if move != previousMove.reverse) {
      val newCube = CubeOperations.applyMove(state, move)
      val (found, newBound) = search(newCube, depth + 1, bound, move)
      if (found) {
        moves = move :: moves
        return (true, newBound)
      }
      if (newBound < minVal) minVal = newBound
    }
    (false, minVal)
  }

  def solve(initialState: Cube): Boolean = {
    moves = List()
    var bound = heuristic(initialState)
    while (true) {
      visited.clear()
      val (found, newBound) = search(initialState, 0, bound)
      if (found) return true
      if (newBound == Int.MaxValue) return false
      bound = newBound
    }
    false
  }
}

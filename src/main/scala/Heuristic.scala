import scala.collection.mutable

object Heuristic {
  val cornerPatternDB = mutable.Map[String, Int]()
  val edgePatternDB = mutable.Map[String, Int]()

  def serializeCorners(cube: Cube): String = {
    cube.faces.slice(0, 4).map(_.flatten.mkString("")).mkString("")
  }

  def serializeEdges(cube: Cube): String = {
    cube.faces.slice(4, 6).map(_.flatten.mkString("")).mkString("")
  }

  def bfs(initialState: Cube, patternDB: mutable.Map[String, Int], serialize: Cube => String, maxMoves: Int = 30): Unit = {
    val queue = mutable.Queue[(Cube, Int)]()
    queue.enqueue((initialState, 0))
    patternDB(serialize(initialState)) = 0

    while (queue.nonEmpty) {
      val (currentCube, depth) = queue.dequeue()
      if (depth <= maxMoves) {
        for (move <- CubeOperations.moves) {
          val newCube = CubeOperations.applyMove(currentCube, move)
          val newCubeStr = serialize(newCube)
          if (!patternDB.contains(newCubeStr) || patternDB(newCubeStr) > depth + 1) {
            patternDB(newCubeStr) = depth + 1
            queue.enqueue((newCube, depth + 1))
          }
        }
      }
    }
  }

  def generate(): Unit = {
    val solvedCube = new Cube()
    bfs(solvedCube, cornerPatternDB, serializeCorners)
    bfs(solvedCube, edgePatternDB, serializeEdges)
  }
}

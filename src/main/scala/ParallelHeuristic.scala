import scala.collection.mutable
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.collection.parallel.CollectionConverters._

object ParallelHeuristic {
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
        val futures = CubeOperations.moves.par.map { move =>
          Future {
            val newCube = CubeOperations.applyMove(currentCube, move)
            val newCubeStr = serialize(newCube)
            if (!patternDB.contains(newCubeStr) || patternDB(newCubeStr) > depth + 1) {
              patternDB(newCubeStr) = depth + 1
              queue.enqueue((newCube, depth + 1))
            }
          }
        }.toList

        Await.result(Future.sequence(futures), Duration.Inf)
      }
    }
  }

  def generate(): Unit = {
    val solvedCube = new Cube()
    println("Generating corner pattern database")
    bfs(solvedCube, cornerPatternDB, serializeCorners)
    println("Generating edge pattern database")
    bfs(solvedCube, edgePatternDB, serializeEdges)
  }
}

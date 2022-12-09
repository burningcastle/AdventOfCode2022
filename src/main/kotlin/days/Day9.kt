package days

import Day
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

class Day9 : Day {

    override fun run() {
        val seriesOfMotions = File("src/main/resources/Day9.txt").readLines().map {
            val motion = it.split(" ")
            Pair(motion[0], motion[1].toInt())
        }

        // Part 1
        val rope = Rope(2) // head + tail
        seriesOfMotions.forEach { rope.moveHead(direction = it.first, times = it.second) }
        println("Part 1: " + rope.tailPositions.size) // 6197

        // Part 2
        val rope2 = Rope(10) // head + 9
        seriesOfMotions.forEach { rope2.moveHead(direction = it.first, times = it.second) }
        println("Part 2: " + rope2.tailPositions.size) // 2562
    }

    private class Rope(numberOfKnots: Int) {
        val tailPositions = mutableSetOf<Position>()
        private val knots = mutableListOf<Position>()

        init {
            repeat(numberOfKnots) { knots.add(Position(0, 0)) }
        }

        fun moveHead(direction: String, times: Int) {
            repeat(times) { moveSingle(direction) }
        }

        private fun moveSingle(direction: String) {
            // new head position
            knots[0] = when (direction) {
                "R" -> Position(knots[0].x + 1, knots[0].y)
                "U" -> Position(knots[0].x, knots[0].y + 1)
                "L" -> Position(knots[0].x - 1, knots[0].y)
                "D" -> Position(knots[0].x, knots[0].y - 1)
                else -> error("unknown direction")
            }

            // move all other knots
            for (i in 0 until knots.lastIndex) {
                knots[i + 1] = moveKnotBasedOnPredecessor(knots[i], knots[i + 1])
            }

            // remember tail position
            tailPositions.add(knots.last())
        }

        private fun moveKnotBasedOnPredecessor(predecessor: Position, knotToMove: Position): Position {
            val xDistance = predecessor.x - knotToMove.x
            val yDistance = predecessor.y - knotToMove.y
            return if (xDistance.absoluteValue == 2 || yDistance.absoluteValue == 2) {
                Position(knotToMove.x + xDistance.sign, knotToMove.y + yDistance.sign)
            } else {
                knotToMove
            }
        }

    }

    private data class Position(val x: Int, val y: Int)

}

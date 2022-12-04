package days

import Day
import java.io.File

class Day4 : Day {

    override fun run() {
        val lines = File("src/main/resources/Day4.txt").readLines()
        val assignmentPairs = lines.map { line ->
            val bounds = line.split(",", "-").map { it.toInt() }
            Pair(
                (bounds[0]..bounds[1]).toList(),
                (bounds[2]..bounds[3]).toList()
            )
        }

        // Part 1
        println("Part 1: " + assignmentPairs.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }) // 515

        // Part 2
        println("Part 2: " + assignmentPairs.count { it.first.intersect(it.second).isNotEmpty() }) // 883
    }

}


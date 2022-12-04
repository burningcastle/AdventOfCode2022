package days

import Day
import java.io.File

class Day4 : Day {

    override fun run() {
        val lines = File("src/main/resources/Day4.txt").readLines()
        val assignmentPairs = lines.map {
            val res = it.split(",", "-")
            Pair(
                Range(lowerBound = res[0].toInt(), upperBound = res[1].toInt()),
                Range(lowerBound = res[2].toInt(), upperBound = res[3].toInt())
            )
        }

        // order the ranges in each pair:
        // first range = larger
        // second range = smaller
        val orderedAssignmentPairs = assignmentPairs.map { pair ->
            if (pair.first.upperBound - pair.first.lowerBound < pair.second.upperBound - pair.second.lowerBound) {
                pair.second to pair.first
            } else {
                pair
            }
        }

        // Part 1
        println("Part 1: " + orderedAssignmentPairs.map { largerFullyContainsSmaller(it) }.count { it }) // 515

        // Part 2
        println("Part 2: " + orderedAssignmentPairs.map { rangesOverlap(it) }.count { it }) // 883
    }

    private fun largerFullyContainsSmaller(ranges: Pair<Range, Range>): Boolean {
        return ranges.first.upperBound >= ranges.second.upperBound &&
                ranges.first.lowerBound <= ranges.second.lowerBound
    }

    private fun rangesOverlap(ranges: Pair<Range, Range>): Boolean {
        return ranges.second.lowerBound in ranges.first.lowerBound..ranges.first.upperBound ||
                ranges.second.upperBound in ranges.first.lowerBound..ranges.first.upperBound
    }

    private data class Range(val lowerBound: Int, val upperBound: Int)

}


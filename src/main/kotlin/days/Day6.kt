package days

import Day
import java.io.File

class Day6 : Day {

    override fun run() {
        val input = File("src/main/resources/Day6.txt").readLines().first()

        // Part 1
        println("Part 1: " + (charactersBeforeMarker(input, 4))) // 1287

        // Part 2
        println("Part 2: " + (charactersBeforeMarker(input, 14))) // 3716

    }

    private fun charactersBeforeMarker(input: String, markerLength: Int): Int {
        for (i in input.indices) {
            if (input.drop(i).take(markerLength).toSet().size == markerLength)
                return i + markerLength
        }
        error("Marker not found")
    }

}

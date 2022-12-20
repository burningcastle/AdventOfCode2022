package days

import Day
import java.io.File

class Day6 : Day {

    override fun run() {
        val input = File("src/main/resources/Day6.txt").readLines().first()

        // Part 1
        println("Part 1: " + (input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4)) // 1287

        // Part 2
        println("Part 2: " + (input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14)) // 3716
    }

}

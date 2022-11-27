package days

import Day
import java.io.File

class Day1 : Day {

    override fun run() {
//        val lines = File("src/main/resources/Day1.example.txt").readLines()
        val lines = File("src/main/resources/Day1.txt").readLines()

        // Part 1
        println("Part 1: " + calcSomething(lines))

        // Part 2
        println("Part 2: " + calcSomething(lines))
    }

    private fun calcSomething(lines: List<String>): Int {
        return 0
    }

}

package days

import Day
import java.io.File

class Day1 : Day {

    override fun run() {
        val paragraphs = File("src/main/resources/Day1.txt").readText().split("\r\n\r\n", "\n\n", "\r\r")
        val sums = paragraphs.map { paragraph -> paragraph.lines().sumOf { it.toInt() } }
        val sortedSums = sums.sortedDescending()

        // Part 1
        println("Part 1: " + sortedSums.first()) // 71934

        // Part 2
        println("Part 2: " + sortedSums.take(3).sum()) // 211447
    }

}

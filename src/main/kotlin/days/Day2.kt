package days

import Day
import java.io.File

class Day2 : Day {

    override fun run() {
        val lines = File("src/main/resources/Day2.txt").readLines()

        // Part 1
        println("Part 1: " + lines.sumOf { getScore(it) }) // 9241

        // Part 2
        println("Part 2: " + lines.map { decrypt(it) }.sumOf { getScore(it) }) // 14610
    }

    // A = X = rock = 1
    // B = Y = paper = 2
    // C = Z = scissors = 3
    // win = 6, draw = 3, lose = 0
    // input = "<opponent choice> <own choice>"
    private fun getScore(input: String): Int = when (input) {
        "A X" -> 1 + 3
        "A Y" -> 2 + 6
        "A Z" -> 3 + 0
        "B X" -> 1 + 0
        "B Y" -> 2 + 3
        "B Z" -> 3 + 6
        "C X" -> 1 + 6
        "C Y" -> 2 + 0
        "C Z" -> 3 + 3
        else -> 0
    }

    // X = lose
    // Y = draw
    // Z = win
    private fun decrypt(input: String): String = when (input) {
        "A X" -> "A Z"
        "A Y" -> "A X"
        "A Z" -> "A Y"
        "B X" -> "B X"
        "B Y" -> "B Y"
        "B Z" -> "B Z"
        "C X" -> "C Y"
        "C Y" -> "C Z"
        "C Z" -> "C X"
        else -> ""
    }
}


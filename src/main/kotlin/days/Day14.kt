package days

import Day
import java.io.File
import kotlin.math.max
import kotlin.math.min


// TODO: Refactoring!
class Day14 : Day {

    private val filledFields = mutableSetOf<Pair<Int, Int>>()
    private val rocks = mutableSetOf<Pair<Int, Int>>()

    override fun run() {
        val input = File("src/main/resources/Day14.txt").readLines().map { it.split(" -> ").zipWithNext() }
        rocks.addAll(getRocks(input))
        filledFields.addAll(rocks)

        // Part 1
        println("Part 1: " + getAmountOfSand(hasFloor = false)) // 825

        filledFields.clear()
        filledFields.addAll(rocks)

        // Part 2
        println("Part 2: " + getAmountOfSand(hasFloor = true)) // 26729
    }

    private fun getAmountOfSand(hasFloor: Boolean): Int {
        val startOfAbyss = rocks.maxOf { it.second } + 2

        val sandSource = 500 to 0
        var sandCounter = 0
        loop@ while (true) {
            var unitOfSand = sandSource.first to sandSource.second
            var prevPosition: Pair<Int, Int>? = null
            while (unitOfSand != prevPosition) {
                prevPosition = unitOfSand
                unitOfSand = nextPosition(unitOfSand, floorY = if (hasFloor) startOfAbyss else Integer.MAX_VALUE)
                // part1 condition: sand is falling...
                if (unitOfSand.second > startOfAbyss) break@loop
            }
            sandCounter++
            filledFields.add(unitOfSand)
            // part2 condition: pile of sand reached sand source...
            if (unitOfSand == sandSource) break@loop
        }
        return sandCounter
    }

    private fun nextPosition(currentPosition: Pair<Int, Int>, floorY: Int): Pair<Int, Int> {
        val x = currentPosition.first
        val newY = currentPosition.second + 1

        if (newY == floorY) {
            // sand reached the floor
            return currentPosition
        }
        if (!filledFields.contains(x to newY)) {
            // sand falls 1 down
            return x to newY
        }
        if (!filledFields.contains(x - 1 to newY)) {
            // sand slides diagonal left
            return x - 1 to newY
        }
        if (!filledFields.contains(x + 1 to newY)) {
            // sand slides diagonal right
            return x + 1 to newY
        }
        // sand came to rest
        return currentPosition

    }

    private fun getRocks(input: List<List<Pair<String, String>>>): Set<Pair<Int, Int>> {
        val rocks = mutableSetOf<Pair<Int, Int>>()
        input.forEach { path ->
            path.forEach { line ->
                val pointA = line.first.split(",").map { it.toInt() }
                val pointB = line.second.split(",").map { it.toInt() }
                val lowerX = min(pointA[0], pointB[0])
                val upperX = max(pointA[0], pointB[0])
                val lowerY = min(pointA[1], pointB[1])
                val upperY = max(pointA[1], pointB[1])
                for (x in lowerX..upperX)
                    for (y in lowerY..upperY)
                        rocks.add(x to y)
            }
        }
        return rocks
    }


}

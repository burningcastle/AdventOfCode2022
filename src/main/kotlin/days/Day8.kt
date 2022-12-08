package days

import Day
import java.io.File
import kotlin.reflect.KFunction2

class Day8 : Day {

    override fun run() {
        val grid = File("src/main/resources/Day8.txt").readLines()
            .map { line -> line.toList().map { char -> char.digitToInt() } }

        var visibleTrees = 0
        val scores = mutableListOf<Int>()

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, treeSize ->
                val treesInColumn = grid.map { it[colIndex] }
                val treesToTheLeft = row.subList(0, colIndex)
                val treesToTheRight = row.subList(colIndex + 1, grid[rowIndex].size)
                val treesToTheTop = treesInColumn.subList(0, rowIndex)
                val treesToTheBottom = treesInColumn.subList(rowIndex + 1, treesInColumn.size)

                // Part 1
                val isOnEdge = rowIndex == 0 || colIndex == 0 ||
                        rowIndex == grid.indices.last || colIndex == row.indices.last
                val isVisibleToTheLeft = treesToTheLeft.find { it >= treeSize } == null
                val isVisibleToTheRight = treesToTheRight.find { it >= treeSize } == null
                val isVisibleToTheTop = treesToTheTop.find { it >= treeSize } == null
                val isVisibleToTheBottom = treesToTheBottom.find { it >= treeSize } == null
                if (isOnEdge || isVisibleToTheLeft || isVisibleToTheRight || isVisibleToTheTop || isVisibleToTheBottom) {
                    visibleTrees++
                }

                // Part 2
                val viewingDistanceLeft = getViewingDistance(treesToTheLeft, List<Int>::takeLastWhile, treeSize)
                val viewingDistanceRight = getViewingDistance(treesToTheRight, List<Int>::takeWhile, treeSize)
                val viewingDistanceTop = getViewingDistance(treesToTheTop, List<Int>::takeLastWhile, treeSize)
                val viewingDistanceBottom = getViewingDistance(treesToTheBottom, List<Int>::takeWhile, treeSize)
                val scenicScore =
                    viewingDistanceLeft * viewingDistanceRight * viewingDistanceTop * viewingDistanceBottom
                scores.add(scenicScore)
            }
        }

        // Part 1
        println("Part 1: " + visibleTrees) // 1711

        // Part 2
        println("Part 2: " + scores.maxOrNull()) // 301392

    }

    // TODO refactoring !
    private fun getViewingDistance(
        listOfTrees: List<Int>,
        takePredicate: KFunction2<List<Int>, (Int) -> Boolean, List<Int>>,
        treeSize: Int
    ): Int {
        var distance = takePredicate(listOfTrees) { it < treeSize }.size
        if (distance != listOfTrees.size) distance++ // can see at least 1 tree
        return distance
    }

}

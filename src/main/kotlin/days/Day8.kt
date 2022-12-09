package days

import Day
import java.io.File

class Day8 : Day {

    override fun run() {
        val grid = File("src/main/resources/Day8.txt").readLines()
            .map { line -> line.toList().map { char -> char.digitToInt() } }

        var visibleTrees = 0
        val scores = mutableListOf<Int>()

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, treeSize ->
                val treesInColumn = grid.map { it[colIndex] }
                val treesLeft = row.subList(0, colIndex)
                val treesRight = row.subList(colIndex + 1, grid[rowIndex].size)
                val treesTop = treesInColumn.subList(0, rowIndex)
                val treesBottom = treesInColumn.subList(rowIndex + 1, treesInColumn.size)

                // Part 1
                val isOnEdge = rowIndex == 0 || colIndex == 0 ||
                        rowIndex == grid.indices.last || colIndex == row.indices.last
                val isVisibleLeft = treesLeft.find { it >= treeSize } == null
                val isVisibleRight = treesRight.find { it >= treeSize } == null
                val isVisibleTop = treesTop.find { it >= treeSize } == null
                val isVisibleBottom = treesBottom.find { it >= treeSize } == null
                if (isOnEdge || isVisibleLeft || isVisibleRight || isVisibleTop || isVisibleBottom) {
                    visibleTrees++
                }

                // Part 2
                val viewingDistanceLeft = getViewingDistance(treesLeft.reversed(), treeSize)
                val viewingDistanceRight = getViewingDistance(treesRight, treeSize)
                val viewingDistanceTop = getViewingDistance(treesTop.reversed(), treeSize)
                val viewingDistanceBottom = getViewingDistance(treesBottom, treeSize)
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
    private fun getViewingDistance(trees: List<Int>, treeSize: Int): Int {
        var distance = trees.takeWhile { it < treeSize }.size
        if (distance != trees.size) distance++ // can see at least 1 tree
        return distance
    }

}

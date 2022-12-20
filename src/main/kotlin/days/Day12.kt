package days

import Day
import java.io.File
import kotlin.math.absoluteValue

class Day12 : Day {

    override fun run() {
        val input = File("src/main/resources/Day12.example.txt").readLines().map { it.toCharArray().toList() }
//        val input = File("src/main/resources/Day12.txt").readLines().map { it.toCharArray().toList() }
        val heightMap = HeightMap(input)
        val start = heightMap.getLocationOf('S')

        // Part 1
        val shortestPath = searchPath(start, heightMap) ?: emptyList()
        println("Part 1: " + (shortestPath.size - 1)) // 447

        // Part 2
        val allAFields = heightMap.map.flatten().filter { it.height == 'a' || it.height == 'S' }
        val allPaths = allAFields.mapNotNull { searchPath(it, heightMap) }
        println("Part 2: " + allPaths.minOfOrNull { it.size - 1 }) // 446 // ~5.5minutes
    }

    private fun searchPath(start: Location, map: HeightMap): List<Location>? {
        val paths: MutableList<List<Location>> = mutableListOf()
        paths.add(mutableListOf(start))
        while (paths.isNotEmpty() && paths.find { it.last().height == 'E' } == null) {
            expandPath(map, paths)
        }
        return paths.find { it.last().height == 'E' }
    }

    private fun expandPath(map: HeightMap, paths: MutableList<List<Location>>) {
        val path = paths.removeFirst()
        val newPaths = map.neighborsOf(path.last())
            .map { path + it }
            .filterNot { it.size != it.toSet().size } // exclude paths with duplicate location visits
            .filter { newPath ->         // wenn die position gleich ist aber kosten höher dann raus damit
                // find all paths where the newPaths last value is member of
                val otherPathsWithThisNode = paths.filter { p -> p.contains(newPath.last()) }
                val otherPathsWIthThisNodeAndBetterValue =
                    otherPathsWithThisNode.filter { path -> path.indexOf(newPath.last()) <= newPath.lastIndex }
                otherPathsWIthThisNodeAndBetterValue.isEmpty()
            }

        paths.addAll(newPaths)

        paths.sortBy {
            it.size - 1 + map.getDistanceToTarget(it.last())
        }
    }

    private class HeightMap(val charMap: List<List<Char>>) {
        val map = charMap.mapIndexed { row, chars ->
            chars.mapIndexed { col, c ->
                Location(col, row, c)
            }
        }
        private val target = getLocationOf('E')

        fun getLocationOf(c: Char): Location {
            val y = charMap.indexOfFirst { rows -> rows.contains(c) }
            return map[y][charMap[y].indexOf(c)]
        }

        fun neighborsOf(l: Location): List<Location> {
            val neighb = mutableListOf<Location>()
            if (l.y - 1 >= 0) {
                val neighbor = map[l.y - 1][l.x]
                if (l.hasAccessTo(neighbor)) neighb.add(neighbor)
            }
            if (l.y + 1 <= map.lastIndex) {
                val neighbor = map[l.y + 1][l.x]
                if (l.hasAccessTo(neighbor)) neighb.add(neighbor)
            }
            if (l.x - 1 >= 0) {
                val neighbor = map[l.y][l.x - 1]
                if (l.hasAccessTo(neighbor)) neighb.add(neighbor)
            }
            if (l.x + 1 <= map[0].lastIndex) {
                val neighbor = map[l.y][l.x + 1]
                if (l.hasAccessTo(neighbor)) neighb.add(neighbor)
            }
            return neighb
        }

        fun getDistanceToTarget(location: Location): Int {
            return (location.y - target.y).absoluteValue + (location.x - target.x).absoluteValue + (location.height.code - 'z'.code).absoluteValue
        }

    }

    data class Location(val x: Int, val y: Int, val height: Char) {
        fun hasAccessTo(neighbor: Location): Boolean {
            val hideToCompare = if (height == 'S') 'a'.code else height.code
            if (neighbor.height.code - 1 == hideToCompare) return true // neighbor is 1 higher
            if (neighbor.height.code <= hideToCompare && neighbor.height != 'E') return true // neighbor is same height or lower
            if (neighbor.height == 'E' && height in listOf('y', 'z')) return true // neighbor is 'E' AND is reachable
            return false
        }
    }

}

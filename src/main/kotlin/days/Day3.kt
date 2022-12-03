package days

import Day
import java.io.File

class Day3 : Day {

    override fun run() {
        val lines = File("src/main/resources/Day3.txt").readLines()
        val rucksacks = lines.map { it.toList() }

        // Part 1
        val wrongItems = rucksacks.flatMap {
            val compartments = it.chunked(it.size / 2)
            compartments[0].intersect(compartments[1])
        }
        println("Part 1: " + wrongItems.sumOf { getItemTypePriority(it) }) // 7845

        // Part 2
        val rucksackTriplets = rucksacks.map { it.toSet() }.chunked(3)
        val badges = rucksackTriplets.flatMap {
            it.reduce { acc, rucksack -> acc.intersect(rucksack) }
        }
        println("Part 2: " + badges.sumOf { getItemTypePriority(it) }) // 2790
    }

    //    Lowercase item types a through z have priorities 1 through 26.
    //    Uppercase item types A through Z have priorities 27 through 52.
    private fun getItemTypePriority(itemType: Char): Int {
        return if (itemType.isLowerCase()) {
            itemType.code - 96
        } else {
            itemType.code - 38
        }
    }

}


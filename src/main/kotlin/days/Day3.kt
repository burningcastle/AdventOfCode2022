package days

import Day
import java.io.File

class Day3 : Day {

    override fun run() {
        val rucksacks = File("src/main/resources/Day3.txt").readLines()

        // Part 1
        val wrongItems = rucksacks.flatMap {
            val compartments = it.chunked(it.length / 2)
            getCommonChars(compartments)
        }
        println("Part 1: " + wrongItems.sumOf { getItemTypePriority(it) }) // 7845

        // Part 2
        val rucksackTriplets = rucksacks.chunked(3)
        val badges = rucksackTriplets.flatMap {
            getCommonChars(it)
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

    private fun getCommonChars(strings: List<String>): Set<Char> {
        return strings.map { it.toSet() }.reduce { acc, chars -> acc.intersect(chars) }
    }

}


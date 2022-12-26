package days

import Day
import java.io.File

// TODO: Refactoring!
class Day13 : Day {

    override fun run() {
        val packets = File("src/main/resources/Day13.txt").readLines().filter { it.isNotEmpty() }.map {
            parsePacketData(it)
        }

        // Part 1
        val packetPairs = packets.chunked(2)
        val sumOfPacketIndexesInRightOrder = packetPairs.mapIndexed { index, packetPair ->
            if (isInOrder(packetPair[0], packetPair[1]) == 1) index + 1 else 0
        }.sum()
        println("Part 1: $sumOfPacketIndexesInRightOrder") // 6076

        // Part 2
        val divider1 = parsePacketData("[[2]]")
        val divider2 = parsePacketData("[[6]]")
        val allPackets = packets + divider1 + divider2
        val sortedPackets = allPackets.sortedWith { a, b -> isInOrder(b, a) }
        println("Part 2: " + (sortedPackets.indexOf(divider1) + 1) * (sortedPackets.indexOf(divider2) + 1)) // 24805
    }

    private fun parsePacketData(dataStr: String): Any {
        return when {
            dataStr == "[]" -> emptyList<Any>()
            dataStr.toIntOrNull() != null -> dataStr.toInt()
            else -> return listOf(splitList(dataStr).map { parsePacketData(it) })
        }
    }

    private fun splitList(lstStrRaw: String): List<String> {
        var remainingString = lstStrRaw.removePrefix("[").removeSuffix("]")
        val parts = mutableListOf<String>()

        var testedString = ""
        while (remainingString.isNotEmpty()) {
            testedString += remainingString.first()
            remainingString = remainingString.drop(1)
            val next = remainingString.firstOrNull()
            if ((next == null || next == ',') && isValidLstOrVal(testedString)) {
                parts.add(testedString)
                testedString = ""
                if (remainingString.isNotEmpty() && remainingString.first() == ',')
                    remainingString = remainingString.drop(1)
            }
        }
        
        return parts
    }

    private fun isValidLstOrVal(substr: String): Boolean {
        return substr.count { it == '[' } == substr.count { it == ']' }
    }

    // 1 = in order
    // -1 = not in order
    // 0 = undetermined
    private fun isInOrder(firstP: Any, secondP: Any): Int {
        val left = firstP as List<*>
        val right = secondP as List<*>

        for (i in left.indices) {
            var packetOrder = 0
            // Right side ran out of items, so inputs are not in the right order
            if (i > right.lastIndex) {
                packetOrder = -1
            } else if (left[i] is List<*> && right[i] is List<*>) {
                // both are lists
                packetOrder = isInOrder(left[i]!!, right[i]!!)
            } else if (left[i] is Int && right[i] is Int) {
                // both are int
                if ((left[i] as Int) < (right[i] as Int)) packetOrder = 1
                if ((left[i] as Int) > (right[i] as Int)) packetOrder = -1
            } else if (left[i] is Int) {
                // Mixed types; convert left to list and retry
                packetOrder = isInOrder(listOf(left[i]), right[i]!!)
            } else {
                // Mixed types; convert right to list and retry
                packetOrder = isInOrder(left[i]!!, listOf(right[i]))
            }
            // return result if order has been determined in this round
            if (packetOrder != 0) return packetOrder
        }

        // Left side ran out of items, so inputs are in the right order
        if (left.size < right.size) return 1

        // undetermined
        return 0
    }

}

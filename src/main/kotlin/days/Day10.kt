package days

import Day
import java.io.File

class Day10 : Day {

    private val signalValues = mutableListOf<Int>(1)
    private val pixels = mutableListOf<String>()

    override fun run() {
        val input = File("src/main/resources/Day10.txt").readLines()
        val instructions = input.map { val inst = it.split(" "); Pair(inst[0], inst.getOrNull(1)?.toIntOrNull() ?: 0) }

        instructions.forEach { inst ->
            when (inst.first) {
                "noop" -> performCycle(increase = 0)
                "addx" -> {
                    performCycle(increase = 0)
                    performCycle(increase = inst.second)
                }

                else -> error("invalid instruction")
            }
        }

        // Part 1
        val signalStrengths = signalValues.mapIndexed { index, x ->
            if (index % 40 == 19) { // 20th cycle = index 19
                (index + 1) * x
            } else 0
        }
        println("Part 1: " + signalStrengths.sum()) // 17020

        // Part 2
        println("Part 2:") // RLEZFLGE
        pixels.chunked(40).forEach { println(it.joinToString("")) }
    }

    private fun performCycle(increase: Int) {
        val x = signalValues.last()
        // Part 2
        val pixelPosition = pixels.lastIndex + 1
        if (pixelPosition % 40 in x - 1..x + 1)
            pixels.add("#")
        else
            pixels.add(".")
        // Part 1
        signalValues.add(x + increase)
    }

}

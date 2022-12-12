package days

import Day
import java.io.File

class Day11 : Day {

    override fun run() {
        val input = File("src/main/resources/Day11.txt").readText()
            .split("\r\n\r\n", "\n\n", "\r\r")
            .map { it.lines() }

        // Part 1
        println("Part 1: " + playKeepAway(getMonkeysFromInput(input), 20, 3)) // 64032

        // Part 2
        println("Part 2: " + playKeepAway(getMonkeysFromInput(input), 10000, 1)) // 12729522272
    }

    private fun getMonkeysFromInput(input: List<List<String>>): List<Monkey> {
        return input.map { rawMonkey ->
            Monkey(
                items = rawMonkey[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }.toMutableList(),
                operation = rawMonkey[2].substringAfter("Operation: new = "),
                moduloTest = rawMonkey[3].substringAfter("Test: divisible by ").toLong(),
                trueDestination = rawMonkey[4].substringAfter("If true: throw to monkey ").toInt(),
                falseDestination = rawMonkey[5].substringAfter("If false: throw to monkey ").toInt(),
            )
        }
    }

    private fun playKeepAway(monkeys: List<Monkey>, rounds: Int, worryLevelDivisor: Int): Long {
        // prime product to reduce worry level
        val monkeyProduct: Long = monkeys.map { it.moduloTest }.distinct().reduce { acc, l -> acc * l }
        repeat(rounds) {
            // ROUND
            monkeys.forEach { monkey ->
                val itemsForThisMonkey = monkey.items.size
                // TURN
                repeat(itemsForThisMonkey) {
                    val itemWithWorryLevel = monkey.items.removeFirst()
                    monkey.inspectCounter++
                    val newWorryLevel = evaluate(monkey.operation, itemWithWorryLevel)
                    val currentWorryLevel: Long = (newWorryLevel / worryLevelDivisor) % monkeyProduct
                    if (currentWorryLevel % monkey.moduloTest == 0L) {
                        monkeys[monkey.trueDestination].items.add(currentWorryLevel)
                    } else {
                        monkeys[monkey.falseDestination].items.add(currentWorryLevel)
                    }
                }
            }
        }

        val monkeyBusiness = monkeys.map { it.inspectCounter }.sortedDescending().take(2).reduce { acc, i -> acc * i }
        return monkeyBusiness
    }

    private fun evaluate(operation: String, old: Long): Long {
        val op = operation.replace("old", "" + old).split(" ")
        return when (op[1]) {
            "+" -> op[0].toLong() + op[2].toLong()
            "*" -> op[0].toLong() * op[2].toLong()
            else -> error("operation not supported")
        }
    }

    private class Monkey(
        val items: MutableList<Long>,
        val operation: String,
        val moduloTest: Long,
        val trueDestination: Int,
        val falseDestination: Int
    ) {
        var inspectCounter: Long = 0
    }

}

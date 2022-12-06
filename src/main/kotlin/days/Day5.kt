package days

import Day
import java.io.File

class Day5 : Day {

    override fun run() {
        val input = File("src/main/resources/Day5.txt").readText().split("\r\n\r\n", "\n\n", "\r\r")

        // parsing
        val stacksMatrix = input[0].lines().dropLast(1).map { line -> line.chunked(4).map { it[1] } }
        val stacks = transpose(stacksMatrix).map { it.filterNot { char -> char == ' ' } }
        val instructions = input[1].lines().map { line ->
            val raw = line.split("move ", " from ", " to ")
            Instruction(
                amount = raw[1].toInt(),
                from = raw[2].toInt(),
                to = raw[3].toInt()
            )
        }

        // Part 1
        val flattenedInstructions = instructions.flatMap { instruction ->
            generateSequence { instruction }
                .take(instruction.amount)
                .map { Instruction(1, instruction.from, instruction.to) }
        }
        val res1 = applyInstructions(stacks, flattenedInstructions)
        println("Part 1: " + res1.map { it.first() }.joinToString("")) // VJSFHWGFT

        // Part 2
        val res2 = applyInstructions(stacks, instructions)
        println("Part 2: " + res2.map { it.first() }.joinToString("")) // LCTQFBVZV
    }

    private fun applyInstructions(stacks: List<List<Char>>, instructions: List<Instruction>): List<List<Char>> {
        return instructions.fold(stacks) { acc, instruction -> applyInstruction(acc, instruction) }
    }

    // TODO refactoring !!!
    private fun applyInstruction(stacks: List<List<Char>>, instruction: Instruction): List<List<Char>> {
        val elementsToBeTransferred = stacks[instruction.from - 1].take(instruction.amount)
        val mutableStacks = stacks.map { it.toMutableList() }
        elementsToBeTransferred.reversed().forEach {
            mutableStacks[instruction.from - 1].removeFirst()
            mutableStacks[instruction.to - 1].add(0, it)
        }
        return mutableStacks
    }

    private fun <T> transpose(matrix: List<List<T>>): List<List<T>> {
        return matrix.first().indices.map { col -> matrix.map { row -> row[col] } }
    }

    private data class Instruction(val amount: Int, val from: Int, val to: Int)

}


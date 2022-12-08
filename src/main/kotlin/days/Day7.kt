package days

import Day
import java.io.File

class Day7 : Day {

    override fun run() {
        val commands = File("src/main/resources/Day7.txt").readText().split("$ ").drop(1)
            .map { command -> command.lines().filterNot { it.isEmpty() } }
            .map {
                val commandWithParam = it.first().split(" ")
                CommandWithOutput(
                    cmd = commandWithParam.first(),
                    param = commandWithParam.getOrNull(1),
                    output = it.drop(1)
                )
            }
        val tree = getTreeFromCommands(FileTreeNode("/", 0), commands)
        val rootNode = tree.getRootNode()
        val directorySizes = getDirectorySizesList(rootNode)

        // Part 1
        println("Part 1: " + directorySizes.filter { it <= 100000 }.sum()) // 2104783
        // Part 2
        val unusedSpace = 70000000 - rootNode.getDirectorySize()
        val requiredSpace = 30000000 - unusedSpace
        val smallestDirThatNeedsToBeDeleted = directorySizes.sorted().first { it >= requiredSpace }
        println("Part 2: $smallestDirThatNeedsToBeDeleted") // 5883165

    }

    private fun getDirectorySizesList(tree: FileTreeNode): List<Int> {
        return listOf(tree.getDirectorySize()) +
                tree.children.filter { it.hasChildren() }.flatMap { getDirectorySizesList(it) }
    }

    private fun getTreeFromCommands(node: FileTreeNode, commands: List<CommandWithOutput>): FileTreeNode {
        val command = commands.firstOrNull()
        return when (command?.cmd) {
            "ls" -> {
                command.output.forEach {
                    val fileEntry = it.split(" ")
                    node.addChild(
                        FileTreeNode(
                            name = fileEntry[1],
                            size = if (fileEntry[0] == "dir") 0 else fileEntry[0].toInt(),
                            parent = node
                        )
                    )
                }
                getTreeFromCommands(node, commands.drop(1))
            }
            "cd" -> when (command.param) {
                "/" -> getTreeFromCommands(FileTreeNode("/", 0), commands.drop(1))
                ".." -> getTreeFromCommands(node.parent!!, commands.drop(1))
                else -> getTreeFromCommands(node.getChild(command.param!!), commands.drop(1))
            }
            else -> node
        }
    }


    private class FileTreeNode(val name: String, val size: Int, val parent: FileTreeNode? = null) {
        val children = mutableListOf<FileTreeNode>()

        fun addChild(child: FileTreeNode) = children.add(child)

        fun hasChildren(): Boolean = children.isNotEmpty()

        fun getDirectorySize(): Int = children.sumOf { it.getDirectorySize() } + this.size

        fun getChild(childName: String): FileTreeNode {
            return children.find { it.name == childName } ?: error("child doesn't exist")
        }

        fun getRootNode(): FileTreeNode {
            return if (this.parent == null) this else this.parent.getRootNode()
        }

    }

    private data class CommandWithOutput(val cmd: String, val param: String?, val output: List<String>)

}

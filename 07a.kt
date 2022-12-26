sealed class Node(val name: String, val parent: Dir?)

class Dir(name: String, parent: Dir?) : Node(name, parent) {
    val children = mutableListOf<Node>()
}

class File(name: String, parent: Dir?, val size: Int) : Node(name, parent)

data class Command(val input: String, val output: List<String>)

fun readCommands(): List<Command> {
    val result = mutableListOf<Command>()
    var input: String? = null
    val output = mutableListOf<String>()
    while (true) {
        val line = readLine() ?: break
        if (line.startsWith('$')) {
            if (input != null) result.add(Command(input, output.toList()))
            output.clear()
            input = line.substringAfter("$ ")
        } else {
            output.add(line)
        }
    }
    result.add(Command(input!!, output))
    return result
}

fun solve(dir: Dir, sizes: MutableMap<Dir, Long>): Long {
    var result = 0L
    for (child in dir.children) {
        when (child) {
            is File -> result += child.size
            is Dir -> result += solve(child, sizes)
        }
    }
    return result.also { sizes[dir] = it }
}

fun main() {
    val root = Dir("", null)
    var cwd = root
    for ((input, output) in readCommands()) {
        val where = input.substringAfter("cd ")
        when {
            where != input -> when (where) {
                "/" -> cwd = root
                ".." -> cwd = cwd.parent!!
                else -> cwd = cwd.children.single { it.name == where } as Dir
            }
            input == "ls" -> {
                for (line in output) {
                    val name = line.substringAfter(" ")
                    val size = line.substringBefore(" ")
                    cwd.children +=
                        if (size == "dir") Dir(name, cwd)
                        else File(name, cwd, size.toInt())
                }
            }
            else -> error(input)
        }
    }

    val sizes = mutableMapOf<Dir, Long>()
    solve(root, sizes)

    println(sizes.toList().filter { it.second <= 100000L }.sumOf { it.second })
}

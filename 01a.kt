fun readLines(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    while (true) {
        val current = mutableListOf<String>()
        while (true) {
            val line = readLine() ?: return result.also { result.add(current) }
            if (line.isEmpty()) break
            current.add(line)
        }
        result.add(current)
    }
}

fun main() {
    val lines = readLines()
    println(lines.maxOfOrNull { list -> list.sumOf { it.toInt() } })
}

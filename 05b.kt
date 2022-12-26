fun main() {
    val N = 10
    val a = List(N) { mutableListOf<Char>() }
    while (true) {
        val line = readLine()!!
        if ("[" !in line) break
        for (i in 1 until N) {
            val j = i * 4 - 3
            val c = line.getOrNull(j)
            if (c != null && c != ' ') a[i].add(0, c)
        }
    }
    readLine()
    for (line in generateSequence(::readLine)) {
        val (n, from, to) = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
            .matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
        a[to].addAll(a[from].subList(a[from].size - n, a[from].size))
        repeat(n) { a[from].removeLast() }
    }
    println((1 until N).mapNotNull { a[it].lastOrNull() }.joinToString(""))
}

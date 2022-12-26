fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.split(",").map(String::toInt)
    }.toSet()
    var ans = 0
    for ((x, y, z) in a) {
        for (d in listOf(-1, 1)) {
            if (listOf(x + d, y, z) !in a) ans++
            if (listOf(x, y + d, z) !in a) ans++
            if (listOf(x, y, z + d) !in a) ans++
        }
    }
    println(ans)
}

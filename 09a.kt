val d = listOf(Vec(1, 0), Vec(0, 1), Vec(-1, 0), Vec(0, -1))

data class Vec(val x: Int, val y: Int) {
    operator fun plus(other: Vec): Vec =
        Vec(x + other.x, y + other.y)

    fun distance(other: Vec): Int =
        maxOf(Math.abs(x - other.x), Math.abs(y - other.y))
}

fun main() {
    var head = Vec(0, 0)
    var tail = head
    val used = mutableSetOf<Vec>()
    for (line in generateSequence(::readLine)) {
        val (dir, count) = line.split(" ")
        val dv = d["URDL".indexOf(dir)]
        repeat(count.toInt()) {
            val new = head + dv
            if (tail.distance(new) > 1) {
                tail = head
            }
            head = new
            used.add(tail)
        }
    }
    println(used.size)
}

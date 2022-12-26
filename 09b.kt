val d = listOf(Vec(1, 0), Vec(0, 1), Vec(-1, 0), Vec(0, -1))

data class Vec(val x: Int, val y: Int) {
    operator fun plus(other: Vec): Vec =
        Vec(x + other.x, y + other.y)

    fun distance(other: Vec): Int =
        maxOf(Math.abs(x - other.x), Math.abs(y - other.y))
}

fun main() {
    val a = Array(10) { Vec(0, 0) }
    val used = mutableSetOf<Vec>()
    for (line in generateSequence(::readLine)) {
        val (dir, count) = line.split(" ")
        val dv = d["URDL".indexOf(dir)]
        repeat(count.toInt()) {
            a[0] += dv
            for (i in 1 until a.size) {
                if (a[i].distance(a[i - 1]) > 1) {
                    a[i] += Vec(
                        if (a[i - 1].x > a[i].x) 1 else if (a[i - 1].x < a[i].x) -1 else 0,
                        if (a[i - 1].y > a[i].y) 1 else if (a[i - 1].y < a[i].y) -1 else 0,
                    )
                }
            }
            used.add(a.last())
        }
    }
    println(used.size)
}

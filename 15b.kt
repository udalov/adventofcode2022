fun main() {
    val number = "(-?\\d+)"
    val regex = "Sensor at x=$number, y=$number: closest beacon is at x=$number, y=$number".toRegex()
    val data = generateSequence(::readLine).map { line ->
        regex.matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
    }.toList()
    val N = 4000000
    for (line in data) {
        val (x1, y1, x2, y2) = line
        val dist = Math.abs(x1 - x2) + Math.abs(y1 - y2)
        for (x in (x1 - dist - 1)..(x1 + dist + 1)) {
            if (x !in 0..N) continue
            for (d in 0 until 2) {
                val y = y1 + (2 * d - 1) * (dist + 1 - Math.abs(x - x1))
                if (y !in 0..N) continue
                if (data.all { (x3, y3, x4, y4) ->
                    Math.abs(x3 - x) + Math.abs(y3 - y) > Math.abs(x3 - x4) + Math.abs(y3 - y4)
                }) {
                    println(x.toLong() * N + y)
                    return
                }
            }
        }
    }
}

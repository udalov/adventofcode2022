fun main() {
    val number = "(-?\\d+)"
    val regex = "Sensor at x=$number, y=$number: closest beacon is at x=$number, y=$number".toRegex()
    val y0 = 2000000
    val b = BooleanArray(10000000)
    val data = generateSequence(::readLine).map { line ->
        regex.matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
    }.toList()
    for (line in data) {
        val (x1, y1, x2, y2) = line
        val dist = Math.abs(x1 - x2) + Math.abs(y1 - y2)
        val diff = dist - Math.abs(y1 - y0)
        for (x in (x1 - diff)..(x1 + diff)) {
            b[x + b.size / 2] = true
        }
    }
    for ((_, _, x, y) in data) {
        if (y == y0) b[x + b.size / 2] = false
    }
    println(b.count { it })
}

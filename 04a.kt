fun main() {
    println(generateSequence(::readLine).count { line ->
        val (a, b, c, d) = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex().matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
        (a >= c && b <= d) || (c >= a && d <= b)
    })
}

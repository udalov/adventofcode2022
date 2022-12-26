fun main() {
    println(generateSequence(::readLine).count { line ->
        val (a, b, c, d) = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex().matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
        a in c..d || b in c..d || c in a..b || d in a..b
    })
}

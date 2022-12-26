fun main() {
    val sum = generateSequence(::readLine).sumOf { line ->
        var k = 1L
        var s = line
        var cur = 0L
        while (true) {
            val d = when (s.lastOrNull()) {
                '2' -> 2
                '1' -> 1
                '0' -> 0
                '-' -> -1
                '=' -> -2
                else -> break
            }
            cur += d * k
            k *= 5
            s = s.substring(0, s.length - 1)
        }
        cur
    }
    var ans = buildString {
        var k = sum
        while (k != 0L) {
            when (k % 5) {
                2L -> { k -= 2; append("2") }
                1L -> { k--; append("1") }
                0L -> append("0")
                4L -> { k++; append("-") }
                3L -> { k += 2; append("=") }
                else -> error(k)
            }
            k /= 5
        }
    }.reversed()
    println(ans)
}

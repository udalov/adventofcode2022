val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

fun main() {
    val a = Array(1000) { BooleanArray(200) }
    for (s in generateSequence(::readLine)) {
        val line = s.split(" -> ").map { it.split(",").map { x -> x.toInt() } }
        for (i in 0 until line.lastIndex) {
            val (x1, y1) = line[i]
            val (x2, y2) = line[i + 1]
            var (x, y) = x1 to y1
            val d = when {
                x1 == x2 -> if (y2 > y1) 1 else 3
                y1 == y2 -> if (x2 > x1) 0 else 2
                else -> error("$x1 $y1 $x2 $y2")
            }
            while (true) {
                a[x][y] = true
                if (x == x2 && y == y2) break
                x += DX[d]
                y += DY[d]
            }
        }
    }

    var ans = 0
    outer@while (true) {
        var x = 500
        var y = 0
        while (true) {
            if (y + 1 == a[x].size) break@outer
            if (!a[x][y + 1]) y++
            else if (!a[x - 1][y + 1]) { x--; y++ }
            else if (!a[x + 1][y + 1]) { x++; y++ }
            else {
                a[x][y] = true
                ans++
                break
            }
        }
    }

    println(ans)
}

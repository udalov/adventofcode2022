fun main() {
    val d = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
    val a = generateSequence(::readLine).toList()
    val n = a.size
    val m = a[0].length
    println((0 until n).sumOf { i ->
        (0 until m).sumOf { j ->
            if (d.any { (dx, dy) ->
                var x = i
                var y = j
                while (true) {
                    x += dx
                    y += dy
                    if (x !in 0 until n || y !in 0 until m) break
                    if (a[x][y] >= a[i][j]) return@any false
                }
                true
            }) 1.toInt() else 0
        }
    })
}

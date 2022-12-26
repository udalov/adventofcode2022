fun main() {
    val d = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
    val a = generateSequence(::readLine).toList()
    val n = a.size
    val m = a[0].length
    println((0 until n).maxOf { i ->
        (0 until m).maxOf { j ->
            d.fold(1) { acc, dd ->
                val (dx, dy) = dd
                var x = i
                var y = j
                var result = 0
                while (true) {
                    x += dx
                    y += dy
                    if (x !in 0 until n || y !in 0 until m) break
                    result++
                    if (a[x][y] >= a[i][j]) break
                }
                acc * result
            }
        }
    })
}

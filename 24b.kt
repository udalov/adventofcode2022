val dx = intArrayOf(1, 0, -1, 0, 0)
val dy = intArrayOf(0, 1, 0, -1, 0)

fun main() {
    val a = generateSequence(::readLine).toList()
    val n = a.size - 2
    val m = a[0].length - 2
    val b = List(4) { mutableListOf<Pair<Int, Int>>() }
    for (i in 0 until n) for (j in 0 until m) {
        val d = when (a[i + 1][j + 1]) {
            'v' -> 0
            '>' -> 1
            '^' -> 2
            '<' -> 3
            '.' -> continue
            else -> error(a[i + 1][j + 1])
        }
        b[d].add(i to j)
    }

    val q = mutableListOf<Triple<Int, Int, Int>>()
    val s = mutableSetOf<Triple<Int, Int, Int>>()
    var start = Triple(0, -1, 0)
    var trip = 0
    for (i in 0 until 3) {
        when (i) {
            1 -> start = Triple(trip, n, m - 1)
            2 -> start = Triple(trip, -1, 0)
        }

        q.clear()
        q.add(start)
        s.clear()
        s.add(start)
        var qb = 0
        while (qb < q.size) {
            val (dist, x, y) = q[qb++]
            if ((i % 2 == 0 && x == n && y == m - 1) || (i == 1 && x == -1 && y == 0)) {
                trip = dist
                break
            }

            for (d in 0 until 5) {
                val xx = x + dx[d]
                val yy = y + dy[d]
                if (yy !in 0 until m) continue
                if (!(xx in 0 until n || (xx == -1 && yy == 0) || (xx == n && yy == m - 1))) continue
                val v = Triple(dist + 1, xx, yy)
                if (v in s) continue
                if ((0 until 4).any { e ->
                    b[e].any { (bx, by) ->
                        (bx + dx[e] * (dist + 1) + 1000000 * n) % n == xx &&
                        (by + dy[e] * (dist + 1) + 1000000 * m) % m == yy
                    }
                }) continue
                q.add(v)
                s.add(v)
            }
        }
    }

    println(trip)
}

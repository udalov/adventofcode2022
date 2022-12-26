const val N = 200

val dx = intArrayOf(-1, -1, -1, 0, 1, 1, 1, 0)
val dy = intArrayOf(-1, 0, 1, 1, 1, 0, -1, -1)
val e = listOf(1, 5, 7, 3)

fun main() {
    val a = Array(2 * N) { BooleanArray(2 * N) }
    for ((i, line) in generateSequence(::readLine).withIndex()) {
        for (j in 0 until line.length) {
            a[i + N][j + N] = line[j] == '#'
        }
    }

    val b = Array(2 * N) { IntArray(2 * N) { -1 } }
    for (iteration in 0 until 10) {
        for (i in 1 until a.lastIndex) {
            for (j in 1 until a[i].lastIndex) {
                b[i][j] = -1
                if (!a[i][j] || (0 until 8).none { d -> a[i + dx[d]][j + dy[d]] }) continue

                for (ei in 0 until 4) {
                    val ee = e[(iteration + ei) % 4]
                    if (listOf(ee - 1, ee, (ee + 1) % 8).none { d -> a[i + dx[d]][j + dy[d]] }) {
                        b[i][j] = ee
                        break
                    }
                }
            }
        }

        for (i in 1 until a.lastIndex) {
            for (j in 1 until a[i].lastIndex) {
                if ((0 until 8).count { d -> b[i + dx[d]][j + dy[d]] == (d + 4) % 8 } > 1) {
                    for (d in 0 until 8) {
                        if (b[i + dx[d]][j + dy[d]] == (d + 4) % 8) {
                            b[i + dx[d]][j + dy[d]] = -1
                        }
                    }
                }
            }
        }

        for (i in 1 until a.lastIndex) {
            for (j in 1 until a[i].lastIndex) {
                if (b[i][j] != -1) {
                    check(a[i][j])
                    a[i + dx[b[i][j]]][j + dy[b[i][j]]] = true
                    a[i][j] = false
                }
            }
        }
    }

    val minX = (0 until a.size).first { i -> (0 until a.size).any { j -> a[i][j] } }
    val maxX = (0 until a.size).last { i -> (0 until a.size).any { j -> a[i][j] } }
    val minY = (0 until a[0].size).first { j -> (0 until a.size).any { i -> a[i][j] } }
    val maxY = (0 until a[0].size).last { j -> (0 until a.size).any { i -> a[i][j] } }
    val sum = a.sumOf { row -> row.sumOf { if (it) 1.toInt() else 0 } }
    println((maxX - minX + 1) * (maxY - minY + 1) - sum)
}

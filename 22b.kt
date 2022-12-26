import kotlin.math.sign

val dx = intArrayOf(0, 1, 0, -1)
val dy = intArrayOf(1, 0, -1, 0)

fun main() {
    val aa = generateSequence { readLine()!!.takeIf { it.isNotEmpty() } }.toList()
    val command = readLine()!!
    val n = aa.size
    val m = aa.maxOf { it.length }
    val a = Array(n) { i ->
        CharArray(m) { j ->
            aa[i].getOrNull(j) ?: ' '
        }
    }

    val move = Array(4) { Array(n) { Array<Triple<Int, Int, Int>?>(m) { null } } }

    fun dir(x1: Int, y1: Int, x2: Int, y2: Int): Int =
        (0 until 4).single { d -> dx[d] == (x2 - x1).sign && dy[d] == (y2 - y1).sign }

    fun ortho(x: Int, y: Int, d: Int): Int =
        listOf((d + 1) % 4, (d + 3) % 4).single { dd ->
            val xx = x + dx[dd]
            val yy = y + dy[dd]
            xx !in 0 until n || yy !in 0 until m || a[xx][yy] == ' '
        }

    fun go(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int, x4: Int, y4: Int) {
        val d1 = dir(x1, y1, x2, y2)
        val d2 = dir(x3, y3, x4, y4)
        val o1 = ortho(x1, y1, d1)
        val o2 = ortho(x3, y3, d2)

        var (x, y) = x1 to y1
        var (xx, yy) = x3 to y3
        repeat(50) {
            check(x in 0 until n && y in 0 until m && a[x][y] != ' ')
            check(xx in 0 until n && yy in 0 until m && a[xx][yy] != ' ')
            move[o1][x][y] = Triple(xx, yy, (o2 + 2) % 4)
            move[o2][xx][yy] = Triple(x, y, (o1 + 2) % 4)
            x += dx[d1]
            y += dy[d1]
            xx += dx[d2]
            yy += dy[d2]
        }
    }

    go(0, 50, 0, 100, 150, 0, 200, 0)
    go(0, 100, 0, 150, 199, 0, 199, 50)
    go(100, 0, 150, 0, 49, 50, -1, 50)
    go(100, 0, 100, 50, 50, 50, 100, 50)
    go(50, 99, 100, 99, 49, 100, 49, 150)
    go(150, 49, 200, 49, 149, 50, 149, 100)
    go(0, 149, 50, 149, 149, 99, 99, 99)

    var x = 0
    var y = 50
    var d = 0
    var at = 0
    while (at < command.length) {
        if (command[at] == 'R') {
            d = (d + 1) % 4
            at++
            continue
        }
        if (command[at] == 'L') {
            d = (d + 3) % 4
            at++
            continue
        }
        var next = at
        while (next < command.length && command[next].isDigit()) next++

        var k = command.substring(at, next).toInt()
        while (k-- > 0) {
            var xx = x + dx[d]
            var yy = y + dy[d]
            var dd = d
            val p = move[d][x][y]
            if (p != null) {
                xx = p.first
                yy = p.second
                dd = p.third
            }
            if (a[xx][yy] == '#') break
            check(a[xx][yy] != ' ')
            x = xx
            y = yy
            d = dd
        }
        at = next
    }
    println(1000 * (x + 1) + 4 * (y + 1) + d)
}

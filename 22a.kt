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
    val rowBegin = IntArray(n) { i -> (0 until m).first { j -> a[i][j] != ' ' } }
    val rowEnd = IntArray(n) { i -> (0 until m).last { j -> a[i][j] != ' ' } }
    val colBegin = IntArray(m) { j -> (0 until n).first { i -> a[i][j] != ' ' } }
    val colEnd = IntArray(m) { j -> (0 until n).last { i -> a[i][j] != ' ' } }

    var x = 0
    var y = rowBegin[x]
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
            when {
                d == 0 && y == rowEnd[x] -> yy = rowBegin[x]
                d == 1 && x == colEnd[y] -> xx = colBegin[y]
                d == 2 && y == rowBegin[x] -> yy = rowEnd[x]
                d == 3 && x == colBegin[y] -> xx = colEnd[y]
            }
            if (a[xx][yy] == '#') break
            x = xx
            y = yy
        }
        at = next
    }
    println(1000 * (x + 1) + 4 * (y + 1) + d)
}

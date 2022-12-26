val DX = intArrayOf(1, -1, 0, 0, 0, 0)
val DY = intArrayOf(0, 0, 1, -1, 0, 0)
val DZ = intArrayOf(0, 0, 0, 0, 1, -1)

fun main() {
    val a = generateSequence(::readLine).map { line ->
        line.split(",").map(String::toInt)
    }.toSet()
    val N = 24
    val b = Array(N) { Array(N) { BooleanArray(N) } }
    b[0][0][0] = true
    val q = mutableListOf<List<Int>>()
    q.add(listOf(0, 0, 0))
    var qb = 0
    var ans = 0
    while (qb < q.size) {
        val (x, y, z) = q[qb++]
        for (d in 0 until 6) {
            val (xx, yy, zz) = listOf(x + DX[d], y + DY[d], z + DZ[d])
            if (listOf(xx - 1, yy - 1, zz - 1) in a) {
                ans++
                continue
            }
            if (xx !in 0 until N || yy !in 0 until N || zz !in 0 until N) continue
            if (b[xx][yy][zz]) continue
            b[xx][yy][zz] = true
            q.add(listOf(xx, yy, zz))
        }
    }
    println(ans)
}

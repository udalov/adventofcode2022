data class Pos(val x: Int, val y: Int) {
    operator fun plus(other: Pos): Pos =
        Pos(x + other.x, y + other.y)
}

val dirs = listOf(Pos(1, 0), Pos(0, 1), Pos(-1, 0), Pos(0, -1))

operator fun List<List<Int>>.contains(pos: Pos): Boolean = pos.x in indices && pos.y in this[pos.x].indices
operator fun List<List<Int>>.get(pos: Pos): Int = this[pos.x][pos.y]

fun main() {
    val lines = generateSequence(::readLine).toList()
    val a = lines.map { line ->
        line.map {
            val height = when (it) {
                'S' -> 'a'
                'E' -> 'z'
                else -> it
            }
            height - 'a'
        }
    }
    val n = a.size
    val m = a[0].size

    lateinit var start: Pos
    lateinit var finish: Pos
    for (i in 0 until n) for (j in 0 until m) when (lines[i][j]) {
        'S' -> start = Pos(i, j)
        'E' -> finish = Pos(i, j)
    }

    val queue = ArrayDeque<Pos>()
    val dist = mutableMapOf<Pos, Int>()
    dist[start] = 0
    queue.add(start)

    while (true) {
        val v = queue.removeFirst()
        if (v == finish) break
        
        for (dir in dirs) {
            val w = v + dir
            if (w !in dist && w in a && a[w] <= a[v] + 1) {
                dist[w] = dist[v]!! + 1
                queue.addLast(w)
            }
        }
    }

    println(dist[finish])
}

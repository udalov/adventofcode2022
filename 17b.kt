fun main() {
    val jets = readLine()!!

    val figures = mutableListOf<List<String>>()
    figures.add(listOf("####"))
    figures.add(listOf(".#.", "###", ".#."))
    figures.add(listOf("###", "..#", "..#"))
    figures.add(listOf("#", "#", "#", "#"))
    figures.add(listOf("##", "##"))

    val a = IntArray(1000000) { x ->
        if (x == 0) (1 shl 9) - 1 else (1 shl 8) + 1 
    }

    operator fun IntArray.get(x: Int, y: Int): Boolean =
        a[x] and (1 shl y) != 0

    operator fun IntArray.set(x: Int, y: Int, value: Boolean) {
        check(value)
        a[x] = a[x] or (1 shl y)
    }

    val target = 1000000000000L

    var top = 1
    var jet = 0
    val seen = List(jets.length) { mutableMapOf<Long, Pair<Int, Int>>() }
    var period: Int? = null
    var increment: Int? = null
    for (iteration in 0 until 100000) {
        if (period != null && (target - iteration) % period == 0L) {
            println(top - 1L + ((target - iteration) / period) * increment!!)
            break
        }

        val figure = figures[iteration % figures.size]
        val n = figure.size
        val m = figure[0].length
        var x = top + 3
        var y = 3

        fun tryMove(mx: Int, my: Int): Boolean {
            val move = (0 until n).all { dx ->
                (0 until m).all { dy ->
                    figure[dx][dy] == '.' || !a[x + dx + mx, y + dy + my]
                }
            }
            if (move) {
                x += mx
                y += my
                return true
            }
            return false
        }

        while (true) {
            if (jets[jet] == '>') tryMove(0, 1)
            else if (jets[jet] == '<') tryMove(0, -1)
            jet = (jet + 1) % jets.length
            if (!tryMove(-1, 0)) break
        }
        for (dx in 0 until n) for (dy in 0 until m) if (figure[dx][dy] == '#') {
            check(!a[x + dx, y + dy])
            a[x + dx, y + dy] = true
        }

        while ((1..7).any { a[top, it] }) top++

        var hash = 0L
        if (iteration > 20 && period == null) {
            for (xx in top downTo top - 15) {
                for (yy in 0 until 9) hash = hash * 13 + (if (a[xx, yy]) 3 else 7)
            }
            if (hash in seen[jet]) {
                val (prevIteration, prevTop) = seen[jet][hash]!!
                period = iteration - prevIteration
                increment = top - 1 - prevTop
            }
            seen[jet][hash] = iteration to (top - 1)
        }
    }
}

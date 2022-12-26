fun main() {
    val jets = readLine()!!

    val figures = mutableListOf<List<String>>()
    figures.add(listOf("####"))
    figures.add(listOf(".#.", "###", ".#."))
    figures.add(listOf("###", "..#", "..#"))
    figures.add(listOf("#", "#", "#", "#"))
    figures.add(listOf("##", "##"))

    val a = Array(100000) { x ->
        BooleanArray(9) { y ->
            x == 0 || y == 0 || y == 8
        }
    }
    var top = 1
    var jet = 0
    for (iteration in 0 until 2022) {
        val figure = figures[iteration % figures.size]
        val n = figure.size
        val m = figure[0].length
        var x = top + 3
        var y = 3

        fun tryMove(mx: Int, my: Int): Boolean {
            val move = (0 until n).all { dx ->
                (0 until m).all { dy ->
                    figure[dx][dy] == '.' || !a[x + dx + mx][y + dy + my]
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
            check(!a[x + dx][y + dy])
            a[x + dx][y + dy] = true
        }

        while ((1 until a[top].lastIndex).any { a[top][it] }) top++
    }
    println(top - 1)
}

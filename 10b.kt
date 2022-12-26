fun main() {
    var i = 0
    var x = 1
    val ans = Array(6) { CharArray(40) { '.' } }

    fun eq(a: Int, b: Int): Boolean =
        (a - b) % 40 == 0

    fun draw(i: Int, x: Int) {
        if (eq(i, x - 1) || eq(i, x) || eq(i, x + 1)) {
            ans[i / ans[0].size][i % ans[0].size] = '#'
        }
    }

    for (line in generateSequence(::readLine)) {
        val s = line.split(" ")
        draw(i, x)
        i++
        if (s[0] == "addx") {
            draw(i, x)
            i++
            x += s[1].toInt()
        }
    }
    println(ans.joinToString("\n") { it.joinToString("") })
}

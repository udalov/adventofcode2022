fun main() {
    val s = readLine()!!
    var i = 4
    while (true) {
        if (s.substring(i - 4, i).toSet().size == 4) break
        i++
    }
    println(i)
}

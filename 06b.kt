fun main() {
    val s = readLine()!!
    var i = 14
    while (true) {
        if (s.substring(i - 14, i).toSet().size == 14) break
        i++
    }
    println(i)
}

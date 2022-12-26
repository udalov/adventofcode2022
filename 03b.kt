fun main() {
    var ans = 0
    while (true) {
        val c = (readLine() ?: break).toSet().intersect(readLine()!!.toSet()).intersect(readLine()!!.toSet()).single()
        ans += if (c in 'a'..'z') c - 'a' + 1 else c - 'A' + 27
    }
    println(ans)
}

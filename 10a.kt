fun main() {
    var i = 0
    var x = 1
    var ans = 0
    for (line in generateSequence(::readLine)) {
        val s = line.split(" ")
        i++
        if (i % 40 == 20) ans += x * i
        if (s[0] == "addx") {
            i++
            if (i % 40 == 20) ans += x * i
            x += s[1].toInt()
        }
    }
    println(ans)
}

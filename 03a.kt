fun main() {
    println(generateSequence(::readLine).sumOf { line ->
        val a = line.substring(0, line.length / 2)
        val b = line.substring(line.length / 2, line.length)
        val c = a.toSet().intersect(b.toSet()).single()
        if (c in 'a'..'z') c - 'a' + 1 else c - 'A' + 27
    })
}

fun main() {
    println(generateSequence(::readLine).sumOf { line ->
        (line.split(" ")[1][0] - 'W') + when {
            line in listOf("A Z", "B X", "C Y") -> 0
            line in listOf("A Y", "B Z", "C X") -> 6
            else -> 3
        }
    })
}

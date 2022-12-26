fun main() {
    println(generateSequence(::readLine).sumOf { line ->
        (line.split(" ")[1][0] - 'X') * 3 + when {
            line in listOf("A Y", "B X", "C Z") -> 1
            line in listOf("A Z", "B Y", "C X") -> 2
            else -> 3
        }
    })
}

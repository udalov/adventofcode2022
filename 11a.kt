fun readLines(): List<List<String>> {
    val result = mutableListOf<List<String>>()
    while (true) {
        val current = mutableListOf<String>()
        while (true) {
            val line = readLine() ?: return result.also { result.add(current) }
            if (line.isEmpty()) break
            current.add(line)
        }
        result.add(current)
    }
}

class Operation(val first: String, val op: String, val second: String) {
    private val firstValue = first.toIntOrNull()
    private val secondValue = second.toIntOrNull()

    fun compute(old: Int): Int {
        val a = if (first == "old") old else firstValue!!
        val b = if (second == "old") old else secondValue!!
        return when (op) {
            "+" -> a + b
            "*" -> a * b
            else -> error(op)
        }
    }
}

class Monkey(
    val items: ArrayDeque<Int>,
    val operation: Operation,
    val divBy: Int,
    val ifTrue: Int,
    val ifFalse: Int,
    var throws: Int,
)

fun main() {
    val a = mutableListOf<Monkey>()
    for (lines in readLines()) {
        val items = ArrayDeque(lines[1].substringAfter("  Starting items: ").split(", ").map(String::toInt))
        val operation = lines[2].substringAfter("  Operation: new = ").split(" ").let { (first, op, second) -> Operation(first, op, second) }
        val divBy = lines[3].substringAfter("  Test: divisible by ").toInt()
        val ifTrue = lines[4].substringAfter("    If true: throw to monkey ").toInt()
        val ifFalse = lines[5].substringAfter("    If false: throw to monkey ").toInt()
        a.add(Monkey(items, operation, divBy, ifTrue, ifFalse, 0))
    }

    repeat(20) {
        for (monkey in a) {
            while (true) {
                val old = monkey.items.removeFirstOrNull() ?: break
                val new = monkey.operation.compute(old) / 3
                a[if (new % monkey.divBy == 0) monkey.ifTrue else monkey.ifFalse].items.addLast(new)
                monkey.throws++
            }
        }
    }

    val sorted = a.sortedByDescending { it.throws }
    println(sorted[0].throws * sorted[1].throws)
}

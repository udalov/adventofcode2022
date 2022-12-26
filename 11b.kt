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

class Operation(val first: String, val op: String, val second: String, val mod: Long) {
    private val firstValue = first.toLongOrNull()
    private val secondValue = second.toLongOrNull()

    fun compute(old: Long): Long {
        val a = if (first == "old") old else firstValue!!
        val b = if (second == "old") old else secondValue!!
        return when (op) {
            "+" -> (a + b) % mod
            "*" -> (a * b) % mod
            else -> error(op)
        }
    }
}

class Monkey(
    val items: ArrayDeque<Long>,
    val operation: Operation,
    val divBy: Int,
    val ifTrue: Int,
    val ifFalse: Int,
    var throws: Int,
)

fun main() {
    val input = readLines()

    val modulos = input.map { lines ->
        lines[3].substringAfter("  Test: divisible by ").toInt()
    }
    val mod = modulos.fold(1L) { acc, element -> acc * element }

    val a = mutableListOf<Monkey>()
    for ((i, lines) in input.withIndex()) {
        val items = ArrayDeque(lines[1].substringAfter("  Starting items: ").split(", ").map(String::toLong))
        val operation = lines[2].substringAfter("  Operation: new = ").split(" ").let { (first, op, second) -> Operation(first, op, second, mod) }
        val ifTrue = lines[4].substringAfter("    If true: throw to monkey ").toInt()
        val ifFalse = lines[5].substringAfter("    If false: throw to monkey ").toInt()
        a.add(Monkey(items, operation, modulos[i], ifTrue, ifFalse, 0))
    }

    repeat(10000) {
        for (monkey in a) {
            while (true) {
                val old = monkey.items.removeFirstOrNull() ?: break
                val new = monkey.operation.compute(old)
                a[if (new % monkey.divBy == 0L) monkey.ifTrue else monkey.ifFalse].items.addLast(new)
                monkey.throws++
            }
        }
    }

    val sorted = a.sortedByDescending { it.throws }
    println(sorted[0].throws.toLong() * sorted[1].throws)
}

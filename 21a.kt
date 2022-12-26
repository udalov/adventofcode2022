sealed class Action {
    abstract fun compute(a: Map<String, Action>): Long
}

class Op(val left: String, val op: String, val right: String) : Action() {
    override fun compute(a: Map<String, Action>): Long {
        val l = a[left]!!.compute(a)
        val r = a[right]!!.compute(a)
        return when (op) {
            "+" -> l + r
            "-" -> l - r
            "*" -> l * r
            "/" -> l / r
            else -> error(op)
        }
    }
}

class Num(val value: Long) : Action() {
    override fun compute(a: Map<String, Action>): Long = value
}

fun main() {
    val a = mutableMapOf<String, Action>()
    for (line in generateSequence(::readLine)) {
        val (name, rest) = line.split(": ")
        val num = rest.toLongOrNull()
        if (num != null) {
            a[name] = Num(num)
        } else {
            val parts = rest.split(" ")
            a[name] = Op(parts[0], parts[1], parts[2])
        }
    }
    println(a["root"]!!.compute(a))
}

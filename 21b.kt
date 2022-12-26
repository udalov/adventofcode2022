typealias Graph = Map<String, Action>

sealed class Action {
    abstract fun compute(a: Graph): Long?
    abstract fun solve(a: Graph, result: Long): Long
}

class Op(val left: String, val op: String, val right: String) : Action() {
    override fun compute(a: Graph): Long? {
        val l = a[left]!!.compute(a) ?: return null
        val r = a[right]!!.compute(a) ?: return null
        return when (op) {
            "+" -> l + r
            "-" -> l - r
            "*" -> l * r
            "/" -> l / r
            else -> error(op)
        }
    }

    override fun solve(a: Graph, result: Long): Long {
        val al = a[left]!!
        val ar = a[right]!!
        val l = al.compute(a)
        val r = ar.compute(a)
        if (l == null && r != null) return when (op) {
            "+" -> al.solve(a, result - r)
            "-" -> al.solve(a, result + r)
            "*" -> al.solve(a, result / r)
            "/" -> al.solve(a, result * r)
            else -> error(op)
        } else if (l != null && r == null) return when (op) {
            "+" -> ar.solve(a, result - l)
            "-" -> ar.solve(a, l - result)
            "*" -> ar.solve(a, result / l)
            "/" -> ar.solve(a, l / result)
            else -> error(op)
        } else error("$l $r")
    }
}

class Num(val value: Long) : Action() {
    override fun compute(a: Graph): Long = value

    override fun solve(a: Graph, result: Long): Long = error(value)
}

object Human : Action() {
    override fun compute(a: Graph): Long? = null

    override fun solve(a: Graph, result: Long): Long = result
}

fun main() {
    val a = mutableMapOf<String, Action>()
    for (line in generateSequence(::readLine)) {
        val (name, rest) = line.split(": ")
        val num = rest.toLongOrNull()
        if (name == "humn") {
            a[name] = Human
        } else if (num != null) {
            a[name] = Num(num)
        } else {
            val parts = rest.split(" ")
            a[name] = Op(parts[0], parts[1], parts[2])
        }
    }
    val root = a["root"]!! as Op
    val left = a[root.left]!!
    val right = a[root.right]!!
    val l = left.compute(a)
    val r = right.compute(a)
    println(when {
        l != null && r == null -> right.solve(a, l)
        l == null && r != null -> left.solve(a, r)
        else -> error("$l $r")
    })
}

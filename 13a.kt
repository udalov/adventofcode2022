sealed class Packet : Comparable<Packet>

class PList(val data: List<Packet>) : Packet() {
    override fun compareTo(other: Packet): Int {
        when (other) {
            is PList -> {
                val n = data.size
                val m = other.data.size
                var i = 0
                var j = 0
                while (i < n && j < m) {
                    val c = data[i++].compareTo(other.data[j++])
                    if (c != 0) return c
                }
                if (i == n) return if (j == m) 0 else -1
                check(j == m)
                return 1
            }
            is PInt -> return this.compareTo(PList(listOf(other)))
        }
    }
}

class PInt(val value: Int) : Packet() {
    override fun compareTo(other: Packet): Int =
        when (other) {
            is PList -> -other.compareTo(this)
            is PInt -> value.compareTo(other.value)
        }
}

class Parser(val s: String) {
    var at = 0

    fun packet(): Packet =
        if (s[at] == '[') list() else int()

    fun list(): PList {
        check(s[at++] == '[')
        if (s[at] == ']') return PList(emptyList()).also { at++ }
        val data = mutableListOf<Packet>()
        while (true) {
            data.add(packet())
            if (s[at++] == ']') break
        }
        return PList(data)
    }

    fun int(): PInt {
        var start = at
        while (at in s.indices && s[at].isDigit()) at++
        return PInt(s.substring(start, at).toInt())
    }
}

fun main() {
    var index = 1
    var ans = 0
    do {
        val a = Parser(readLine()!!).list()
        val b = Parser(readLine()!!).list()
        if (a < b) ans += index
        index++
    } while (readLine() != null)
    println(ans)
}

class Node(val value: Long) {
    lateinit var prev: Node
    lateinit var next: Node
}

fun main() {
    val a = generateSequence(::readLine).map { Node(it.toLong() * 811589153L) }.toList()
    val n = a.size
    for (i in a.indices) {
        a[i].prev = a[(i - 1 + n) % n]
        a[i].next = a[(i + 1) % n]
    }
    repeat(10) {
        for (v in a) {
            var k = ((if (v.value >= 0) v.value else (n - 1) * 1_000_000_000_000_000L + v.value) % (n - 1)).toInt()
            while (k-- > 0) {
                val prev = v.prev
                val next = v.next
                prev.next = next
                next.prev = prev
                v.next = next.next
                next.next.prev = v
                next.next = v
                v.prev = next
            }
        }
    }

    var cur = a[0]
    while (cur.value != 0L) cur = cur.next
    var ans = 0L
    repeat(3) {
        repeat(1000) { cur = cur.next }
        ans += cur.value
    }
    println(ans)
}

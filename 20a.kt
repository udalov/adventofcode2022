class Node(val value: Int) {
    lateinit var prev: Node
    lateinit var next: Node
}

fun main() {
    val a = generateSequence(::readLine).map { Node(it.toInt()) }.toList()
    val n = a.size
    for (i in a.indices) {
        a[i].prev = a[(i - 1 + n) % n]
        a[i].next = a[(i + 1) % n]
    }
    for (v in a) {
        var k = (if (v.value >= 0) v.value else (n - 1) * 10000 + v.value) % (n - 1)
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

    var cur = a[0]
    while (cur.value != 0) cur = cur.next
    var ans = 0
    repeat(3) {
        repeat(1000) { cur = cur.next }
        ans += cur.value
    }
    println(ans)
}

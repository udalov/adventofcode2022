// Beware: it's not a real solution! Also, it runs in 6 minutes.

const val TIME = 32

operator fun Int.component1(): Int = this ushr 24
operator fun Int.component2(): Int = (this ushr 16) and 255
operator fun Int.component3(): Int = (this ushr 8) and 255
operator fun Int.component4(): Int = this and 255

fun Four(x1: Int, x2: Int, x3: Int, x4: Int): Int =
    (x1 shl 24) + (x2 shl 16) + (x3 shl 8) + x4

fun State(res: Int, rob: Int): Long =
    (res.toLong() shl 32) + rob

val Long.res: Int
    get() = (this ushr 32).toInt()

val Long.rob: Int
    get() = toInt()

fun <T : Comparable<T>> MutableList<T>.dedup() {
    sort()
    var i = 0
    var j = 0
    while (j < size) {
        this[i] = this[j]
        while (j < size && this[j] == this[i]) j++
        i++
    }
    while (size != i) removeAt(lastIndex)
}

fun main() {
    operator fun List<Int>.component6(): Int = this[5]
    operator fun List<Int>.component7(): Int = this[6]

    val regex = ("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. " +
        "Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.").toRegex()
    var ans = 1
    for (line in generateSequence(::readLine).take(3)) {
        val (index, a1, b1, c1, c2, d1, d3) = regex.matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
        val a = Array(TIME + 1) { mutableListOf<Long>() }
        a[0].add(State(Four(0, 0, 0, 0), Four(1, 0, 0, 0)))
        for (time in 0 until TIME) {
            val list = a[time]
            // print("time=$time size=${list.size}")
            list.dedup()
            // println(" dedup=${list.size}")
            for (state in list.sortedByDescending { it.res.component4() }.take(10_000_000)) {
                val (res1, res2, res3, res4) = state.res
                val (rob1, rob2, rob3, rob4) = state.rob
                val (new1, new2, new3, new4) = Four(res1 + rob1, res2 + rob2, res3 + rob3, res4 + rob4)
                if (a1 <= res1) {
                    a[time + 1].add(State(Four(new1 - a1, new2, new3, new4), Four(rob1 + 1, rob2, rob3, rob4)))
                }
                if (b1 <= res1) {
                    a[time + 1].add(State(Four(new1 - b1, new2, new3, new4), Four(rob1, rob2 + 1, rob3, rob4)))
                }
                if (c1 <= res1 && c2 <= res2) {
                    a[time + 1].add(State(Four(new1 - c1, new2 - c2, new3, new4), Four(rob1, rob2, rob3 + 1, rob4)))
                }
                if (d1 <= res1 && d3 <= res3) {
                    a[time + 1].add(State(Four(new1 - d1, new2, new3 - d3, new4), Four(rob1, rob2, rob3, rob4 + 1)))
                }
                a[time + 1].add(State(Four(new1, new2, new3, new4), state.rob))
            }
            a[time].clear()
        }
        val max = a[TIME].maxOf { it.res.component4() }
        // println("$index $max")
        ans *= max
    }
    println(ans)
}

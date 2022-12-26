const val TIME = 24

@JvmInline
value class Four private constructor(val value: Int) {
    operator fun component1(): Int = value ushr 24
    operator fun component2(): Int = (value ushr 16) and 255
    operator fun component3(): Int = (value ushr 8) and 255
    operator fun component4(): Int = value and 255

    companion object {
        operator fun invoke(x1: Int, x2: Int, x3: Int, x4: Int): Four =
            Four((x1 shl 24) + (x2 shl 16) + (x3 shl 8) + x4)
    }
}

data class State(val res: Four, val rob: Four)

fun main() {
    operator fun List<Int>.component6(): Int = this[5]
    operator fun List<Int>.component7(): Int = this[6]

    val regex = ("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. " +
        "Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.").toRegex()
    var ans = 0
    for (line in generateSequence(::readLine)) {
        val (index, a1, b1, c1, c2, d1, d3) = regex.matchEntire(line)!!.groupValues.mapNotNull(String::toIntOrNull)
        val a = Array(TIME + 1) { mutableSetOf<State>() }
        val maxGeode = Array(TIME + 1) { Int.MIN_VALUE }
        a[0].add(State(Four(0, 0, 0, 0), Four(1, 0, 0, 0)))
        for (time in 0 until TIME) {
            for (state in a[time]) {
                val (res1, res2, res3, res4) = state.res
                if (res4 < maxGeode[time]) continue
                val (rob1, rob2, rob3, rob4) = state.rob
                val (new1, new2, new3, new4) = Four(res1 + rob1, res2 + rob2, res3 + rob3, res4 + rob4)
                maxGeode[time + 1] = maxOf(maxGeode[time + 1], new4)
                a[time + 1].add(State(Four(new1, new2, new3, new4), state.rob))
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
            }
            // println("time=$time size=${a[time].size}")
        }
        val max = a[TIME].maxOf { it.res.component4() }
        // println("$index $max")
        ans += index * max
    }
    println(ans)
}

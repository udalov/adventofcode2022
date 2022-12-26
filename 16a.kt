const val MIN = Int.MIN_VALUE / 3
const val MAX = Int.MAX_VALUE / 3

fun main() {
    val index = mutableMapOf<String, Int>()
    val name = mutableListOf<String>()
    val rate = mutableMapOf<String, Int>()
    val graph = mutableMapOf<String, List<String>>()
    val regex = "Valve (.+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)".toRegex()
    for (line in generateSequence(::readLine)) {
        val (v, r, ws) = regex.matchEntire(line)!!.destructured
        if (r != "0" || v == "AA") {
            name.add(v)
            index[v] = index.size
            rate[v] = r.toInt()
        }
        graph[v] = ws.split(", ")
    }

    val dist = mutableMapOf<Pair<String, String>, Int>()
    for (v in graph.keys) dist[v to v] = 0
    for (v in graph.keys) for (w in graph[v]!!) dist[v to w] = 1
    for (k in graph.keys) for (i in graph.keys) for (j in graph.keys) {
        dist[i to j] = minOf(
            dist[i to j] ?: MAX,
            (dist[i to k] ?: MAX) + (dist[k to j] ?: MAX),
        )
    }

    val n = index.size
    val nn = 1 shl n
    val a = Array(31) { Array(n) { IntArray(nn) { MIN } } }
    val start = index["AA"]!!
    a[0][start][1 shl start] = 0
    var ans = MIN
    for (time in 0 until a.size) for (cur in 0 until n) for (mask in 0 until nn) {
        if (mask and (1 shl cur) == 0) continue
        if (a[time][cur][mask] < 0) continue
        for (next in 0 until n) {
            if (mask and (1 shl next) != 0) continue
            val d = dist[name[cur] to name[next]]!! + 1
            if (time + d >= a.size) continue
            val value = a[time][cur][mask] + (30 - time - d) * rate[name[next]]!!
            a[time + d][next][mask + (1 shl next)] = maxOf(a[time + d][next][mask + (1 shl next)], value)
            ans = maxOf(ans, value)
        }
    }
    println(ans)
}

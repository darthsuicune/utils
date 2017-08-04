class Permutations {
    fun calc(): List<Stats> {
        val exem = mutableListOf<Stats>()
        for (hp in 0..15) {
            for (atk in 0..15) {
                (0..15)
                        .filter { isExemplar(hp, atk, it) }
                        .mapTo(exem) { Stats(hp, atk, it) }
            }
        }
        return exem
    }

    private fun isExemplar(hp: Int, atk: Int, def: Int): Boolean {
        var ise = (((hp + atk + def).toDouble() / 45) * 100.0 > 81.0)
        if (ise) println("HP: $hp, Atk: $atk, Def: $def")
        return ise
    }
}

class Stats(val hp: Int, val atk:Int, val def:Int)
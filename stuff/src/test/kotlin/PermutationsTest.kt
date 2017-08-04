import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class PermutationsTest {
    @Test
    fun calc() {
            val size = Permutations().calc().size.toDouble()
            val total = Math.pow(15.0, 3.0)
            val percentChance = size/total * 100
            println("Chances are $size in $total, that is $percentChance%")
    }

}
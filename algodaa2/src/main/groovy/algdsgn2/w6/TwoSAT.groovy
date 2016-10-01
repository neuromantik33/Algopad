/*
 *  algopad.
 */

package algdsgn2.w6

import groovy.transform.CompileStatic
import groovy.transform.Immutable

import static algopad.common.misc.Bits.randomBitSet
import static algopad.common.misc.Bits.toBinaryString
import static algopad.common.misc.Ints.log2
import static algopad.common.misc.RandomOps.shuffle
import static java.lang.Math.max

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings('GroovyBreak')
@CompileStatic
class TwoSAT {

    final int numVars
    final Clause[] clauses
    final Random rnd

    TwoSAT(int numVars, Clause[] clauses, Random rnd) {
        this.numVars = numVars
        this.clauses = clauses
        this.rnd = rnd
    }

    /**
     * @param numVars the number of variables.
     * @param clauses the array of clauses.
     * @return {@code true} if the clauses are satisfiable, {@code false} otherwise.
     */
    static boolean isSatisfiable(final int numVars, final Clause[] clauses) {
        def maxTries = max(log2(numVars), 10)
        while (maxTries > 0) {
            def ts = new TwoSAT(numVars, clauses, new Random())
            def bits = randomBitSet(numVars, ts.rnd)
            if (ts.randomWalk(bits)) {
                return true
            }
            maxTries--
        }
        false
    }

    private boolean randomWalk(BitSet bits) {
        long maxSteps = 2L * numVars * numVars
        while (maxSteps > 0) {
            // println "remaining steps = $maxSteps, bits=${toBinaryString(bits, numVars)}"
            def clause = findUnsatisfiableClause(bits)
            if (clause == null) {
                println "satisfying bits : ${toBinaryString(bits, numVars)}"
                return true
            }
            int nextBit = rnd.nextBoolean() ? clause.v1 : clause.v2
            bits.flip nextBit
            maxSteps--
        }
        false
    }

    private Clause findUnsatisfiableClause(BitSet bits) {
        shuffle rnd, clauses
        clauses.find { !it.evaluate(bits) }
    }

    @Immutable
    static class Clause {

        int v1, v2
        boolean not1, not2

        boolean evaluate(BitSet bits) {
            def val1 = bits.get(v1)
            if (not1) { val1 = !val1 }
            def val2 = bits.get(v2)
            if (not2) { val2 = !val2 }
            val1 || val2
        }

        String toString() {
            def s1 = (not1 ? '¬' : '') + "x($v1)"
            def s2 = (not2 ? '¬' : '') + "x($v2)"
            "{ $s1 ∨ $s2 }"
        }
    }
}

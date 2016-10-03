/*
 *  algopad.
 */

package algdsgn2.w6

import algopad.common.ds.BitArray
import groovy.transform.CompileStatic
import groovy.transform.Immutable

import static algopad.common.misc.Bits.randomBitArray
import static algopad.common.misc.Ints.log2
import static java.lang.Thread.currentThread

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class TwoSAT {

    final int numVars
    final Clause[] clauses
    final Random rnd

    int current = 0

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
        def maxTries = log2(numVars)
        def numTries = maxTries
        while (numTries > 0) {
            def ts = new TwoSAT(numVars, clauses, new Random())
            def bits = randomBitArray(numVars, ts.rnd)
            if (ts.randomWalk(bits)) {
                return true
            }
            numTries -= 1
        }
        false
    }

    private boolean randomWalk(BitArray bits) {

        long maxSteps = 2L * numVars**2
        boolean satisfied = false

        long numSteps = 0
        while (numSteps < maxSteps) {
            def clause = findUnsatisfiableClause(bits)
            if (clause == null || currentThread().interrupted) {
                println "numSteps = $numSteps, satisfying bits :\n$bits"
                satisfied = true
                //noinspection GroovyBreak
                break
            }
            int nextBit = rnd.nextBoolean() ? clause.v1 : clause.v2
            bits.flip nextBit
            numSteps += 1
        }

        satisfied

    }

    private Clause findUnsatisfiableClause(BitArray bits) {
        int len = clauses.length
        if (current >= len) { current = 0 }
        for (; current < len; current++) {
            def clause = clauses[current]
            if (!clause.evaluate(bits)) {
                current += 1
                return clause
            }
        }
        clauses.find { !it.evaluate(bits) }
    }

    @Immutable
    static class Clause {

        int v1, v2
        boolean not1, not2

        boolean evaluate(BitArray bits) {
            def val1 = bits[v1]
            if (not1) { val1 = !val1 }
            def val2 = bits[v2]
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

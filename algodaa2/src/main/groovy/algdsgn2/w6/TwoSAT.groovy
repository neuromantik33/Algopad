/*
 *  algopad.
 */

package algdsgn2.w6

import groovy.transform.CompileStatic
import groovy.transform.Immutable

import static algopad.common.misc.Ints.log2
import static groovy.transform.TypeCheckingMode.SKIP
import static groovyx.gpars.GParsPool.withPool
import static java.lang.Math.abs
import static java.lang.Math.max

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings('GroovyBreak')
@CompileStatic
class TwoSAT {

    final Random rnd = new Random()

    final int numVars
    final Clause[] clauses

    TwoSAT(int numVars, Clause[] clauses) {
        this.numVars = numVars
        this.clauses = clauses
    }

    /**
     * TODO
     *
     * @param n
     * @param clauses
     * @return
     */
    @CompileStatic(SKIP)
    static boolean isSatisfiable(final int n, final Clause[] clauses) {
        def ts = new TwoSAT(n, clauses)
        def maxTries = max(log2(n), 10)
        boolean satisfied = withPool {
            (0..<maxTries).find {
                ts.randomWalk()
            }
        }
        satisfied
    }

    private boolean randomWalk() {
        def bits = new BigInteger(numVars, rnd)
        long maxSteps = 2L * numVars * numVars
        while (maxSteps > 0) {
            println "steps = $maxSteps, bits=$bits"
            def clause = clauses.find { !it.evaluate(bits) }
            if (clause == null) {
                println "satisfying bits for $clauses = ${bits.toString(2).padLeft(numVars, '0')}"
                return true
            }
            int nextBit = rnd.nextBoolean() ? clause.v1 : clause.v2
            bits = bits.flipBit(abs(nextBit) - 1)
            maxSteps -= 1
        }
        false
    }

    /**
     * @return the list of unsatisfied clauses for the given <i>bits</i>.
     */
    private Collection<Clause> areClausesSatisfied(final BigInteger bits) {
        clauses.findAll { !it.evaluate(bits) }
    }

    @Immutable
    static class Clause {

        int v1, v2

        boolean evaluate(BigInteger bits) {
            def val1 = v1 < 0 ? !bits.testBit(abs(v1) - 1) : bits.testBit(v1 - 1)
            def val2 = v2 < 0 ? !bits.testBit(abs(v2) - 1) : bits.testBit(v2 - 1)
            val1 || val2
        }

        String toString() {
            def s1 = v1 < 0 ? "¬x(${abs(v1) - 1})" : "x(${v1 - 1})"
            def s2 = v2 < 0 ? "¬x(${abs(v2) - 1})" : "x(${v2 - 1})"
            "{ $s1 ∨ $s2 }"
        }
    }
}

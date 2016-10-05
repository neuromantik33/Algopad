/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.algdsgn2.w6

import algopad.common.ds.BitArray
import groovy.transform.CompileStatic

import static algopad.common.misc.Bits.randomBitArray
import static algopad.common.misc.Ints.log2

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class TwoSAT {

    final int numVars
    final Clause[] clauses
    final Random rnd
    final BitArray bits

    int current = 0

    TwoSAT(int numVars, Clause[] clauses, Random rnd) {
        this.numVars = numVars
        this.clauses = clauses
        this.rnd = rnd
        this.bits = randomBitArray(numVars, rnd)
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
            if (ts.randomWalk()) {
                return true
            }
            numTries -= 1
        }
        false
    }

    private boolean randomWalk() {

        long maxSteps = 2L * numVars**2
        boolean satisfied = false

        long numSteps = 0
        while (numSteps < maxSteps) {
            def clause = findUnsatisfiableClause()
            if (clause == null) {
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

    private Clause findUnsatisfiableClause() {
        int len = clauses.length
        if (current >= len) { current = 0 }
        for (; current < len; current++) {
            def clause = clauses[current]
            if (!clause.evaluate(bits)) {
                current += 1
                return clause
            }
        }
        confirmUnsatisfied()
    }

    private Clause confirmUnsatisfied() {
        for (Clause clause : clauses) {
            if (!clause.evaluate(bits)) {
                return clause
            }
        }
        null
    }
}

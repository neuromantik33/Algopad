/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

import static algopad.common.misc.Counting.chooseK
import static java.lang.Integer.bitCount

/**
 * Some useful methods for working with integers.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Ints {

    /**
     * @return the distance between two integers where the number of bits at which
     * the corresponding bits are different. In another way, it measures the minimum number
     * of bit substitutions required to change one integer into the other.
     */
    static int hammingDistance(int x1, int x2) {
        bitCount x1 ^ x2
    }

    /**
     * Helper class for calculating bitwise integer <i>neighbors</i>
     * of a certain bitwise distance.
     */
    static class HammingCalculator {

        def bitmasks

        HammingCalculator(int distance, int numBits = 32) {
            this.bitmasks = chooseK(distance, 0..<numBits)
        }

        /**
         * @return the hamming neighbors for the integer <i>num</i>.
         */
        Set<Integer> neighborsFor(int num) {
            bitmasks.collect { List<Integer> offsets ->
                def val = num
                offsets.each {
                    val ^= 1 << it
                }
                val
            } as Set
        }
    }
}

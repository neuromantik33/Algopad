/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

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
}

/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

import static groovy.transform.TypeCheckingMode.SKIP

/**
 * Some useful methods for working with {@link BitSet}s.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Bits {

    /**
     * Generates a randomly generated non-negative {@link BitSet},
     * uniformly distributed over the range 0 to (2<sup><i>numBits</i></sup> - 1), inclusive.
     *
     * @param numBits maximum bit length of the new BitSet.
     * @param rnd source of randomness to be used in computing the new BitSet.
     */
    static BitSet randomBitSet(final int numBits, final Random rnd) {
        def bytes = randomBits(numBits, rnd)
        def bits = BitSet.valueOf(bytes)
        bits
    }

    /**
     * @see BigInteger#randomBits(int, java.util.Random)
     */
    @CompileStatic(SKIP)
    private static byte[] randomBits(int numBits, Random rnd) {
        assert numBits > 0, 'numBits must be non-negative'
        int numBytes = (int) (((long) numBits + 7) / 8) // avoid overflow
        byte[] randomBits = new byte[numBytes]
        // Generate random bytes and mask out any excess bits
        if (numBytes > 0) {
            rnd.nextBytes randomBits
            int excessBits = 8 * numBytes - numBits
            int mask = (1 << (8 - excessBits)) - 1
            randomBits[0] &= mask
        }
        randomBits
    }
}

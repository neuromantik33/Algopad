/*
 *  algopad.
 */

package algopad.common.misc

import algopad.common.ds.BitArray
import groovy.transform.CompileStatic

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
     * Generates a randomly generated non-negative {@link BitArray},
     * uniformly distributed over the range 0 to (2<sup><i>numBits</i></sup> - 1), inclusive.
     *
     * @param numBits maximum bit length of the new BitSet.
     * @param rnd source of randomness to be used in computing the new BitSet.
     */
    static BitArray randomBitArray(final int numBits, final Random rnd) {
        def bytes = randomBits(numBits, rnd)
        def bits = new BitArray(numBits, bytes)
        bits
    }

    /**
     * @see BigInteger#randomBits(int, java.util.Random)
     */
    private static byte[] randomBits(int numBits, Random rnd) {
        assert numBits > 0, 'numBits must be non-negative'
        int numBytes = (int) (((long) numBits + 7) / 8) // avoid overflow
        byte[] randomBits = new byte[numBytes]
        // Generate random bytes and mask out any excess bits
        if (numBytes > 0) {
            rnd.nextBytes randomBits
            int excessBits = 8 * numBytes - numBits
            int mask = (1 << (8 - excessBits)) - 1
            randomBits[0] = (randomBits[0] & mask).byteValue()
        }
        randomBits
    }

    /**
     * @return a string representation of the {@link BitSet} <i>bits</i>
     * argument as an unsigned integer in base&nbsp;2.
     */
    static String toBinaryString(final BitSet bits, final int numBits = 0) {
        toBinaryString bits.toByteArray(), numBits
    }

    /**
     * @return a string representation of the {@link BitArray} <i>bits</i>
     * argument as an unsigned integer in base&nbsp;2.
     */
    static String toBinaryString(final BitArray bits, final int numBits = 0) {
        toBinaryString bits.bytes, numBits
    }

    static String toBinaryString(final byte[] bytes, final int numBits = 0) {
        def sb = new StringBuilder()
        def toBinary = Integer.&toBinaryString
        for (byte b in bytes) {
            sb.append toBinary(b & 0xff)
        }
        sb.padLeft numBits, '0'
    }
}

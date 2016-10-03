/*
 *  algopad.
 */

package algopad.common.ds

import groovy.transform.CompileStatic

/**
 * An implementation of fixed-length bit array, similar to {@link BitSet},
 * but lighter weight and immutable in length.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class BitArray {

    static final int BITS_PER_UNIT = 8

    final int length
    final byte[] bytes

    BitArray(final int length) {
        this(length, new byte[(length + BITS_PER_UNIT - 1).intdiv(BITS_PER_UNIT)])
    }

    BitArray(final int length, final byte[] bytes) {
        assert length >= 0
        this.length = length
        this.bytes = bytes
    }

    /**
     * @return the value of the bit with the specified <i>index</i>.
     */
    boolean getAt(final int index) {
        assert index >= 0 && index < length
        (bytes[subscript(index)] & position(index)) != 0
    }

    /**
     * Sets the bit at the specified <i>index</i> to {@code true}.
     */
    boolean putAt(final int index, final boolean value) {
        assert index >= 0 && index < length
        def idx = subscript(index)
        def bit = position(index)
        if (value) {
            bytes[idx] = (bytes[idx] | bit).byteValue()
        } else {
            bytes[idx] = (bytes[idx] & ~bit).byteValue()
        }
        value
    }

    /**
     * Sets the bit at the specified <i>index</i> to the complement of its current value.
     */
    boolean flip(final int index) {
        assert index >= 0 && index < length
        def idx = subscript(index)
        def bit = position(index)
        bytes[idx] = (bytes[idx] ^ bit).byteValue()
    }

    /**
     * @return the number of bits set to {@code true} in this {@code BitArray}
     */
    public int getCardinality() {
        int sum = 0
        for (byte b in bytes) {
            sum += Integer.bitCount(b & 0xff)
        }
        return sum;
    }

    private static int subscript(final int idx) {
        idx >>> 3 // equivalent to idx / BITS_PER_UNIT
    }

    private static int position(final int idx) {
        // bits big-endian in each unit
        1 << BITS_PER_UNIT - 1 - idx % BITS_PER_UNIT;
    }
}

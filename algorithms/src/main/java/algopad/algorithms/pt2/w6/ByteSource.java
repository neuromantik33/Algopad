/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
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

package algopad.algorithms.pt2.w6;

/**
 * Interface for reading binary data.
 *
 * @author Nicolas Estrada.
 */
public interface ByteSource {

    /**
     * Returns true if this binary input stream is empty.
     *
     * @return <tt>true</tt> if this binary input stream is empty;
     * <tt>false</tt> otherwise
     */
    boolean isEmpty();

    /**
     * Reads the next 8 bits from this binary input stream
     * and return as an 8-bit char.
     *
     * @return the next 8 bits of data from this binary input
     * stream as a <tt>char</tt>
     *
     * @throws RuntimeException if there are fewer than 8 bits
     * available
     */
    char readChar();

    /**
     * Reads the next 32 bits from this binary input stream and
     * return as a 32-bit int.
     *
     * @return the next 32 bits of data from this binary input
     * stream as a <tt>int</tt>
     *
     * @throws RuntimeException if there are fewer than 32 bits
     * available
     */
    int readInt();

    /**
     * Reads the remaining bytes of data from this binary input stream
     * and return as a string.
     *
     * @return the remaining bytes of data from this binary input stream
     * as a <tt>String</tt>
     *
     * @throws RuntimeException if this binary input stream is empty or
     * if the number of bits available is not a multiple of 8 (byte-aligned)
     */
    String readString();

    /**
     * Close this input stream and release any associated system resources.
     */
    void close();

}

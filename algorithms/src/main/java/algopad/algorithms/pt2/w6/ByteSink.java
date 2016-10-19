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
 * Interface for writing binary data.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("OverloadedMethodsWithSameNumberOfParameters")
public interface ByteSink {

    /**
     * Writes the 8-bit char to the binary output stream.
     *
     * @param x the <tt>char</tt> to write
     *
     * @throws IllegalArgumentException unless <tt>x</tt> is between 0 and 255.
     */
    void write(char x);

    /**
     * Writes the 32-bit int to the binary output stream.
     *
     * @param x the <tt>int</tt> to write
     */
    void write(int x);

    /**
     * Flushes the binary output stream, padding 0s if number of bits written
     * so far if not a multiple of 8.
     */
    void flush();

    /**
     * Flush and close standard output. Once standard output is closed,
     * you can no longer write bits to it.
     */
    void close();

}

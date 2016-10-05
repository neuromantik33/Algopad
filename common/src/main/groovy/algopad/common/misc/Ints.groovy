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

package algopad.common.misc

import groovy.transform.CompileStatic

import static algopad.common.misc.Counting.chooseK
import static java.lang.Integer.bitCount
import static java.lang.Integer.numberOfLeadingZeros

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
     * @return the logarithm (base <i>2</i>) of the {@code int} <i>val</i>.
     */
    static int log2(int val) {
        assert val > 0
        return 31 - numberOfLeadingZeros(val);
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

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

import algopad.common.misc.Ints.HammingCalculator
import spock.lang.Specification
import spock.lang.Unroll

import static algopad.common.misc.Ints.hammingDistance

class IntsSpec extends Specification {

    @Unroll
    def 'it should calculate the hamming distance #dist for #x and #y'() {

        expect:
        hammingDistance(x, y) == dist

        where:
        x          | y          | dist
        0b1011101  | 0b1001001  | 2
        0b10101010 | 0b01010101 | 8
        331882123  | 996630289  | 14

    }

    @Unroll
    def 'it should return all possible combinations of ints of #numBits bits with hamming distance #distance for #n'() {

        given:
        def hamming = new HammingCalculator(distance, numBits)

        expect:
        hamming.neighborsFor(n) == neighbors as Set
        neighbors.each {
            assert hammingDistance(n, it) == distance
        }

        where:
        n      | distance | numBits | neighbors
        0b1    | 1        | 1       | [0]
        0b11   | 2        | 2       | [0b00]
        0b11   | 1        | 2       | [0b10, 0b01]
        0b111  | 3        | 3       | [0b000]
        0b111  | 2        | 3       | [0b100, 0b010, 0b001]
        0b111  | 1        | 3       | [0b110, 0b101, 0b011]
        0b1111 | 4        | 4       | [0b0000]
        0b1111 | 3        | 4       | [0b1000, 0b0100, 0b0010, 0b0001]
        0b1111 | 2        | 4       | [0b1100, 0b1010, 0b0110, 0b1001, 0b0101, 0b0011]

    }
}

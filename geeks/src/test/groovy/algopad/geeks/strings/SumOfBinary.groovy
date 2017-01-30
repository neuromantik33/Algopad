/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

package algopad.geeks.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Math.max

@See('http://www.geeksforgeeks.org/add-two-bit-strings')
@SuppressWarnings('GroovyAssignmentToMethodParameter')
class SumOfBinary extends Specification {

    /*@Subject // My working but somewhat awkward solution
    def addBinary = { String s1, String s2 ->

        int n = max(s1.length(), s2.length())
        s1 = s1.padLeft(n, '0')
        s2 = s2.padLeft(n, '0')

        def result = new StringBuilder()
        int carry = 0
        (n - 1..0).each { int i ->
            int i1 = s1[i] as int
            int i2 = s2[i] as int
            if (i1 + i2 == 0) {
                if (carry > 0) {
                    result.append 1
                    carry -= 1
                } else {
                    result.append 0
                }
            } else if (i1 + i2 == 1) {
                result.append carry > 0 ? 0 : 1
            } else {
                if (carry > 0) {
                    result.append 1
                } else {
                    result.append 0
                    carry = 1
                }
            }
        }

        if (carry > 0) {
            result.append 1
        }
        result.reverse()
              .toString()
    }*/

    @Subject
    def addBinary = { String s1, String s2 ->

        int n = max(s1.length(), s2.length())
        s1 = s1.padLeft(n, '0')
        s2 = s2.padLeft(n, '0')

        def result = new StringBuilder()
        int carry = 0
        (n - 1..0).each { int i ->
            int i1 = s1[i] as int
            int i2 = s2[i] as int

            /*
             * Perform bit addition
             * .Boolean expression for adding 3 bits a, b, c
             * .Sum = a XOR b XOR c
             * .Carry = (a AND b) OR ( b AND c ) OR ( c AND a )
             */
            int sum = i1 ^ i2 ^ carry
            result.append sum

            carry = (i1 & i2) | (i2 & carry) | (i1 & carry)

        }

        if (carry > 0) {
            result.append 1
        }
        result.reverse()
              .toString()
    }

    @Unroll
    def 'given 2 binary strings #a and #b, it should return their sum #sum also as binary'() {

        expect:
        addBinary(a, b) == sum

        where:
        a                        | b                           | sum
        '100'                    | '11'                        | '111'
        '1100011'                | '10'                        | '1100101'
        '11111'                  | '11'                        | '100010'
        '11111111'               | '11111111'                  | '111111110'
        '1010110111001101101000' | '1000011011000000111100110' | '1001110001111010101001110'

    }
}

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

package algopad.geeks.math

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Integer.bitCount

@See('http://www.geeksforgeeks.org/write-one-line-c-function-to-find-whether-a-no-is-power-of-two')
class PowerOf2 extends Specification {

    @Subject
    def isPowerOf2 = { int n ->
        n != 1 && bitCount(n) == 1
    }

    @Unroll
    def 'given a positive integer in #n, it should check if it is a power of 2'() {

        expect:
        n.every { isPowerOf2(it) == powerOf2 }

        where:
        n                          | powerOf2
        [0, 1, 3, 5, 6, 7, 9]      | false
        [2, 4, 8, 16, 32, 64, 128] | true

    }
}

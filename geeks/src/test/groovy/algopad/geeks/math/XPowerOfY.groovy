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

import static java.lang.Math.sqrt

@See('http://www.geeksforgeeks.org/check-if-a-number-can-be-expressed-as-xy-x-raised-to-power-y')
class XPowerOfY extends Specification {

    @Subject
    def canBeExponent = { int n ->
        if (n == 1) { return true }
        def limit = sqrt(n) as int
        for (int x = 2; x <= limit; x++) {
            def y = 2
            def pow = x * x
            while (pow <= n && pow > 0) {
                if (pow == n) {
                    return true
                }
                y += 1
                pow *= x
            }
        }
        false
    }

    @Unroll
    def 'given a positive integer #n, it should detect if it can be expressed as x^y (y > 1 and x > 0) -> #x^#y'() {

        expect:
        canBeExponent(n) == exp

        where:
        n  | x | y | exp
        1  | 1 | 2 | true
        8  | 2 | 3 | true
        49 | 7 | 2 | true
        64 | 2 | 6 | true
        48 | _ | _ | false

    }
}

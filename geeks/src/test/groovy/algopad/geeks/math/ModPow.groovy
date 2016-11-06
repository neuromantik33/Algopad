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

@See('http://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic')
class ModPow extends Specification {

    @Subject
    def modPow = { int x, int y, int p ->
        def result = 1
        def exp = x
        while (y > 0) {
            if (y % 2 == 0) {
                y = y >> 1
                exp *= exp
            } else {
                y -= 1
                result = (result * exp) % p
            }
        }
        result
    }

    @Unroll
    def 'given three numbers #x, #y and #p, compute (#x^#y) % #p.'() {

        expect:
        modPow(x, y, p) == mp

        where:
        x  | y | p  | mp
        10 | 0 | 2  | 1
        2  | 3 | 5  | 3
        2  | 5 | 13 | 6

    }
}

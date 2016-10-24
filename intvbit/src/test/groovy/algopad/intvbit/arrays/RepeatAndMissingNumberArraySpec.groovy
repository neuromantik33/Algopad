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

package algopad.intvbit.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.interviewbit.com/problems/repeat-and-missing-number-array')
class RepeatAndMissingNumberArraySpec extends Specification {

    /* Alternate solution (mine)
    @Subject
    def repeatedNumber = { List a ->
        def n = a.size()
        def marked = new boolean[n]
        def dup = -1
        n.times {
            final idx = a[it] - 1
            if (marked[idx]) {
                dup = a[it]
            } else {
                marked[idx] = true
            }
        }
        def missing = marked.findIndexOf { !it } + 1
        [dup, missing]
    }*/

    @Subject
    @SuppressWarnings('GroovyLocalVariableNamingConvention')
    def repeatedNumber = { List a ->

        def pow2 = { int x -> (long) x * x }
        def n = a.size()

        // A - B
        def sum = 0g
        // A^2 - B^2 = (A - B)(A + B)
        def sumSquared = 0g

        1.upto(n) {
            sum = sum + a[it - 1] - it
            sumSquared = sumSquared + pow2(a[it - 1]) - pow2(it)
        }

        // squareSum / sum = A + B
        sumSquared /= sum

        // Now we have A + B and A - B -> A = ((A - B) + (A + B)) / 2
        int A = ((sum + sumSquared) / 2) as int
        // B = A + B - A
        int B = sumSquared - A

        [A, B]

    }

    @Unroll
    def '''You are given a read only array of #n integers from 1 to n. Each integer appears exactly once
           except A which appears twice and B which is missing. Return #A and #B.'''() {

        expect:
        repeatedNumber(input) == [A, B]

        where:
        input              | A | B
        [3, 1, 2, 5, 3]    | 3 | 4
        [4, 3, 6, 2, 1, 1] | 1 | 5
        [3, 1, 3]          | 3 | 2

        n = input.size()

    }
}

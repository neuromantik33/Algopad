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

package algopad.geeks.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/find-a-repeating-and-a-missing-number')
class RepeatAndMissingNumber extends Specification {

    /* Alternate solution (mine)
    @Subject
    def repeatedAndMissingNumber = { List a ->
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
    def repeatedAndMissingNumber = { List a ->

        def pow2 = { int x -> (long) x * x }
        def n = a.size()

        // A - B
        long sum = 0L
        // A^2 - B^2 = (A - B)(A + B)
        long sumSquared = 0L

        1.upto(n) {
            sum = sum + a[it - 1] - it
            sumSquared = sumSquared + pow2(a[it - 1]) - pow2(it)
        }

        // squareSum / sum = A + B
        sumSquared /= sum

        // Now we have A + B and A - B -> A = ((A - B) + (A + B)) / 2
        int A = (sum + sumSquared) >> 1
        // B = A + B - A
        int B = sumSquared - A

        [A, B]

    }

    @Unroll
    def '''You are given a read only array of #n integers from 1 to n. Each integer appears exactly once
           except A which appears twice and B which is missing. Return #A and #B.'''() {

        expect:
        repeatedAndMissingNumber(input) == [A, B]

        where:
        input              | A | B
        [3, 1, 2, 5, 3]    | 3 | 4
        [4, 3, 6, 2, 1, 1] | 1 | 5
        [3, 1, 3]          | 3 | 2

        n = input.size()

    }
}

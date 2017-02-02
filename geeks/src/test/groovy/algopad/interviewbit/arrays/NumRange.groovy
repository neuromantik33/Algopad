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

package algopad.interviewbit.arrays

import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/numrange')
@Narrative('''
For each left index a, let b be the largest possible right index such that B[a] + B[a+1] + ... + B[b] <= P 
(unless B_a > P, in which case there's no such index and we can let b = a - 1 for convenience). 
Note that there are then exactly b - a + 1 valid contiguous sequences with a as their left index 
(namely, the sequences a..a, a..(a+1), ..., a..b).

For each of the N potential left indices a, the corresponding maximum right index b can be found in
O(log(N)) time using binary search, assuming that the prefix sums of the B array have been precomputed.
Alternatively, we can observe that when a is increased by 1, its corresponding b index is always
increased by 0 or more (never decreased). This allows us to achieve a time complexity of O(N) by employing
the "sliding window" technique, iterating over all values of a from 1 to N while also
shifting b forward from 1 to N as appropriate.
''')
class NumRange extends Specification {

    // TODO Can do better than O(n^2)!!
    @Subject
    def numRange = { List<Integer> a, IntRange range ->
        int num = 0
        int n = a.size()
        def isSumWithinRange = { int sum ->
            if (sum > range.to) { return false }
            if (sum >= range.from) { num++ }
            true
        }
        (0..<n).each { int i ->
            int sum = a[i]
            if (!isSumWithinRange(sum)) {
                return
            }
            for (int j = i + 1; j < n; j++) {
                sum += a[j]
                if (!isSumWithinRange(sum)) {
                    return
                }
            }
        }
        num
    }

    @Unroll
    def '''given an array of non negative integers #a, and a range #range, it should find the number
           of continuous sub-sequences #num in the array which have sum S in the range.
           Continuous sub-sequence is defined as all the numbers A[i], A[i + 1], .... A[j]
           where 0 <= i <= j < size(A)'''() {

        expect:
        numRange(a, range) == num

        where:
        a                                                                                    | range   | num
        [10, 5, 1, 0, 2]                                                                     | 6..8    | 3
        [80, 34, 71, 40, 62, 30, 93, 11, 22, 59, 80, 61, 91, 94, 77, 27, 78, 72, 45, 53, 37] | 34..612 | 154

    }
}

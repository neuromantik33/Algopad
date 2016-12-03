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

package algopad.geeks.pointers

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Narrative('''
The idea is to use sliding window for the given array.
Let us use a window covering from index lo to index hi.
Let the number of zeros inside the window be numZeros.
We maintain the window with at most m zeros inside.

The main steps are:
– While numZeros is no more than m: expand the window to the right (hi++) and update the count numZeros.
– While numZeros exceeds m, shrink the window from left (lo++), update numZeros;
– Update the widest window along the way. The positions of output zeros are inside the best window.
''')
class MaxContinuousOnesAfterKFlips extends Specification {

    /* @Subject My quadratic solution :(
    def flipKBits = { List a, int m ->
        int n = a.size()
        if (m >= n) { return 0..<n }

        int len = 0, start = 0
        0.upto(n - 1) { i ->
            int numBits = m
            if (!a[i]) {
                numBits -= 1
                if (numBits < 0) {
                    return
                }
            }
            int j = i + 1
            for (; j < n; j++) {
                if (!a[j]) {
                    if (numBits > 0) {
                        numBits -= 1
                    } else {
                        break
                    }
                }
            }
            if (j - i > len) {
                len = j - i
                start = i
            }
        }
        start..(start + len - 1)
    }*/

    @Subject
    def flipKBits = { List a, int m ->
        int n = a.size()
        int lo = 0, hi = 0
        int start = 0, len = 0
        int numZeros = 0

        while (hi < n) {
            if (numZeros <= m) {
                if (!a[hi]) {
                    numZeros++
                }
                hi++
            }
            if (numZeros > m) {
                if (!a[lo]) {
                    numZeros--
                }
                lo++
            }
            if (hi - lo > len) {
                len = hi - lo
                start = lo
            }
        }
        start..(start + len - 1)
    }

    @Unroll
    def '''given a binary array #a and an integer #m, find the position of zeroes flipping
           which creates maximum number of consecutive 1s in array. Then return the indices #indices of
           maximum continuous series of 1s in order.
           If there are multiple possible solutions, return the sequence which has the
           minimum start index.'''() {

        expect:
        flipKBits(a, m) == indices

        where:
        a                                 | m | indices
        [0, 1, 1, 1]                      | 0 | 1..3
        [0, 0]                            | 1 | 0..0
        [0, 0, 0, 1]                      | 4 | 0..3
        [1, 1, 0, 1, 1, 0, 0, 1, 1, 1]    | 1 | 0..4
        [1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1] | 2 | 3..10

    }
}

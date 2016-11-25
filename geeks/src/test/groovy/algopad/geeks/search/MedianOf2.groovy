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

package algopad.geeks.search

import algopad.common.misc.ListOps
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/median-of-two-sorted-arrays-of-different-sizes')
class MedianOf2 extends Specification {

    @Subject
    def medianOf2 = { List<Integer> a, List<Integer> b ->

        def m = a.size()
        def n = b.size()
        assert m <= n

        def max = { i, j ->
            if (i < 0) { return b[j] }
            if (j < 0) { return a[i] }
            Math.max(a[i], b[j])
        }

        def min = { i, j ->
            if (i == m) { return b[j] }
            if (j == n) { return a[i] }
            Math.min(a[i], b[j])
        }

        int iMin = 0, iMax = m
        while (iMin <= iMax) {
            int i = (iMin + iMax) >> 1
            int j = ((m + n + 1) >> 1) - i
            if (i < m && j > 0 && b[j - 1] > a[i]) {
                iMin = i + 1
            } else if (j < n && i > 0 && a[i - 1] > b[j]) {
                iMax = i - 1
            } else {
                return (m + n) % 2 == 1 ? max(i - 1, j - 1)
                                        : (max(i - 1, j - 1) + min(i, j)) / 2.0
            }
        }

        assert false // Impossible!

    }

    //    @Subject Online solution : which is better?!
    //    def medianOf2 = { List<Integer> a, List<Integer> b ->
    //
    //        def findKth = { int i, int j, int k ->
    //            def m = a.size()
    //            def n = b.size()
    //            if (i >= m) { return b[j + k - 1] }
    //            if (j >= n) { return a[i + k - 1] }
    //            if (k == 1) { return min(a[i], b[j]) }
    //
    //            int mid = k >> 1
    //            int aMid = i + mid - 1 < m ? a[i + mid - 1] : MAX_VALUE
    //            int bMid = j + mid - 1 < n ? b[j + mid - 1] : MAX_VALUE
    //            aMid < bMid ? call(i + mid, j, k - mid) // Right
    //                        : call(i, j + mid, k - mid) // Left
    //        }
    //
    //        def len = a.size() + b.size()
    //        def mid = len >> 1
    //        len % 2 == 1 ? findKth(0, 0, mid + 1) // Odd
    //                     : (findKth(0, 0, mid) + findKth(0, 0, mid + 1)) / 2 // Even
    //    }

    @Unroll
    def 'given 2 sorted arrays #a and #b, it should return the median of the merging of the 2 arrays.'() {

        given:
        use(ListOps) {
            assert a.isSorted()
            assert b.isSorted()
        }

        expect:
        medianOf2(a, b) == median

        where:
        a                   | b                                    | median
        []                  | [0, 23]                              | 11.5
        []                  | [5, 6, 7, 8, 9]                      | 7
        [2, 3]              | [1, 4, 5]                            | 3
        [900]               | [5, 8, 10, 20]                       | 10
        [-2]                | [-43, -25, -18, -15, -10, 9, 39, 40] | -10
        [-44]               | [-50, -42, -38, 1, 4, 9, 16, 33, 47] | 2.5
        [16, 19]            | [-46, -15, -9, -7, -2, 24, 40]       | -2
        [25, 44]            | [-24, -11, -7, 4, 21, 26]            | 12.5
        [1, 2, 3, 6]        | [4, 6, 8, 10]                        | 5
        [1, 12, 15, 26, 38] | [2, 13, 17, 30, 45]                  | 16
        [-50, -21, -10]     | [-50, -41, -40, -19, 5, 21, 28]      | -20
        [35]                | [1, 26, 35, 49]                      | 35
        [-31, -11]          | [-3, -2, 1, 15]                      | -2.5

    }
}

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

package algopad.geeks.search

import algopad.common.BinarySearch
import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array')
class RotatedSortedSearch extends Specification {

    def search = new BinarySearch()

    def isValuePresent = { List a, int value ->

        int n = a.size()
        if (n == 0) { return -1 }
        if (a[0] == value) { return 0 }

        // Very difficult, not my idea, did a linear search for the pivot :(
        def findPivot = {
            int lo = 0, hi = n - 1
            while (lo <= hi) {
                int mid = (lo + hi) >> 1
                if (a[mid] > a[mid + 1]) {
                    return mid
                } else if (a[mid] < a[mid - 1]) {
                    return mid - 1
                } else if (a[lo] >= a[mid]) {
                    hi = mid - 1
                } else {
                    lo = mid + 1
                }
            }
            0
        }

        int pivot = findPivot()
        int offset = 0
        List space
        if (pivot > 0) {
            if (value >= a[0] && value <= a[pivot]) {
                space = a[0..pivot]
            } else if (value >= a[pivot + 1] && value <= a[-1]) {
                offset = pivot + 1
                space = a[pivot + 1..-1]
            } else {
                return -1
            }
        } else {
            space = a
        }

        int idx = search(space, value)
        idx >= 0 ? idx + offset : idx

    }

    @Unroll
    def 'it should return #index if the #value is present in the rotated sorted array #a'() {

        expect:
        isValuePresent(a, value) == index

        where:
        a                              | value | index
        []                             | 1     | -1
        [1]                            | 1     | 0
        [5, 6, 7, 8, 9, 10, 1, 2, 3]   | 3     | 8
        [5, 6, 7, 8, 9, 10, 1, 2, 3]   | 30    | -1
        [30, 40, 50, 10, 20]           | 10    | 3
        (50..100) + (1..49)            | 25    | 75
        (50..100) + (1..45) + (47..49) | 46    | -1
        [180, 181, 182, 183, 184, 187, 188, 189, 191, 192, 193, 194, 195,
         196, 201, 202, 203, 204, 3, 4, 5, 6, 7, 8, 9, 10, 14, 16, 17, 18,
         19, 23, 26, 27, 28, 29, 32, 33, 36, 37, 38, 39, 41, 42, 43, 45,
         48, 51, 52, 53, 54, 56, 62, 63, 64, 67, 69, 72, 73, 75, 77, 78,
         79, 83, 85, 87, 90, 91, 92, 93, 96, 98, 99, 101, 102, 104, 105,
         106, 107, 108, 109, 111, 113, 115, 116, 118, 119, 120, 122, 123,
         124, 126, 127, 129, 130, 135, 137, 138, 139, 143, 144, 145, 147,
         149, 152, 155, 156, 160, 162, 163, 164, 166, 168, 169, 170, 171,
         172, 173, 174, 175, 176, 177] | 42    | 43

    }
}

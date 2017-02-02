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

import algopad.common.misc.RandomOps
import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.System.nanoTime
import static java.util.concurrent.TimeUnit.NANOSECONDS

@See('http://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array')
class RotatedSortedSearch extends Specification {

    def isValuePresent = { List<Integer> a, int value ->

        int n = a.size()
        if (n == 0) { return -1 }
        if (a[0] == value) { return 0 }

        int lo = 0, hi = n - 1
        while (lo <= hi) {
            int left = a[lo]
            int right = a[hi]
            int mid = (lo + hi) >> 1
            int guess = a[mid]
            if (guess == value) {
                return mid
            }
            // If left half sorted?
            if (left < guess) {
                if (left <= value && value < guess) {
                    hi = mid - 1
                } else {
                    lo = mid + 1
                }
            }
            // Right half is sorted then
            else if (value > guess && value <= right) {
                lo = mid + 1
            } else {
                hi = mid - 1
            }
        }
        -1
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
        [101, 103, 106, 109, 158, 164, 182, 187, 202, 205, 2, 3, 32, 57,
         69, 74, 81, 99, 100]          | 202   | 8
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

    def 'it should return the correct index for a very large input of integers'() {

        def n = 100000
        def rnd = new Random()

        given: 'Generate some random data'
        def (List a, value) = use(RandomOps) {
            def ints = rnd.nextInts(n, Short.MAX_VALUE)
            def val = ints[rnd.nextInt(n)]
            [ints as List, val]
        }

        and: 'Sort the array of integers and rotate'
        int pivot = rnd.nextInt(n)
        a.sort()
        a = a[pivot..-1] + a[0..<pivot]

        when: 'Search using linear scan and binary search'
        def expected = benchmark("linear scan") {
            a.findIndexValues { it == value }
             .collect { it as int } // For some weird reason indexes are longs
        }
        int actual = benchmark("binary search") { isValuePresent a, value }

        then:
        actual in expected

    }

    def benchmark = { comment, Closure cls ->
        long start = nanoTime()
        def value = cls()
        println "Time spent for $comment = ${NANOSECONDS.toMillis(nanoTime() - start)}ms"
        value
    }
}

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

package algopad.geeks.hash

import spock.lang.See
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Integer.MIN_VALUE

@See('http://www.geeksforgeeks.org/longest-consecutive-subsequence')
class LongestConsecutiveSequence extends Specification {

    @Shared
    def rnd = new Random()

    @Subject
    def longestSequence = { List<Integer> list ->
        def set = list as Set
        int max = MIN_VALUE
        list.each { int i ->
            int prev = i - 1
            if (prev in set) {
                return
            }
            int cnt = 1, j = i + 1
            while (j in set) {
                cnt++
                j++
            }
            max = Math.max(max, cnt)
        }
        max
    }

    // n log n version for verification purposes
    def verify = { List<Integer> list ->
        int n = list.size()
        def sorted = (list as Set).sort(false)
        int max = MIN_VALUE, cur = 1
        for (int i = 1; i < n; i++) {
            if (sorted[i] == sorted[i - 1] + 1) {
                cur++
            } else {
                max = Math.max(max, cur)
                cur = 1
            }
        }
        max = Math.max(max, cur)
        max
    }

    @Unroll
    def 'given an array of #n integers, it should find the length of the longest consecutive sequence.'() {

        expect:
        longestSequence(a) == verify(a)

        where:
        a << [
          [100, 4, 200, 1, 3, 2, 2],
          [24, 35, 30, 40, 4, 31, 47, 21, 15, 12, 28, 19, 32, 14, 13, 34, 6, 38, 1, 10, 41, 22, 0, 43, 39, 48, 45, 8,
           11, 3, 9, 42, 37, 36, 49, 7, 44, 33, 18, 16, 20, 17, 23, 46, 26, 27, 29, 5, 2, 25],
          [50, 51, 15, 101, 23, 66, 69, 24, 75, 40, 30, 10, 61, 73, 95, 119, 106, 60, 26, 111, 54, 79, 18, 28, 72, 110,
           37, 63, 5, 102, 53, 42, 49, -4, -2, 64, 93, 117, 103, 115, -5, 87, 47, 12, 48, 1, 9, 91, 85, -3, 68, 76, 59,
           38, 35, 45, 0, 78, 62, 70, 46, 90, 52, 3, 83, 43, 11, 89, 21, 80, 77, 33, 17, 92, 113],
          [97, 86, 67, 19, 107, 9, 8, 49, 23, 46, -4, 22, 72, 4, 57, 111, 20, 52, 99, 2, 113, 81, 7, 5, 21, 0, 47, 54,
           76, 117, -2, 102, 34, 12, 103, 69, 36, 51, 105, -3, 33, 91, 37, 11, 48, 106, 109, 45, 58, 77, 104, 60, 75,
           90, 3, 62, 16, 119, 85, 63, 87, 43, 74, 13, 95, 94, 56, 28, 55, 66, 92, 79, 27, 42, 70],
          randomInts(1000)
        ]

        n = a.size()

    }

    private List randomInts(int size) {
        def list = []
        size.times { list << rnd.nextInt() }
        list
    }
}

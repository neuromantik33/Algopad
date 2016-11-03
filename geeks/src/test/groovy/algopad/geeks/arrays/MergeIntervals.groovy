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

package algopad.geeks.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Math.max

@See('http://www.geeksforgeeks.org/merging-intervals')
class MergeIntervals extends Specification {

    @Subject
    def mergeIntervals = { List<List> intervals ->
        intervals.sort { l1, l2 -> l1[0] <=> l2[0] }
        def merged = []
        def n = intervals.size()
        def interval = intervals[0]
        1.upto(n - 1) { i ->
            def next = intervals[i]
            if (next[0] <= interval[1]) {
                interval[1] = max(interval[1], next[1])
            } else {
                merged << interval
                interval = next
            }
        }
        merged << interval
        merged
    }

    @Unroll
    def '''Given a set of time intervals in any order, it should merge all overlapping intervals
           into one and output the result which should have only mutually exclusive intervals'''() {

        expect:
        mergeIntervals(intervals) == merged

        where:
        intervals                        | merged
        [[1, 3], [2, 4], [5, 7], [6, 8]] | [[1, 4], [5, 8]]
        [[6, 8], [1, 9], [2, 4], [4, 7]] | [[1, 9]]

    }
}

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

@See('http://www.geeksforgeeks.org/largest-sum-contiguous-subarray')
class LargestContiguousSum extends Specification {

    @Subject
    def largestSum = { List a ->
        if (a.empty) { return 0 }
        def sum = a[0]
        def curr = a[0]
        a.tail().each { i ->
            curr = max(i, curr + i)
            sum = max(sum, curr)
        }
        sum
    }

    @Unroll
    def 'it should find the sum of contiguous subarray within #a which has the largest sum.'() {

        expect:
        largestSum(a) == sum

        where:
        a                             | sum
        []                            | 0
        [-2, -1]                      | -1
        [1, 2, 3, -1]                 | 6
        [-2, -3, 4, -1, -2, 1, 5, -3] | 7
        [20, -3, 4, -1, -2, 1, 5, -3] | 24

    }
}

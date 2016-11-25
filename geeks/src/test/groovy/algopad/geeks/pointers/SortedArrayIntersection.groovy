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

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/union-and-intersection-of-two-sorted-arrays-2')
class SortedArrayIntersection extends Specification {

    @Subject
    def intersection = { List a, List b ->
        def result = []
        int i = 0, j = 0
        while (i < a.size() && j < b.size()) {
            if (a[i] < b[j]) {
                i++
            } else if (a[i] > b[j]) {
                j++
            } else {
                result << a[i]
                i++
                j++
            }
        }
        result
    }

    @Unroll
    def 'given 2 sorted arrays #a and #b, it should calculate the intersection #intrsct'() {

        expect:
        intersection(a, b) == intrsct

        where:
        a                     | b            | intrsct
        [1, 3, 4, 5, 7]       | [2, 3, 5, 6] | [3, 5]
        [1, 2, 3, 3, 4, 5, 6] | [3, 3, 5]    | [3, 3, 5]
        [1, 2, 3, 3, 4, 5, 6] | [3, 5]       | [3, 5]

    }
}

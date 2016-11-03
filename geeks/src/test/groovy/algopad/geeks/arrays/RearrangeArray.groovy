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

@See('http://www.geeksforgeeks.org/rearrange-given-array-place')
class RearrangeArray extends Specification {

    @Subject
    def rearrange = { List<Integer> list ->
        def n = list.size()
        n.times { i ->
            // Trying to put 2 numbers into one using following property
            // A = B + C * N if ( B, C < N ) => A % N = B , A / N = C
            int val = list[i]
            val += (list[val] % n) * n
            list[i] = val
        }
        n.times { i ->
            list[i] = list[i].intdiv(n)
        }
    }

    @Unroll
    def '''Given an array #arr of size #n where every element is in range from 0 to #max.
           Rearrange the given array so that arr[i] becomes arr[arr[i]].
           This should be done with O(1) extra space.'''() {

        when:
        rearrange(arr)

        then:
        arr == rearranged

        where:
        arr                | rearranged
        [3, 2, 0, 1]       | [1, 0, 3, 2]
        [4, 0, 2, 1, 3]    | [3, 4, 2, 0, 1]
        [0, 1, 2, 3]       | [0, 1, 2, 3]
        [3, 2, 1, 0]       | [0, 1, 2, 3]
        [2, 0, 1, 4, 5, 3] | [1, 2, 0, 5, 3, 4]

        n = arr.size()
        max = n - 1

    }
}

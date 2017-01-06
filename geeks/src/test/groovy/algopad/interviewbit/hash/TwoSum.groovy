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

package algopad.interviewbit.hash

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/2-sum')
class TwoSum extends Specification {

    /* @Subject My overly complicated solution
    def twoSum = { List a, int sum ->
        int n = a.size()
        def map = [:] as Map<Integer, List>
        for (int i = 0; i < n; i++) {
            map.get(a[i], []) << i
        }
        for (int i2 = 0; i2 < n; i2++) {
            def indices = map[sum - a[i2]]
            if (indices) {
                def i1 = indices.min()
                if (i1 < i2) {
                    return [i1 + 1, i2 + 1]
                }
            }
        }
        []
    }*/

    @Subject
    def twoSum = { List a, int sum ->
        int n = a.size()
        def map = [:]
        for (int i2 = 0; i2 < n; i2++) {
            def val = a[i2]
            def i1 = map[sum - val]
            if (i1) {
                return [i1, i2 + 1]
            }
            map.putIfAbsent val, i2 + 1
        }
        []
    }

    @Unroll
    def '''given an array of integers #a, it should find two numbers "#i1" and "#i2" such that
           they add up to a specific target number #sum.
           It should return indices of the two numbers such that they add up to the target,
           where i1 < i2. The returned answers i1 and i2 are not zero-based.
           If multiple solutions exist, output the one where i2 is minimum. If there are multiple
           solutions with the minimum i2, choose the one with minimum i1 out of them.'''() {

        expect:
        twoSum(a, sum) == indices

        where:
        a                                                    | sum | indices
        [1, 2]                                               | 4   | []
        [1, 1, 1]                                            | 2   | [1, 2]
        [2, 7, 11, 15]                                       | 9   | [1, 2]
        [4, 7, -4, 2, 2, 2, 3, -5, -3, 9, -4, 9, -7, 7, -1, 9,
         9, 4, 1, -4, -2, 3, -3, -5, 4, -7, 7, 9, -4, 4, -8] | -3  | [4, 8]
        i1 = indices[0]
        i2 = indices[1]

    }
}

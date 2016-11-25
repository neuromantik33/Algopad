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
When the array is sorted, try to fix the least integer by looping over it.
Lets say the least integer in the solution is arr[i].
Now we need to find a pair of integers j and k, such that arr[j] + arr[k] is closest to (target - arr[i]).
To do that, let us try the 2 pointer approach.
If we fix the two pointers at the end ( that is, i+1 and end of array ), we look at the sum.
If the sum is smaller than the sum we need to get to, we increase the first pointer.
If the sum is bigger, we decrease the end pointer to reduce the sum.
''')
class ClosestThreeSum extends Specification {

    @Subject
    def closestSum = { List a, int target ->

        int n = a.size()
        int smallestDelta = Integer.MAX_VALUE
        int closestSum = 0

        a.sort()
        outer:
        for (int i = 0; i < n; i++) {
            int j = i + 1, k = n - 1
            while (j < k) {
                int sum = a[i] + a[j] + a[k]
                int delta = (sum - target).abs()
                if (delta == 0) {
                    closestSum = sum
                    break outer
                } else if (delta < smallestDelta) {
                    smallestDelta = delta
                    closestSum = sum
                }
                if (sum < target) {
                    j += 1
                } else {
                    k -= 1
                }
            }
        }
        closestSum
    }

    @Unroll
    def 'given an array #input, it should find the triplet sum which is closest to the given value #target.'() {

        expect:
        closestSum(input, target) == sum

        where:
        input                | target | sum
        [1, 2, 3]            | 6      | 6
        [1, 2, 3]            | 7      | 6
        [12, 3, 4, 1, 6, 9]  | 30     | 27
        [1, 4, 45, 6, 10, 8] | 42     | 50
        [1, 4, 45, 6, 10, 8] | 11     | 11

    }
}

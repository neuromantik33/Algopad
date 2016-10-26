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

package algopad.geeks.arrays

import spock.lang.*

import static java.lang.Math.max
import static java.lang.Math.min

@Narrative('''
To solve this problem, we need to get two optimum indexes of a[]: left index i and right index j.
For an element a[i], we do not need to consider a[i] for left index if there is an element smaller
than a[i] on left side of a[i].
Similarly, if there is a greater element on right side of a[j] then we do not need to consider
this j for right index.
So we construct two auxiliary arrays minLeft[] and maxRight[] such that minLeft[i] holds the
smallest element on left side of a[i] including a[i], and maxRight[j] holds the greatest element
on right side of a[j] including a[j].
After constructing these two auxiliary arrays, we traverse both of these arrays from left to right.
While traversing minLeft[] and maxRight[] if we see that minLeft[i] is greater than maxRight[j],
then we must move ahead in minLeft[] because all elements on left of minLeft[i] are greater than
or equal to minLeft[i]. Otherwise we must move ahead in maxRight[j] to look for a greater j – i value.
''')
@See('http://www.geeksforgeeks.org/given-an-array-arr-find-the-maximum-j-i-such-that-arrj-arri')
class MaxDistance extends Specification {

    @Subject
    def maxDistance = { List a ->

        final int n = a.size()
        if (n == 0) { return -1 }
        if (n == 1) { return 0 }

        def maxRight = new int[n]
        def minLeft = new int[n]

        minLeft[0] = a[0];
        for (int i = 1; i < n; ++i) {
            minLeft[i] = min(a[i], minLeft[i - 1])
        }

        maxRight[n - 1] = a[n - 1];
        for (int j = n - 2; j >= 0; --j) {
            maxRight[j] = max(a[j], maxRight[j + 1])
        }

        int i = 0, j = 0;
        int gap = -1
        while (j < n && i < n) {
            if (minLeft[i] < maxRight[j]) {
                gap = max(gap, j - i)
                j += 1
            } else {
                i += 1
            }
        }
        gap
    }

    @Unroll
    def '''given #input, find the maximum of gap #gap (j - i) subjected to the constraint of A[i] <= A[j].
           If there is no solution possible, return -1.'''() {

        expect:
        maxDistance(input) == gap

        where:
        input                            | gap
        [3, 5, 4, 2]                     | 2
        [1, 2, 3, 4, 5, 6]               | 5
        [9, 2, 3, 4, 5, 6, 7, 8, 18, 0]  | 8
        [34, 8, 10, 3, 2, 80, 30, 33, 1] | 6
        [6, 5, 4, 3, 2, 1]               | -1

    }
}

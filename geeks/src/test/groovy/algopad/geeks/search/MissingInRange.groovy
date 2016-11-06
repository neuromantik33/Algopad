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

package algopad.geeks.search

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Narrative('''
First, let us find the left boundary of the range.
We initialize the range to [i=0, j=n-1]. In each step, calculate the middle element [mid = (i+j)/2].

Now according to the relative value of A[mid] to target, there are three possibilities:
If A[mid] < target, then the range must begin on the right of mid (hence i = mid+1 for the next iteration)
If A[mid] > target, it means the range must begin on the left of mid (j = mid-1)
If A[mid] = target, then the range must begins on the left of or at mid (j= mid)

Since we would move the search range to the same side for case 2) and 3), we might as well merge them as one
single case so that less code is needed:

2*. If A[mid] >= target, j = mid;

Surprisingly, 1 and 2* are the only logic you need to put in loop while (i < j). When the while loop terminates,
the value of i/j is where the start of the range is.
No matter what the sequence originally is, as we narrow down the search range, eventually we will be at a situation
where there are only two elements in the search range.

Suppose our target is 5, then we have only 7 possible cases:

Case 1: [5 7] (A[i] = target < A[j])
Case 2: [5 3] (A[i] = target > A[j])
Case 3: [5 5] (A[i] = target = A[j])
Case 4: [3 5] (A[j] = target > A[i])
Case 5: [3 7] (A[i] < target < A[j])
Case 6: [3 4] (A[i] < A[j] < target)
Case 7: [6 7] (target < A[i] < A[j])

For case 1, 2 and 3, if we follow the above rule, since mid = i => A[mid] = target in these cases,
then we would set j = mid.
Now the loop terminates and i and j both point to the first 5.
For case 4, since A[mid] < target, then set i = mid+1. The loop terminates and both i and j point to 5.
For all other cases, by the time the loop terminates, A[i] is not equal to 5. So we can easily know 5
is not in the sequence if the comparison fails.

In conclusion, when the loop terminates, if A[i]==target, then i is the left boundary of the range;
otherwise, just return -1;

For the right of the range, we can use a similar idea. Again we can come up with several rules:
If A[mid] > target, then the range must begin on the left of mid (j = mid-1)
If A[mid] < target, then the range must begin on the right of mid (hence i = mid+1 for the next iteration)
If A[mid] = target, then the range must begins on the right of or at mid (i= mid)

Again, we can merge conditions 2 and 3 into:

2*. If A[mid] <= target, then i = mid;

However, the terminate condition on longer works this time.
Consider the following case:

[5 7], target = 5
Now A[mid] = 5, then according to rule 2), we set i = mid.
This practically does nothing because i is already equal to mid.
As a result, the search range is not moved at all!
The solution is in using a small trick:
Instead of calculating mid as mid = (i+j)/2, we now do:
mid = (i+j)/2+1
Why does this trick work?
When we use mid = (i+j)/2, the mid is rounded to the lowest integer.
In other words, mid is always biased towards the left. This means we could have i == mid when j - i == mid,
but we NEVER have j == mid. So in order to keep the search range moving, we must make sure that the new i is
set to something different than mid, otherwise we are at the risk that i gets stuck.

But for the new j, it is okay if we set it to mid, since it was not equal to mid anyway.
Our two rules in search for the left boundary happen to satisfy these requirements,
so it works perfectly in that situation.
Similarly, when we search for the right boundary, we must make sure i does not get stuck
when we set the new i to i = mid.
The easiest way to achieve this is by making mid biased to the right, i.e. mid = (i+j)/2+1.
''')
class MissingInRange extends Specification {

    @Subject
    def findRange = { List<Integer> a, int target ->
        def n = a.size()
        def median = { lo, hi -> (lo + hi) >> 1 }
        def findLeftBound = {
            def lo = 0, hi = n - 1
            def bound = -1
            while (lo < hi) {
                def mid = median(lo, hi)
                // Pull left
                def guess = a[mid]
                if (guess == target && (mid == 0 || a[mid - 1] < target)) {
                    bound = mid
                    break
                }
                if (guess >= target) {
                    hi = mid
                } else {
                    lo = mid + 1
                }
            }
            bound
        }
        def findRightBound = {
            def lo = 0, hi = n - 1
            def bound = -1
            while (lo < hi) {
                int mid = median(lo, hi) + 1
                // Pull right
                int guess = a[mid]
                if (guess == target && (mid == n - 1 || a[mid + 1] > target)) {
                    bound = mid
                    break
                }
                if (guess <= target) {
                    lo = mid
                } else {
                    hi = mid - 1
                }
            }
            bound
        }
        def left = findLeftBound()
        def right = findRightBound()
        left > 0 && right > 0 ? left..right : -1..-1
    }

    @Unroll
    def '''given a sorted array #array, find the range #r of a given target #target.
           Runtime complexity must be in the order of O(log n).
           If the target is not found in the array, return [-1, -1].'''() {

        expect:
        findRange(array, target) == range

        where:
        array                                                    | target | range
        [5, 7, 7, 8, 8, 10]                                      | 8      | 3..4
        [4, 7, 7, 7, 8, 10, 10]                                  | 3      | [-1]
        [1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 5, 5, 6, 6, 6, 6, 6, 6, 7,
         7, 8, 8, 8, 9, 9, 9, 10, 10, 10]                        | 4      | [-1]
        [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
         2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
         3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5,
         5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7,
         7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8,
         8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10,
         10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10] | 10     | 118..133

        r = range.inspect()

    }
}

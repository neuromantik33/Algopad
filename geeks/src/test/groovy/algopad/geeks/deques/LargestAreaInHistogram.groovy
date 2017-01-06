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

package algopad.geeks.deques

import algopad.common.ds.LinkedStack
import spock.lang.*

@See('http://www.geeksforgeeks.org/largest-rectangle-under-histogram')
@Narrative('''
Width of all bars is assumed to be 1 for simplicity. For every bar ‘x’, we calculate the area with ‘x’ as the smallest
bar in the rectangle. If we calculate such area for every bar ‘x’ and find the maximum of all areas, our task is done.
How to calculate area with ‘x’ as smallest bar? We need to know index of the first smaller (smaller than ‘x’) bar on
left of ‘x’ and index of first smaller bar on right of ‘x’.
Let us call these indexes as ‘left index’ and ‘right index’ respectively.
We traverse all bars from left to right, maintain a stack of bars. Every bar is pushed to stack once. A bar is popped
from stack when a bar of smaller height is seen. When a bar is popped, we calculate the area with the popped bar as
smallest bar. How do we get left and right indexes of the popped bar – the current index tells us the ‘right index’
and index of previous item in stack is the ‘left index’. Following is the complete algorithm.

1) Create an empty stack.

2) Start from first bar, and do following for every bar ‘hist[i]’ where ‘i’ varies from 0 to n-1.
    a) If stack is empty or hist[i] is higher than the bar at top of stack, then push ‘i’ to stack.
    b) If this bar is smaller than the top of stack, then keep removing the top of stack while top of the stack is
       greater. Let the removed bar be hist[tp]. Calculate area of rectangle with hist[tp] as smallest bar.
       For hist[tp], the ‘left index’ is previous (previous to tp) item in stack and
       ‘right index’ is ‘i’ (current index).

3) If the stack is not empty, then one by one remove all bars from stack and do step 2.b for every removed bar.

references : http://www.informatik.uni-ulm.de/acm/Locals/2003/html/histogram.html
             http://www.informatik.uni-ulm.de/acm/Locals/2003/html/judge.html
''')
class LargestAreaInHistogram extends Specification {

    @Subject
    def largestArea = { List<Integer> histogram ->

        int right = 0, n = histogram.size(), max = 0

        def stack = new LinkedStack<Integer>()
        def updateArea = {
            // Stack top is the smallest (or minimum height) bar
            int left = stack.pop()
            int width = stack.empty ? right : right - stack.peek() - 1
            int area = histogram[left] * width
            max = Math.max(max, area)
        }

        while (right < n) {
            // If this bar is higher than the bar on top of the stack, push it
            if (stack.empty || histogram[right] >= histogram[stack.peek()]) {
                stack.push right
                right++
            } else {
                updateArea()
            }
        }

        // Calculate area for every popped bar on the stack as the smallest bar
        while (!stack.empty) {
            updateArea()
        }

        max

    }

    @Unroll
    def '''it should find the largest rectangular area #area possible in the histogram #histogram
           where the largest rectangle can be made of a number of contiguous bars.
           For simplicity it is assumed that all bars have the same width of one.'''() {

        expect:
        largestArea(histogram) == area

        where:
        histogram                                        | area
        []                                               | 0
        [10]                                             | 10
        [3, 6]                                           | 6
        [1, 2, 3]                                        | 4
        [4, 1000, 1000, 1000, 1000]                      | 4000
        [6, 2, 5, 4, 5, 1, 6]                            | 12
        [7, 2, 1, 4, 5, 1, 3, 3]                         | 8
        [2, 82, 11, 89, 7, 21, 92, 13, 11, 94, 4, 96, 3] | 96

    }
}

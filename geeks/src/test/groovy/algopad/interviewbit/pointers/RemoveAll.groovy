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

package algopad.interviewbit.pointers

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/remove-element-from-array')
class RemoveAll extends Specification {

    /*@Subject // My more complicated solution :/
    def removeAll = { List<Integer> a, int val ->
        def n = a.size()
        if (n == 1 && a[0] == val) {
            a.clear()
        } else if (n > 1) {
            int i = 0, j = 1
            while (i < n) {
                if (a[i] == val) {
                    while (j < n && a[j] == val) { j++ }
                    if (j < n) {
                        a.swap(i, j)
                    } else {
                        break
                    }
                }
                i++
                j = Math.max(j, i + 1)
            }
        }
        int k = n - 1
        while (!a.empty && a[k] == val && k >= 0) {
            a.remove(k)
            k--
        }
        a.size()
    }*/

    @Subject
    def removeAll = { List<Integer> a, int val ->
        int n = a.size()
        int count = 0
        n.times { i ->
            if (a[i] == val) {
                count += 1
            } else {
                a[i - count] = a[i]
            }
        }
        count.times {
            n = a.size()
            a.remove n - 1
        }
        a.size()
    }

    @Unroll
    def 'given an array #a and a value #val, remove all instances of that value in the array.'() {

        expect:
        removeAll(a, val) == remaining.size()
        a == remaining

        where:
        a                     | val
        []                    | 3
        [1]                   | 2
        [2]                   | 2
        [11, 15, 6, 8, 9, 10] | 6
        [4, 1, 1, 2, 1, 3]    | 1
        [2, 0, 1, 2, 0, 3, 2, 2, 2, 1, 0, 0, 0, 1, 0,
         0, 2, 2, 2, 3, 2, 3, 1, 2, 1, 2, 2, 3, 2, 3,
         0, 3, 0, 2, 1, 2, 0, 0, 3, 2, 3, 0, 3, 0, 2,
         3, 2, 2, 3, 1, 3, 3, 0, 3, 3, 0, 3, 3, 2, 0,
         0, 0, 0, 1, 3, 0, 3, 1, 3, 1, 0, 2, 3, 3, 3,
         2, 3, 3, 2, 2, 3, 3, 3, 1, 3, 2, 1, 0, 0, 0,
         1, 0, 3, 2, 1, 0, 2, 3, 0, 2, 1, 1, 3, 2, 0,
         1, 1, 3, 3, 0, 1, 2, 1, 2, 2, 3, 1, 1, 3, 0,
         2, 2, 2, 2, 1, 0, 2, 2, 2, 1, 3, 1, 3, 1, 1,
         0, 2, 2, 0, 2, 3, 0, 1, 2, 1, 1, 3, 0, 2, 3,
         2, 3, 2, 0, 2, 2, 3, 2, 2, 0, 2, 1, 3, 0, 2,
         0, 2, 1, 3, 1, 1, 0, 0, 3, 0, 1, 2, 2, 1, 2,
         0, 1, 0, 0, 0, 1, 1, 0, 3, 2, 3, 0, 1, 3, 0,
         0, 1, 0, 1, 0, 0, 0, 0, 3, 2, 2, 0, 0, 1, 2,
         0, 3, 0, 3, 3, 3, 0, 3, 3, 1, 0, 1, 2, 1, 0,
         0, 2, 3, 1, 1, 3, 2] | 2

        remaining = a.findAll { it != val }

    }
}

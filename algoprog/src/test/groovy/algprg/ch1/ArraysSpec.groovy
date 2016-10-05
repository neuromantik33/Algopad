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

package algprg.ch1

import spock.lang.Shared
import spock.lang.Specification

import static algopad.common.sorting.MergeSort.sort
import static java.lang.Integer.MIN_VALUE

class ArraysSpec extends Specification {

    @Shared
    def random = new Random()

    def '''1.2.1. Fill the array x with zeros. (Write a program fragment whose execution guarantees
           that all values x[1]..x[n] are zero independent of the initial value of x.)'''() {

        given:
        def zero = { x ->
            x.eachWithIndex { int val, int i ->
                x[i] = 0
            }
        }

        when:
        zero(a)

        then:
        a == new int[a.length]

        where:
        a = randomArray(20)

    }

    def '''1.2.2. Count the number of zeros in an array x. (Write a program fragment that does not
           change the value of x and guarantees that the integer variable k contains the number of
           zeros among x[1]..x[n].)'''() {

        given:
        def countZeros = { x ->
            def num = 0
            x.eachWithIndex { int val, int i ->
                if (val == 0) {
                    num += 1
                }
            }
            num
        }

        expect:
        countZeros(a) == numZeros

        where:
        a               | numZeros
        [1, 1, 1]       | 0
        [0, 2, 0, 3, 0] | 3

    }

    def '''1.2.3. Not using assignment statement for arrays, write a program that is equivalent
           to the statement x = y.'''() {

        given:
        def copy = { int[] x ->
            def b = new int[x.length]
            x.eachWithIndex { int val, int i ->
                b[i] = val
            }
            b
        }

        expect:
        copy(a) == a

        where:
        a = (0..9) as int[]

    }

    def '1.2.4. Find the maximum value among x[1]..x[n].'() {

        given:
        def max = { x ->
            def mx = MIN_VALUE
            x.each {
                if (it > mx) { mx = it }
            }
            mx
        }

        expect:
        max(a) == Collections.max(a as List)

        where:
        a = randomArray(20)

    }

    def '''1.2.5. An array x: array[1..n] of integer is given such that x[1] <= x[2] <= ... <= x[n].
           Find the number of different elements among x[1]..x[n].'''() {

        given:
        def countDistinct = { x ->
            def last = null, num = 0
            x.each {
                if (it != last) {
                    //noinspection GrReassignedInClosureLocalVar
                    last = it
                    num += 1
                }
            }
            num
        }

        /* Book solution
        def countDistinct = { x ->
            def k = 1
            for (int i = 1; i < x.size(); i++) {
                if (x[i] != x[i - 1]) {
                    k += 1
                }
            }
            k
        }*/

        expect:
        countDistinct(a) == num

        where:
        a                  | num
        [1, 1, 2, 2, 3, 3] | 3
        (1..10)            | 10

    }

    def '''1.2.6. An array x: array[1..n] of integer is given.
           Compute the number of different elements among x[1]..x[n].
           (The number of operations should be of order n2.)'''() {

        given:
        def countDistinct = { x ->
            def num = 0, distinct = []
            x.each {
                if (!(it in distinct)) {
                    distinct << it
                    num += 1
                }
            }
            num
        }

        expect:
        countDistinct(a) == (a as Set).size()

        where:
        a = randomArray(30)

    }

    def '''1.2.7. The same problem with an additional requirement:
           the number of operations should be of order n log n.'''() {

        def countDistinct = { x ->
            def num = 1, len = x.size()
            def sorted = sort(x)
            for (int i = 1; i < len; i++) {
                if (sorted[i] != sorted[i - 1]) {
                    num += 1
                }
            }
            num
        }

        expect:
        countDistinct(a) == (a as Set).size()

        where:
        a = randomIntList(30)

    }

    private int[] randomArray(int size) {
        def array = new int[size]
        size.times { i ->
            array[i] = random.nextInt(size)
        }
        array
    }

    private List randomIntList(int size) {
        def list = []
        size.times { list << random.nextInt() }
        list.trimToSize()
        list
    }
}

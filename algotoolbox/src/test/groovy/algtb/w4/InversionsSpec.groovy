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

package algtb.w4

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w4.Inversions.countInversions

@Narrative('''An inversion of a sequence a0, a1, ... , an−1 is a pair of indices 0 ≤ i < j < n
              such that ai > aj. The number of inversions of a sequence in some sense measures
              how close the sequence is to being sorted.
              For example, a sorted (in non-descending order) sequence contains no inversions
              at all, while in a sequence sorted in descending order any two elements constitute
              an inversion (for a total of n(n − 1)/2 inversions).''')
class InversionsSpec extends Specification {

    @Unroll
    def 'it should count #count inversions of a given array #a'() {

        given:
        def sorted = a.sort(false)
        a = a as int[]
        int[] aux = new int[a.length]

        expect:
        countInversions(a, aux, 0, a.length - 1) == count
        a == sorted as int[]

        where:
        a                   | count
        [2, 3, 9, 2, 9]     | 2
        [2, 3, 9, 10, 2, 9] | 4
        [9, 8, 7, 3, 2, 1]  | 15

    }
}

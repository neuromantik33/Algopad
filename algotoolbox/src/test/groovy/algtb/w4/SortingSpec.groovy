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

import spock.lang.Specification

import static algtb.w4.Sorting.randomizedQuickSort

class SortingSpec extends Specification {

    def 'it should sort the array of integers using a 3-way quicksort'() {

        given:
        input = input as int[]

        when:
        randomizedQuickSort(input, 0, input.size() - 1)

        then:
        input == sorted as int[]

        where:
        input                    | sorted
        [2, 3, 9, 2, 2]          | [2, 2, 2, 3, 9]
        [1, 1, 1, 1, 1, 1, 1, 0] | [0, 1, 1, 1, 1, 1, 1, 1]

    }
}

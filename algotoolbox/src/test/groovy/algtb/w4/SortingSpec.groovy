/*
 *  algopad.
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

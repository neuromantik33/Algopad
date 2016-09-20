/*
 *  algopad.
 */

package algtb.w4

import spock.lang.Specification

import static algtb.w4.BinarySearch.binarySearch

class BinarySearchSpec extends Specification {

    def 'it should find an integer within an array using binary search'() {

        expect:
        binarySearch(a as int[], x) == index

        where:
        a                 | x  | index
        [1, 5, 8, 12, 13] | 8  | 2
        [1, 5, 8, 12, 13] | 1  | 0
        [1, 5, 8, 12, 13] | 23 | -1
        [1, 5, 8, 12, 13] | 11 | -1
        (2..11)           | 1  | -1
        (2..11)           | 2  | 0
        (2..11)           | 3  | 1
        (2..11)           | 4  | 2
        (2..11)           | 5  | 3
        (2..11)           | 6  | 4
        (2..11)           | 7  | 5
        (2..11)           | 8  | 6
        (2..11)           | 9  | 7
        (2..11)           | 10 | 8
        (2..11)           | 11 | 9
        (2..11)           | 12 | -1

    }
}

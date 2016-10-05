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

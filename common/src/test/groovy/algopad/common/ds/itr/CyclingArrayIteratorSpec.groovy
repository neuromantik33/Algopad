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

package algopad.common.ds.itr

import spock.lang.Specification
import spock.lang.Unroll

class CyclingArrayIteratorSpec extends Specification {

    def array = 0..9 as Integer[]

    @Unroll
    def 'it should iterate starting at #start (len=#len) in ascending order'() {

        when:
        def itr = new CyclingArrayIterator(array, start, len, true)

        then:
        itr.toList() == elements

        where:
        start | len | elements
        0     | 0   | []
        0     | 4   | 0..3
        0     | 10  | 0..9

    }

    @Unroll
    def 'it should iterate starting at #start (len=#len) in descending order'() {

        when:
        def itr = new CyclingArrayIterator(array, start, len, false)

        then:
        itr.toList() == elements

        where:
        start | len | elements
        0     | 0   | []
        3     | 4   | 3..0
        9     | 10  | 9..0

    }

    @Unroll
    def 'it should iterate starting at #start, cycling if necessary'() {

        when:
        def itr = new CyclingArrayIterator(array, start, len, ascending)

        then:
        itr.toList() == elements

        where:
        start | len | ascending | elements
        4     | 10  | true      | (4..9) + (0..3)
        4     | 10  | false     | (4..0) + (9..5)

    }
}

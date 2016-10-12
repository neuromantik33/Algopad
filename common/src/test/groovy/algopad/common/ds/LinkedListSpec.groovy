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

package algopad.common.ds

import spock.lang.Specification
import spock.lang.Unroll

class LinkedListSpec extends Specification {

    def list = new LinkedList()

    def 'initially the linked list is empty'() {

        expect:
        list.empty
        list.size() == 0

    }

    @Unroll
    def 'it should throw an error when #op from an empty list'() {

        expect:
        list.empty

        when:
        list."$op"()

        then:
        thrown NoSuchElementException

        where:
        op << ['head', 'tail']

    }

    def 'it should support basic linked list operations (adding, removing, etc...)'() {

        when:
        list.add 1
        list.add 2

        then:
        list.contains 1
        list.contains 2
        list.head() == 1
        // list.tail() == 2

//        when:
//        list.addFirst 0
//
//        then:
//        list.contains 0
//        list.head() == 0
//        list.tail() == 2
//
//        when:
//        (3..9).each list.&add
//
//        then:
//        list.asList() == (0..9)
//
//        when:
//        [0, 5, 9].each list.&remove
//
//        then:
//        list.asList() == [1, 2, 3, 4, 6, 7, 8]

    }
}

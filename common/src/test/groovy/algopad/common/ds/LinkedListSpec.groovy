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
import spock.lang.Subject

class LinkedListSpec extends Specification {

    @Subject
    def ll = new LinkedList()

    def 'initially it is empty'() {

        expect:
        ll.empty
        ll.size() == 0

    }

    def 'it should throw an error if any null values are added'() {

        when:
        ll.add null

        then:
        thrown IllegalArgumentException

    }

    def 'it should support basic operations (insertion, removal, searching etc...)'() {

        when:
        ll.add 0

        then:
        ll.first == 0
        ll.size() == 1

        when:
        (1..9).each ll.&add

        then:
        ll.containsAll 0..9
        ll.size() == 10

        expect:
        ll.removeAll { it % 2 == 0 }
        ll.first == 1
        ll == [1, 3, 5, 7, 9]

        when:
        ll.clear()

        then:
        ll.empty
        ll.first == null
        ll == []

        when:
        ll.addAll 9..7

        then:
        ll.size() == 3
        ll.first == 9
        ll == [9, 8, 7]

    }

    def 'it should support an in-place reverse'() {

        given:
        ll.addAll 0..9

        when:
        ll.reverse()

        then:
        ll == 9..0

    }

    def 'it should support iterating over nodes and inserting after nodes'() {

        given:
        ll.addAll 0..9

        expect:
        ll.nodeIterator()*.value == 0..9 as List

        when:
        [0, 5, -1].each {
            def node = ll.nodeIterator()[it]
            ll.insertAfter 1, node
        }

        then:
        ll.size() == old(ll.size()) + 3
        ll == [0, 1, 1, 2, 3, 4, 1, 5, 6, 7, 8, 9, 1]

        when: 'Last node (indexed at -1) is ignored for removal'
        [0, 4, -1].each {
            def node = ll.nodeIterator()[it]
            ll.removeAfter node
        }

        then:
        ll.size() == old(ll.size()) - 2
        ll.nodeIterator()*.value == (0..9) + [1]

    }
}

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

package algopad.algorithms.pt1.w2

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@SuppressWarnings("GroovyAccessibility")
class DequeSpec extends Specification {

    @Subject
    def deque = new Deque<Integer>()

    @Unroll
    def 'it should throw an error if one attempts to #method an item from an empty deque'() {

        when:
        deque."$method"()

        then:
        thrown NoSuchElementException

        where:
        method << ['removeFirst', 'removeLast']

    }

    @Unroll
    def 'it should throw an error if one attempts to #method a null item'() {

        when:
        deque."$method"()

        then:
        thrown NullPointerException

        where:
        method << ['addFirst', 'addLast']

    }

    def 'it should throw an error if one attempts to remove() from an iterator'() {

        when:
        deque.iterator().remove()

        then:
        thrown UnsupportedOperationException

    }

    @SuppressWarnings("ChangeToOperator")
    def 'it should throw an error if next() is called when there are no more items to return'() {

        when:
        deque.iterator().next()

        then:
        thrown NoSuchElementException

        when:
        deque.addFirst 1

        then:
        deque.iterator().hasNext()

        when:
        def iterator = deque.iterator()
        def head = iterator.next()

        then:
        head == 1
        !iterator.hasNext()

        when:
        iterator.next()

        then:
        thrown NoSuchElementException

    }

    def 'it should support iteration without any removal'() {

        given:
        (0..9).each { deque.addLast it }

        when:
        def results = deque.asList()

        then:
        results == (0..9) as List
        deque.size() == 10

        when: 'Testing simultaneous iterators'
        results = []
        for (Integer val : deque) {
            for (Integer val2 : deque) {
                results << [val, val2]
            }
        }

        then:
        results.sort() == [(0..9), (0..9)].combinations().sort()

    }

    def 'it should support the default deque operations'() {

        def dequeIsEmpty = { deque.empty && deque.size() == 0 }
        def dequeHasSize = { size -> !deque.empty && deque.size() == size }

        expect:
        dequeIsEmpty()

        when:
        deque.addFirst 1

        then:
        dequeHasSize 1

        when:
        def head = deque.removeFirst()

        then:
        head == 1
        dequeIsEmpty()

        when:
        deque.addLast 2

        then:
        dequeHasSize 1

        when:
        def tail = deque.removeLast()

        then:
        tail == 2
        dequeIsEmpty()

        when:
        deque.addFirst 100
        deque.addFirst 101

        then:
        def results = [deque.removeLast(), deque.removeLast()]
        results == [100, 101]

    }

    @Unroll
    def 'it should be able to perform #add() #elmts to the followed by #remove() in reverse order'() {

        when:
        elmts.each {
            deque."$add"(it)
        }

        then:
        deque.size() == elmts.size()

        when:
        def removed = []
        (1..deque.size()).each {
            removed << deque."$remove"()
        }

        then:
        deque.empty
        removed == elmts.reverse()

        where:
        elmts              | add        | remove
        [0, 1, 2, 3, 4, 5] | 'addLast'  | 'removeLast'
        [9, 8, 7, 6, 5]    | 'addFirst' | 'removeFirst'

    }
}

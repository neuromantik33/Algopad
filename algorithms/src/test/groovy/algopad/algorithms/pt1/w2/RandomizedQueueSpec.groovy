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

import static edu.princeton.cs.algs4.StdRandom.seed
import static java.lang.System.currentTimeMillis

@SuppressWarnings("GroovyAccessibility")
class RandomizedQueueSpec extends Specification {

    @Subject
    def queue = new RandomizedQueue<Integer>()

    @Unroll
    def 'it should throw an error if one attempts to #method an item from an empty queue'() {

        when:
        queue."$method"()

        then:
        thrown NoSuchElementException

        where:
        method << ['dequeue', 'sample']

    }

    def 'it should throw an error if one attempts to enqueue a null item'() {

        when:
        queue.enqueue null

        then:
        thrown NullPointerException

    }

    def 'it should throw an error if one attempts to remove() from an iterator'() {

        when:
        queue.iterator().remove()

        then:
        thrown UnsupportedOperationException

    }

    @SuppressWarnings("ChangeToOperator")
    def 'it should throw an error if next() is called when there are no more items to return'() {

        given:
        queue.enqueue 1

        when:
        def iterator = queue.iterator()

        then:
        iterator.hasNext()

        when:
        def head = iterator.next()

        then:
        head == 1
        !iterator.hasNext()

        when:
        iterator.next()

        then:
        thrown NoSuchElementException

    }

    def 'it should support the default queue operations'() {

        def dequeIsEmpty = { queue.empty && queue.size() == 0 }
        def dequeHasSize = { size -> !queue.empty && queue.size() == size }

        expect:
        dequeIsEmpty()

        when:
        queue.enqueue 1

        then:
        dequeHasSize 1

        when:
        def head = queue.sample()

        then:
        head == 1
        dequeHasSize 1

        when:
        head = queue.dequeue()

        then:
        dequeIsEmpty()
        head == old(head)

    }

    def 'the internal array capacity should double once filled'() {

        expect:
        queue.elements.length == 10

        when:
        (0..10).each { queue.enqueue it }

        then:
        queue.elements.length == 20

        when: 'If the queue is less than 1/4 full, then the capacity should be halved'
        6.times { queue.dequeue() }

        then:
        queue.elements.length == 10

    }

    @Unroll
    def 'it should iterate randomly (seed: #mockSeed) every time an iterator is traversed'() {

        given:
        seed = mockSeed
        (0..9).each { queue.enqueue it }

        when: 'Iterator sampling'
        def results = queue.asList()

        then:
        results == shuffled

        when: 'Normal sampling'
        seed = mockSeed
        results.clear()
        10.times {
            results << queue.sample()
        }

        then:
        results == sampled

        cleanup:
        seed = currentTimeMillis()

        where:
        mockSeed       | shuffled                       | sampled
        1              | [5, 2, 0, 3, 6, 9, 7, 8, 1, 4] | [5, 8, 7, 3, 4, 4, 4, 6, 8, 8]
        Long.MAX_VALUE | [3, 6, 2, 9, 1, 8, 7, 4, 0, 5] | [3, 5, 9, 9, 4, 8, 7, 8, 5, 1]

    }

    @Unroll
    def 'it should randomly (seed: #mockSeed) empty the queue in a similar manner'() {

        given:
        seed = mockSeed
        (0..9).each { queue.enqueue it }

        when:
        def results = []
        10.times {
            results << queue.dequeue()
        }

        then:
        results == dequeued
        queue.empty

        cleanup:
        seed = currentTimeMillis()

        where:
        mockSeed       | dequeued
        1              | [5, 1, 3, 0, 2, 4, 8, 7, 9, 6]
        Long.MAX_VALUE | [3, 5, 0, 6, 2, 9, 1, 7, 4, 8]

    }
}

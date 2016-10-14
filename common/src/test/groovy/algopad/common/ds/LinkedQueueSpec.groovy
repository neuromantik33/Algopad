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
import spock.lang.Unroll

class LinkedQueueSpec extends Specification {

    @Subject
    def queue = new LinkedQueue()

    def 'initially it is empty'() {

        expect:
        queue.empty
        queue.size() == 0

    }

    @Unroll
    def 'it should throw an error when #op from an empty queue'() {

        expect:
        queue.empty

        when:
        queue."$op"()

        then:
        thrown NoSuchElementException

        where:
        op << ['remove', 'element']

    }

    def 'it should support basic queue operations (offering, polling, peeking)'() {

        when:
        queue.offer 0

        then:
        queue.peek() == 0

        when:
        for (int i in 1..9) {
            queue.offer i
        }

        then:
        queue.size() == 10
        queue.poll() == 0
        queue.poll() == 1
        queue.poll() == 2
        queue.size() == 7

        when: 'clear the queue to make sure tail is correct'
        queue.clear()
        queue.offer 99

        then:
        queue == [99]

    }
}

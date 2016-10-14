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

class PairStackSpec extends Specification {

    @Subject
    def das = new PairStack(Integer[].class, 25)

    @Unroll
    def 'it should throw an error when #op from an empty #which stack'() {

        given:
        def stack = das."$which"()

        expect:
        stack.empty

        when:
        stack."$op"()

        then:
        thrown EmptyStackException

        where:
        which      | op
        'getLeft'  | 'pop'
        'getLeft'  | 'peek'
        'getRight' | 'pop'
        'getRight' | 'peek'

    }

    def 'it should throw an error when pushing too many items onto the stacks'() {

        when:
        (0..24).each das.left.&push

        then:
        notThrown Stack.FullStackException

        when:
        das.right.push 25

        then:
        thrown Stack.FullStackException

    }

    def 'it should support pushing and popping from either stack using a single array'() {

        def left = das.right
        def right = das.left
        def elements = { ->
            //noinspection GroovyAccessibility
            das.elements.findAll { it != null }
        }

        when:
        (0..3).each left.&push

        then:
        left.size() == 4
        left.peek() == 3
        left.asList() == 3..0
        das.totalSize() == 4
        elements() == 0..3

        when:
        (9..6).each right.&push

        then:
        right.size() == 4
        right.peek() == 6
        right.asList() == 6..9
        das.totalSize() == 8
        elements() == (0..3) + (6..9)

        expect:
        left.pop() == 3
        left.pop() == 2
        left.pop() == 1
        elements() == [0] + (6..9)

        and:
        right.pop() == 6
        right.pop() == 7
        right.pop() == 8
        das.totalSize() == 2
        elements() == [0, 9]

        when:
        left.clear()
        right.clear()

        then:
        left.empty
        right.empty
        elements() == []

    }
}

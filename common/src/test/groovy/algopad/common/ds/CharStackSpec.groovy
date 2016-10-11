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

class CharStackSpec extends Specification {

    def stack = new CharStack(25)

    @Unroll
    def 'it should throw an error when #op from an empty stack'() {

        expect:
        stack.empty

        when:
        stack."$op"()

        then:
        thrown EmptyStackException

        where:
        op << ['pop', 'peek']

    }

    def 'it should throw an error when pushing too many items onto the stack'() {

        when:
        for (char c in 'a'..'y') {
            stack.push c
        }

        then:
        notThrown IllegalArgumentException

        when:
        stack.push 'z' as char

        then:
        thrown IllegalArgumentException

    }

    def 'it should support basic stack operations (pushing, popping, peeking)'() {

        when:
        stack.push('a' as char)

        then:
        stack.peek() == 'a' as char

        when:
        for (char c in 'bcdefg') {
            stack.push c
        }

        then:
        stack.size() == 7
        stack.pop() == 'g' as char
        stack.pop() == 'f' as char
        stack.pop() == 'e' as char
        stack.size() == 4

    }
}

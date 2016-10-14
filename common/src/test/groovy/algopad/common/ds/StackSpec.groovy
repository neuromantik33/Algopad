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

class StackSpec extends Specification {

    def 'it should throw an error when pushing too many items onto an array stack'() {

        @Subject
        def stack = newStack('array')

        when:
        for (char c in 'a'..'y') {
            stack.push c
        }

        then:
        notThrown Stack.FullStackException

        when:
        stack.push 'z' as char

        then:
        thrown Stack.FullStackException

    }

    @Unroll
    def 'initially the #impl stack is empty'() {

        expect:
        stack.empty
        stack.size() == 0

        where:
        impl << ['array', 'linked']
        stack = newStack(impl)

    }

    @Unroll
    def 'it should throw an error when #op-ing from an empty #impl stack'() {

        expect:
        stack.empty

        when:
        stack."$op"()

        then:
        thrown EmptyStackException

        where:
        impl     | op
        'array'  | 'pop'
        'array'  | 'peek'
        'linked' | 'pop'
        'linked' | 'peek'

        stack = newStack(impl)

    }

    @Unroll
    def 'the #impl stack should support basic stack operations (pushing, popping, peeking)'() {

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

        and:
        stack.join() == 'dcba'

        when:
        stack.clear()

        then:
        stack.empty
        stack.asList() == []

        where:
        impl << ['array', 'linked']
        stack = newStack(impl)

    }

    private static Stack newStack(name) {
        def impls = [
          'array' : { new ArrayStack(Character[].class, 25) },
          'linked': { new LinkedStack<Character>() }
        ]
        impls[name].call()
    }
}

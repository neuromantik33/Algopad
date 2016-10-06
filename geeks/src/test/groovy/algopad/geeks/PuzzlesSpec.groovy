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

package algopad.geeks

import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll

class PuzzlesSpec extends Specification {

    @Unroll
    @See('http://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression')
    def '''given an expression string #exp, it should examine whether the pairs and the orders of
           '{','}','(',')','[',']' are #correct.'''() {

        given:
        def areParenthesesBalanced = { String s ->
            def pairs = ['}': '{', ')': '(', ']': '[']
            def stack = new Stack<Character>()
            for (char c in s) {
                def closeCh = pairs[c as String]
                if (closeCh) {
                    if (stack.empty || stack.pop() != closeCh) {
                        return false
                    }
                } else {
                    stack.push c
                }
            }
            true
        }

        expect:
        areParenthesesBalanced(exp) == balanced

        where:
        exp                | balanced
        '[()]{}{[()()]()}' | true
        '[(])'             | false
        '()]{}{[()()]()}'  | false

        correct = balanced ? 'balanced' : 'unbalanced'

    }

    @Unroll
    @See('http://www.geeksforgeeks.org/given-a-string-find-its-first-non-repeating-character')
    def 'given a string "#input", it should find the first non-repeating character #c in it'() {

        given:
        def findNonRepeatingChar = { String s ->
            def counts = [:] as LinkedHashMap
            for (char c in s) {
                counts[c] = counts.get(c, 0) + 1
            }
            counts.find { it.value == 1 }.key as char
        }

        expect:
        findNonRepeatingChar(input) == c as char

        where:
        input           | c
        'GeeksforGeeks' | 'f'
        'GeeksQuiz'     | 'G'

    }

    @Unroll
    def '''given a linked list #list, it should modify the list such that all even numbers appear
           before all the odd numbers in the modified linked list, preserving relative order.'''() {

        given:
        def segregate = { LinkedList ll ->
            def i = 0
            def len = ll.size()
            while (i < len) {
                if (ll[i] % 2 != 0) {
                    def val = ll.remove(ll.first)
                    ll.add val
                }
                i += 1
            }
        }

        and:
        list = list as LinkedList
        segregated = segregated as LinkedList

        when:
        segregate list

        then:
        list == segregated

        where:
        list                               | segregated
        // [8, 12, 10]                        | [8, 12, 10]
        [1, 3, 5, 7]                       | [1, 3, 5, 7]
//        [8, 12, 10, 5, 4, 1, 6]            | [8, 12, 10, 4, 6, 5, 1]
//        [17, 15, 8, 12, 10, 5, 4, 1, 7, 6] | [8, 12, 10, 4, 6, 17, 15, 5, 1, 7]

    }
}

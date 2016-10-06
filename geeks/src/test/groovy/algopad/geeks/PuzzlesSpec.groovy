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

import spock.lang.Specification
import spock.lang.Unroll

class PuzzlesSpec extends Specification {

    @Unroll
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
    def 'given a string #input, it should find the first non-repeating character #c in it'() {

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
}

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

package algopad.geeks.deques

import algopad.common.ds.ArrayStack
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression')
class BalancedParentheses extends Specification {

    @Subject
    def areParenthesesBalanced = { String s ->
        def pairs = [
          '}': '{',
          ')': '(',
          ']': '[',
          '>': '<'
        ]
        def parentheses = (pairs.keySet() + pairs.values()).collect { it as char } as Set
        def stack = new ArrayStack(Character[].class, s.length())
        for (char c in s) {
            if (c in parentheses) {
                def closeCh = pairs[c as String]
                if (closeCh) {
                    if (stack.empty || stack.pop() != closeCh as char) {
                        return false
                    }
                } else {
                    stack.push c
                }
            }
        }
        stack.empty
    }

    @Unroll
    def '''given an expression string "#exp", it should examine whether the pairs and the orders of
           '{','}','(',')','[',']','<','>' are #correct.'''() {

        expect:
        areParenthesesBalanced(exp) == balanced

        where:
        exp                        | balanced
        '('                        | false
        ')'                        | false
        '<>{}'                     | true
        '[h(e)]l{}l{[o(y)(o)](u)}' | true
        '[(])'                     | false
        '()]{}{[()()]()}'          | false

        correct = balanced ? 'balanced' : 'unbalanced'

    }
}

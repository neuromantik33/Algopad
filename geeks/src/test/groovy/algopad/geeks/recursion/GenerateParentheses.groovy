/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

package algopad.geeks.recursion

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/print-all-combinations-of-balanced-parentheses')
class GenerateParentheses extends Specification {

    @Subject
    def genParentheses = { int n ->
        def combos = []
        def generate = { int i, int open, int closed, String s ->
            if (closed == n) {
                combos << s
            } else {
                if (open < n) { call i + 1, open + 1, closed, "$s(" }
                if (open > closed) { call i + 1, open, closed + 1, "$s)" }
            }
        }
        if (n > 0) {
            generate 0, 0, 0, ''
        }
        combos
    }

    @Unroll
    def '''Given #n pairs of parentheses, it should generate all combinations
           of well-formed parentheses of length 2*n.'''() {

        expect:
        genParentheses(n) == result

        where:
        n | result
        0 | []
        1 | ['()']
        2 | ['(())', '()()']
        3 | ['((()))', '(()())', '(())()', '()(())', '()()()']
        4 | ['(((())))', '((()()))', '((())())', '((()))()',
             '(()(()))', '(()()())', '(()())()', '(())(())',
             '(())()()', '()((()))', '()(()())', '()(())()',
             '()()(())', '()()()()']

    }
}

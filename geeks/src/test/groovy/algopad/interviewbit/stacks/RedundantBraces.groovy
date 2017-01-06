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

package algopad.interviewbit.stacks

import algopad.common.ds.LinkedStack
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/redundant-braces')
class RedundantBraces extends Specification {

    @Subject
    def hasRedundantBraces = { String s ->
        def stack = new LinkedStack<String>()
        def operators = ['+', '*', '-', '/'] as Set
        def st = new StringTokenizer(s, '+*-/()', true)
        while (st.hasMoreTokens()) {
            def token = st.nextToken()
            switch (token) {
                case '(':
                case operators:
                    stack.push token
                    break
                case ')':
                    if (stack.peek() == '(') {
                        return true
                    } else {
                        while (!stack.empty &&
                               stack.peek() in operators) {
                            stack.pop()
                        }
                        stack.pop() // Pop '('
                    }
                    break
                default:
                    break // Do nothing
            }
        }
        false
    }

    @Unroll
    def '''given a string "#s", it should verify that there #are redundant braces
           the operators allowed are #operators'''() {

        expect:
        hasRedundantBraces(s) == redundant

        where:
        s               | redundant
        '()'            | true
        '((a+b))'       | true
        '(a + (a + b))' | false

        operators = ['+', '*', '-', '/']
        are = redundant ? 'are' : "aren't"

    }
}

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

package algopad.interviewbit.stacks

import algopad.common.ds.LinkedStack
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/evaluate-expression')
class ReversePolishNotation extends Specification {

    @Subject
    def evaluateRpn = { List expression ->
        assert !expression?.empty
        def operators = ['+', '-', '*', '/'] as Set
        def stack = new LinkedStack<String>()
        expression.each { String c ->
            if (c in operators) {
                int op2 = stack.pop() as int
                int op1 = stack.pop() as int
                switch (c) {
                    case '+':
                        stack.push op1 + op2
                        break
                    case '-':
                        stack.push op1 - op2
                        break
                    case '*':
                        stack.push op1 * op2
                        break
                    case '/':
                        stack.push op1 / op2
                        break
                    default:
                        assert false, 'Impossible'
                }
            } else {
                stack.push c
            }
        }
        stack.peek() as int
    }

    @Unroll
    def '''it should evaluate the value of an arithmetic expression "#exp" in Reverse Polish Notation.
           Valid operators are +, -, *, /. Each operand may be an integer or another expression.'''() {

        expect:
        evaluateRpn(exp) == val

        where:
        exp                        | val
        ['99']                     | 99
        ['1', '1', '+']            | 2 // 1+1
        ['2', '1', '+', '3', '*']  | 9 // (2+1)*3
        ['4', '13', '5', '/', '+'] | 6 // 4+(13/5)

    }
}

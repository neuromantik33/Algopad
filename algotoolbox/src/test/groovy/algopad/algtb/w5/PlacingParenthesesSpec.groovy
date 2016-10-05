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

package algopad.algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static PlacingParentheses.calculateMaximumValue

@Narrative('In this problem, your goal is to add parentheses to a given arithmetic expression to maximize its value.')
class PlacingParenthesesSpec extends Specification {

    @Unroll
    def '''it should find the maximum value #value for #exp by specifying the order
           of applying its arithmetic operations using additional parentheses.'''() {

        expect:
        calculateMaximumValue(exp) == value

        where:
        exp           | value
        '1+5'         | 6
        '5-8+7*4-8+9' | 200
        '1+2-3*4-5'   | 6

    }
}

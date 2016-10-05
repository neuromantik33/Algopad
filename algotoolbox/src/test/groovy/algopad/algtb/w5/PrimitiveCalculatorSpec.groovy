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

import static PrimitiveCalculator.calculateOptimalSequence

@Narrative('''You are given a primitive calculator that can perform the following three operations
              with the current number x: multiply x by 2, multiply x by 3, or add 1 to x.
              Your goal is given a positive integer n, find the minimum number of operations
              needed to obtain the number n starting from the number 1.''')
class PrimitiveCalculatorSpec extends Specification {

    @Unroll
    def '''Given an integer #n, it should compute the minimum number of operations needed to obtain
           the number #n starting from the number 1.'''() {

        expect:
        calculateOptimalSequence(n) == sequence

        where:
        n     | sequence
        1     | [1]
        2     | [1, 2]
        3     | [1, 3]
        5     | [1, 3, 4, 5]
        6     | [1, 3, 6]
        96234 | [1, 3, 9, 10, 11, 33, 99, 297, 891, 2673, 8019, 16038, 16039, 48117, 96234]

    }
}

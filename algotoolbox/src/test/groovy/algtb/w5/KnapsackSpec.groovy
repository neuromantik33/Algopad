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

package algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w5.Knapsack.calculateOptimalWeight

@Narrative('This problem is about implementing an algorithm for the knapsack without repetitions problem.')
class KnapsackSpec extends Specification {

    @Unroll
    def '''it should calculate the optimal knapsack capacity never exceeding #maxCapacity
           for equally valuable items with weight #weights'''() {

        expect:
        calculateOptimalWeight(maxCapacity, weights as int[]) == weight

        where:
        maxCapacity | weights         | weight
        10          | [1, 4, 8]       | 9
        10          | [3, 5, 3, 3, 5] | 10

    }
}

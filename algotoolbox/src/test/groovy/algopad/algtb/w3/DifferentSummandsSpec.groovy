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

package algopad.algtb.w3

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static DifferentSummands.calculateOptimalSummands

@Narrative('''You are organizing a funny competition for children. As a prize fund you have n candies.
              You would like to use these candies for top n places in a competition with a natural
              restriction that a higher place gets a larger number of candies.
              To make as many children happy as possible, you are going to find the largest value
              of n for which it is possible.''')
class DifferentSummandsSpec extends Specification {

    @Unroll
    def '''it should find the maximum k such that #n can be written as a1 + a2 + ... + ak
           where a1...ak are positive integers and ai != aj for all 1 ≤ i < j ≤ k.'''() {

        expect:
        calculateOptimalSummands(n) == summands as List<Long>

        where:
        n | summands
        2 | [2]
        6 | [1, 2, 3]
        8 | [1, 2, 5]

    }

    @Unroll
    def 'it should find #numSummands different summands for #n'() {

        expect:
        calculateOptimalSummands(n).size() == numSummands

        where:
        n             | numSummands
        987654321     | 44443
        12345678910   | 157134
        2673516735757 | 2312364

    }
}

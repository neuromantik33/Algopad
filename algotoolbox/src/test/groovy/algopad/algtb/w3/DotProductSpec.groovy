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

import spock.lang.Specification
import spock.lang.Unroll

import static DotProduct.maxDotProduct

class DotProductSpec extends Specification {

    @Unroll
    def '''given 2 sequences :
           - a1..an (ai is the profit per click of the ith ad) and
           - b1..bn (bi is the average number of clicks per day of the ith slot,
           it should partition them into n pairs (ai,bj) such that the sum of their products #sum is maximized'''() {

        expect:
        maxDotProduct(ads as int[], slots as int[]) == sum

        where:
        ads        | slots      | sum
        [23]       | [39]       | 897
        [1, 3, -5] | [-2, 4, 1] | 23

    }
}

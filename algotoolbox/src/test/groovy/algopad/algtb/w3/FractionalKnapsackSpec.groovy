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

import algopad.algtb.w3.FractionalKnapsack.Item
import spock.lang.Specification
import spock.lang.Unroll

import static FractionalKnapsack.calculateOptimalValue

class FractionalKnapsackSpec extends Specification {

    @Unroll
    def 'it should calculate the maximal value of fractions of #n items that fit into a knapsack of capacity #W'() {

        given:
        items = items.collect { new Item(it[0], it[1]) } as Item[]

        expect:
        calculateOptimalValue(W, items).round(4) == maxVal

        where:
        W  | items                            | maxVal
        10 | [[500, 30]]                      | 166.6667
        50 | [[60, 20], [100, 50], [120, 30]] | 180.0

        n = items.size()

    }
}

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

package algopad.geeks.greedy

import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/find-a-tour-that-visits-all-stations')
@Narrative('''There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
              You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i
              to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.''')
class GasStationTour extends Specification {

    @Subject
    def determineStartIndex = { List gas, List cost ->
        def n = gas.size()
        def start = -1

        def remainingGas
        def hasEnoughGas = { idx ->
            cost[idx - 1] <= remainingGas
        }

        for (int i = 0; i < n; i++) {
            remainingGas = gas[i]
            int j = i + 1
            while (j < n && hasEnoughGas(j)) {
                remainingGas += gas[j] - cost[j]
                j++
            }
            if (hasEnoughGas(j) && j == n) {
                start = i
                break
            }
        }

        start

    }

    @Unroll
    def '''given the amount of gas at stations #gas and the costs #cost to reach next gas station,
           it should return the minimum starting gas station\'s index #start when travelling the circuit once,
           or -1 if impossible'''() {

        expect:
        determineStartIndex(gas, cost) == start

        where:
        gas          | cost         | start
        [1, 2]       | [2, 1]       | 1
        [4, 6, 7, 4] | [6, 5, 3, 5] | 1
        [6, 3, 7]    | [4, 6, 3]    | 2
        [13, 1, 11]  | [1, 23, 1]   | 2
        [1, 2, 3, 4] | [2, 3, 4, 5] | -1

    }
}

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

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/stock-buy-sell')
class StockBuySell extends Specification {

    @Subject
    def findSellingIntervals = { List stocks ->
        def intervals = []
        def last = 0
        def len = stocks.size() - 1
        for (int i = 0; i < len; i++) {
            if (stocks[i] > stocks[i + 1]) {
                if (last != i) {
                    intervals << [last, i]
                }
                last = i + 1
            }
        }
        if (last != len) {
            intervals << [last, len]
        }
        intervals
    }

    @Unroll
    def '''given an array of the cost of a stock each day, it should find the max profit #maxProfit that can be made
           by buying and selling in those days.'''() {

        expect:
        findSellingIntervals(stocks) == intervals

        where:
        stocks                             | intervals
        (9..0)                             | []
        [100, 180, 260, 310, 40, 535, 695] | [[0, 3], [4, 6]]

        maxProfit = intervals.inject(0) { profit, interval ->
            def max = stocks[interval[1]]
            def min = stocks[interval[0]]
            profit + (max - min)
        }
    }
}

/*
 *  algopad.
 */

package algtb.w3

import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.FractionalKnapsack.calculateOptimalValue

class FractionalKnapsackSpec extends Specification {

    @Unroll
    def 'it should calculate the maximal value of fractions of #n items that fit into a knapsack of capacity #W'() {

        expect:
        calculateOptimalValue(W, values as int[], weights as int[]).round(4) == maxVal

        where:
        W  | values         | weights      | maxVal
        10 | [500]          | [30]         | 166.6667
        50 | [60, 100, 120] | [20, 50, 30] | 180.0

        n = values.size()

    }
}

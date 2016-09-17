/*
 *  algopad.
 */

package algtb.w3

import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.FractionalKnapsack.calculateOptimalValue
import algtb.w3.FractionalKnapsack.Item

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

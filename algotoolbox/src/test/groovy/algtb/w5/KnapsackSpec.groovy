/*
 *  algopad.
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
        maxCapacity | weights   | weight
        10          | [1, 4, 8] | 9

    }
}

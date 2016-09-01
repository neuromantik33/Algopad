/*
 *  algopad.
 */

package algtb.w1

import spock.lang.Specification

import static algtb.w1.MaxPairwiseProduct.getMaxPairwiseProduct
import static java.lang.Integer.MAX_VALUE

class MaxPairwiseProductSpec extends Specification {

    def 'it should calculate the maximum pairwise product'() {

        expect:
        getMaxPairwiseProduct(numbers as int[]) == product

        where:
        numbers                                                                                | product
        [1, 2, 3]                                                                              | 6
        [7, 5, 14, 2, 8, 8, 10, 1, 2, 3]                                                       | 140
        [4, 6, 2, 6, 1]                                                                        | 36
        [38, 35, 2, 31, 15, 49, 17, 35, 1, 38, 6, 24, 27, 49, 39, 14, 43, 28, 4, 21, 23, 2, 24, 45, 20, 45, 41, 26,
         48, 1, 21, 33, 32, 26, 10, 12, 26, 49, 15, 41, 39, 21, 30, 14, 49, 18, 38, 8, 28, 33] | 2401
        [MAX_VALUE, MAX_VALUE]                                                                 | 4611686014132420609L

    }
}

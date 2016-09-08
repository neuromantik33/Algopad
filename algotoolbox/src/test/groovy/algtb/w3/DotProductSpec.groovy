/*
 *  algopad.
 */

package algtb.w3

import spock.lang.Specification

import static algtb.w3.DotProduct.maxDotProduct

class DotProductSpec extends Specification {

    def '''given 2 sequences a1..an (ai is the profit per click of the ith ad) and b1..bn
           (bi is the average number of clicks per day of the ith slot, it should partition them into
           n pairs (ai,bj) such that the sum of their products is maximized'''() {

        expect:
        maxDotProduct(ads as int[], slots as int[]) == sum

        where:
        ads        | slots      | sum
        [23]       | [39]       | 897
        [1, 3, -5] | [-2, 4, 1] | 23

    }
}

/*
 *  algopad.
 */

package algtb.w3

import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.Change.getChange

class ChangeSpec extends Specification {

    @Unroll
    def '''it should calculate the minimum number of coins needed to change the input value #m
           into coins with denominations 1, 5, and 10.'''() {

        expect:
        getChange(m) == numCoins

        where:
        m  | numCoins
        2  | 2
        28 | 6

    }
}

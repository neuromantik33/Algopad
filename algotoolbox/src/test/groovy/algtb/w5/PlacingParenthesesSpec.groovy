/*
 *  algopad.
 */

package algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w5.PlacingParentheses.calculateMaximumValue

@Narrative('In this problem, your goal is to add parentheses to a given arithmetic expression to maximize its value.')
class PlacingParenthesesSpec extends Specification {

    @Unroll
    def '''it should find the maximum value #value for #exp by specifying the order
           of applying its arithmetic operations using additional parentheses.'''() {

        expect:
        calculateMaximumValue(exp) == value

        where:
        exp           | value
        '1+5'         | 6
        '5-8+7*4-8+9' | 200
        '1+2-3*4-5'   | 6

    }
}

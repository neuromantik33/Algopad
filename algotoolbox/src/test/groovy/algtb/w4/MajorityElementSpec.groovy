/*
 *  algopad.
 */

package algtb.w4

import spock.lang.Narrative
import spock.lang.Specification

import static algtb.w4.MajorityElement.hasMajorityElement

@Narrative('''Majority rule is a decision rule that selects the alternative which has a majority,
              that is, more than half the votes''')
class MajorityElementSpec extends Specification {

    def 'Given an array of ints #input, it should check whether it contains an int that appears more than n/2 times'() {

        expect:
        hasMajorityElement(input as int[]) == hasMajority

        where:
        input           | hasMajority
        [2, 3, 9, 2, 2] | true
        [1, 2, 3, 4]    | false
        [1, 2, 3, 1]    | false

    }
}

/*
 *  algopad.
 */

package algtb.w3

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.LargestNumber.getLargestNumber

@Narrative('''As the last question of a successful interview, your boss gives you a few pieces of paper
              with numbers on it and asks you to compose a largest number from these numbers.
              The resulting number is going to be your salary, so you are very much interested in
              maximizing this number.''')
class LargestNumberSpec extends Specification {

    @Unroll
    def 'it should compose the largest number #largest out of a set of integers #numbers'() {

        given:
        numbers = numbers.collect { "$it" } as String[]

        expect:
        getLargestNumber(numbers) == largest

        where:
        numbers                        | largest
        [9, 4, 6, 1, 9]                | '99641'
        [21, 2]                        | '221'
        [23, 39, 92]                   | '923923'
        [858, 85]                      | '85885'
        [9, 2, 200, 100, 110, 8, 1, 1] | '98220011110100'

    }
}
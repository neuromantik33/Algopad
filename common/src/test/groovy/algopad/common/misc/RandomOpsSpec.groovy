/*
 *  algopad.
 */

package algopad.common.misc

import spock.lang.Specification

class RandomOpsSpec extends Specification {

    def 'it should randomly shuffle an array'() {

        given:
        items = items as Integer[]
        def rnd = new Random(seed)

        when:
        use(RandomOps) {
            rnd.shuffle items
        }

        then:
        items == shuffled as Integer[]

        where:
        seed | items   | shuffled
        100  | (1..20) | [16, 4, 13, 20, 15, 12, 17, 18, 1, 7, 3, 10, 14, 19, 5, 2, 11, 8, 9, 6]

    }
}

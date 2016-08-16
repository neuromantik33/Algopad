package algopad.common.misc

import spock.lang.Specification
import spock.lang.Unroll

import static algopad.common.misc.Ints.hammingDistance

class IntsSpec extends Specification {

    @Unroll
    def 'it should calculate the hamming distance #dist for #x and #y'() {

        expect:
        hammingDistance(x, y) == dist

        where:
        x          | y          | dist
        0b1011101  | 0b1001001  | 2
        0b10101010 | 0b01010101 | 8
        331882123  | 996630289  | 14

    }
}

/*
 *  algopad.
 */

package algopad.common.ds

import spock.lang.Shared
import spock.lang.Specification

import static java.lang.Integer.MAX_VALUE

class BitArraySpec extends Specification {

    @Shared
    def random = new Random()

    def 'it should support basic bitwise operations similar to bitset'() {

        given:
        def bits = new BitArray(length)

        expect:
        bits.length == length

        when:
        bits[idx] = true

        then:
        bits[idx]
        bits.cardinality == 1

        when:
        bits[idx] = false

        then:
        !bits[idx]
        bits.cardinality == 0

        when:
        bits.flip idx

        then:
        bits[idx]
        bits.cardinality == 1

        when:
        bits.flip idx

        then:
        !bits[idx]
        bits.cardinality == 0

        where:
        length = random.nextInt(MAX_VALUE)
        idx = random.nextInt(length)

    }
}

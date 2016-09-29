/*
 *  algopad.
 */

package algopad.common.misc

import spock.lang.Shared
import spock.lang.Specification

import static algopad.common.misc.Bits.randomBitSet

class BitsSpec extends Specification {

    @Shared
    def random = new Random()

    def 'it should generate a random number of bits'() {

        when:
        def bits = randomBitSet(numBits, new Random(seed))
        def ref = new BigInteger(numBits, new Random(seed))

        then:
        bits.toByteArray() == ref.toByteArray()
        bits.cardinality() == ref.bitCount()

        where:
        seed = random.nextLong()
        numBits = random.nextInt(100)

    }
}
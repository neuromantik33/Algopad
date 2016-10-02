/*
 *  algopad.
 */

package algopad.common.misc

import spock.lang.Shared
import spock.lang.Specification

import static algopad.common.misc.Bits.randomBitArray
import static algopad.common.misc.Bits.randomBitSet

class BitsSpec extends Specification {

    @Shared
    def random = new Random()

    def 'it should generate a random number of bits'() {

        when:
        def bitSet = randomBitSet(numBits, new Random(seed))
        def bitAry = randomBitArray(numBits, new Random(seed))
        def bigInt = new BigInteger(numBits, new Random(seed))

        then:
        bitSet.toByteArray() == bigInt.toByteArray()
        bitAry.bytes == bigInt.toByteArray()
        bitSet.cardinality() == bigInt.bitCount()
        bitAry.cardinality == bigInt.bitCount()

        where:
        seed = random.nextLong()
        numBits = random.nextInt(100)

    }
}
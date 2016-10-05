/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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
        def bigInt = new BigInteger(numBits, new Random(seed))
        def bitSet = randomBitSet(numBits, new Random(seed))
        def bitAry = randomBitArray(numBits, new Random(seed))

        then:
        bitSet.toByteArray() == bigInt.toByteArray()
        bitAry.toByteArray() == bigInt.toByteArray()
        bitSet.cardinality() == bigInt.bitCount()
        bitAry.cardinality == bigInt.bitCount()

        where:
        seed = random.nextLong()
        numBits = random.nextInt(100)

    }
}

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

package algopad.common.ds

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static java.lang.Short.MAX_VALUE

class BitArraySpec extends Specification {

    @Shared
    def random = new Random()

    def 'it should throw an error when trying to create bit arrays of negative length'() {

        when:
        new BitArray(0)

        then:
        notThrown IllegalArgumentException

        when:
        new BitArray(-1)

        then:
        thrown IllegalArgumentException

        when:
        new BitArray(-1, '123'.bytes)

        then:
        thrown IllegalArgumentException

    }

    def 'it should throw an error if the length is insufficient to support byte array'() {

        when:
        new BitArray(8, '1'.bytes)

        then:
        notThrown IllegalArgumentException

        when:
        new BitArray(16, '1'.bytes)

        then:
        thrown IllegalArgumentException

    }

    def 'it should support basic bitwise operations (getting, setting, flipping)'() {

        given:
        def length = random.nextInt(MAX_VALUE)
        def idx = random.nextInt(length)

        @Subject
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

    }
}

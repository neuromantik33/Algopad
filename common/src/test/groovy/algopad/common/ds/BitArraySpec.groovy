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

import static java.lang.Short.MAX_VALUE

class BitArraySpec extends Specification {

    @Shared
    def random = new Random()

    def 'it should support basic bitwise operations (getting, setting, flipping)'() {

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

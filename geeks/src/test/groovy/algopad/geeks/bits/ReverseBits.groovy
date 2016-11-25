/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
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

package algopad.geeks.bits

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/write-an-efficient-c-program-to-reverse-bits-of-a-number')
class ReverseBits extends Specification {

    @Subject
    def reverseBits = { long l ->
        long reverse = 0L
        def addOne = { if ((l & 1) == 1) { reverse += 1 } }
        addOne()
        31.times {
            l >>= 1
            reverse <<= 1
            addOne()
        }
        reverse
    }

    @Unroll
    def 'it should reverse the bits of a 32 bit unsigned integer #i'() {

        def toBinary = Long.&toBinaryString

        setup:
        println "input : ${toBinary(i)}"

        expect:
        reverseBits(i) == reverse

        cleanup:
        println "output : ${toBinary(reverse)}\n"

        where:
        i          | reverse
        0          | 0L
        3          | 3221225472
        345        | 2592079872
        4294967295 | 4294967295

    }
}

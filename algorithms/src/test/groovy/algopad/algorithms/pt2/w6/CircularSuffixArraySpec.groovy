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

package algopad.algorithms.pt2.w6

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.System.nanoTime
import static java.util.concurrent.TimeUnit.NANOSECONDS

class CircularSuffixArraySpec extends Specification {

    def 'it should throw an error if the string is null'() {

        when:
        new CircularSuffixArray(null)

        then:
        thrown NullPointerException

    }

    def 'it should throw an error if i is outside the prescribed range'() {

        given:
        def array = new CircularSuffixArray('a')

        when:
        array.index(i)

        then:
        thrown IndexOutOfBoundsException

        where:
        i << [-1, 1]

    }

    @Unroll
    def 'it should calculate the correct suffixes given the string #str'() {

        given:
        def array = new CircularSuffixArray(str)

        expect:
        indices.eachWithIndex { index, int i ->
            assert array.index(i) == index
        }

        where:
        str            | indices
        'ABRACADABRA!' | [11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2]
        'swiss miss'   | [5, 2, 7, 6, 4, 3, 8, 9, 0, 1]

    }

    @Unroll
    def 'it should not overflow the stack'() {

        when:
        new CircularSuffixArray(input)

        then:
        notThrown StackOverflowError

        where:
        input << ['1' * 1000, 'AB' * 500]

    }

    @Unroll
    def 'it should calculate the suffix array of the first #num characters of dickens.txt in reasonable time'() {

        given:
        def url = getClass().getResource(name)
        def input = url.text[0..num - 1]

        when:
        def start = nanoTime()
        def csa = new CircularSuffixArray(input)
        def end = nanoTime()

        then:
        def indices = getClass().getResource("$name-${num}.csa").readLines()
        num.times { i ->
            assert csa.index(i) == indices[i] as int
        }

        and:
        NANOSECONDS.toMillis(end - start) < time

        cleanup:
        println "Time taken : ${NANOSECONDS.toMillis(end - start)}ms"

        where:
        num    | time
        32000  | 50
        64000  | 100
        128000 | 100
        //        256000  | 100
        //        512000  | 200
        //        1024000 | 400
        //        2048000 | 1000
        //        4096000 | 2000

        name = 'textfiles/dickens.txt'

    }
}

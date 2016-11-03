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

import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GrEqualsBetweenInconvertibleTypes')
class CharRangeSpec extends Specification {

    def 'it should support basic range operations for an increasing range'() {

        when:
        def range = new CharRange('a' as char, 'h' as char)

        then:
        range == 'a'..'h'
        !range.reverse
        range.from == 'a'
        range.to == 'h'
        range.inspect() == 'a..h'
        range[1..5] == 'b'..'f'

    }

    def 'it should support basic range operations for an decreasing range'() {

        when:
        def range = new CharRange('Z' as char, 'P' as char)

        then:
        range == 'Z'..'P'
        range.reverse
        range.from == 'P'
        range.to == 'Z'
        range.inspect() == 'Z..P'
        range[0..3] == 'S'..'P' // Weird behaviour

    }

    def 'it should support stepping'() {

        given:
        def range = 'a'..<'z' as CharRange

        expect:
        range.step(5) == ['a', 'f', 'k', 'p', 'u']
        range.step(-6) == ['y', 's', 'm', 'g', 'a']

    }

    def 'it should throw an error when 0-stepping on an non-empty range'() {

        when:
        ('a'..'z' as CharRange).step(0)

        then:
        thrown RuntimeException

    }

    @Unroll
    def '"#c" #should be contained within a..z'() {

        given:
        def range = 'a'..'z' as CharRange

        expect:
        range.containsWithinBounds(c) == present

        where:
        c   | present
        's' | true
        '1' | false

        should = present ? 'should' : 'should not'

    }
}

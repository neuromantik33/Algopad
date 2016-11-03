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
import spock.lang.Subject

class PrefixMapSpec extends Specification {

    @Subject
    def map = new PrefixMap('a'..'z' as CharRange)

    def 'it should throw an error if the key or value is null'() {

        when:
        map[null] = 1

        then:
        thrown NullPointerException

        when:
        map[''] = null

        then:
        thrown NullPointerException

    }

    def 'it should support basic map operations (putting, getting, deletion)'() {

        when:
        map.a = 1

        then:
        map.size() == 1
        map.a == 1
        map == [a: 1]

        when:
        map.abc = 2

        then:
        map.size() == 2
        map.abc == 2
        map == [a: 1, abc: 2]

        when: 'Replacement'
        map.abc = 3

        then:
        map.size() == 2
        map.abc == 3
        map == [a: 1, abc: 3]

        when: 'Deletion'
        map.remove 'a'
        map.remove 'abc'

        then:
        map.isEmpty()
        map.a == null
        map.abc == null
        map == [:]

        when: 'Add a power set of first 4 letters'
        ('a'..'d')
          .subsequences()
          .eachWithIndex { seq, i ->
            map[seq.join()] = i
        }

        then:
        map.size() == 15

        when:
        map.clear()

        then:
        map.isEmpty()

    }

    def 'it should support prefix operations (entries, keys)'() {

        def keys = { map.entriesWithPrefix(it)*.key }

        given:
        ['apple', 'yellow', 'black', 'blue']
          .eachWithIndex { word, i ->
            map[word] = i
        }

        expect:
        keys('app') == ['apple']
        keys('yel') == ['yellow']
        keys('bl') == ['black', 'blue']

    }
}

/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

package algopad.interviewbit.hash

import groovy.transform.EqualsAndHashCode
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/anagrams')
class FindAnagrams extends Specification {

    @Subject
    def findAnagrams = { List strings ->
        int n = strings.size()
        def map = [:] as Map<Histogram, List>
        def buildKey = { String s ->
            def start = 'a' as char
            def key = new Histogram()
            s.chars.each { key.counts[it - start] += 1 }
            key
        }
        for (int i = 0; i < n; i++) {
            def key = buildKey(strings[i] as String)
            def indices = map.get(key, [])
            indices << i + 1
        }
        map.values() as List
    }

    @EqualsAndHashCode
    class Histogram {
        int[] counts = new int[26]
    }

    @Unroll
    def '''given an array of strings #strings, it should return all groups of strings
           that are anagrams. It should represent a group by a list of integers #indices
           representing the index in the original list.'''() {

        expect:
        findAnagrams(strings) == indices

        where:
        strings                      | indices
        ['cat', 'dog', 'god', 'tca'] | [[1, 4], [2, 3]]

    }
}

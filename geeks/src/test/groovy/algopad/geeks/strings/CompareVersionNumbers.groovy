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

package algopad.geeks.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/compare-two-version-numbers')
class CompareVersionNumbers extends Specification {

    @Subject
    def compareVersions = { String v1, String v2 ->
        def pts1 = v1.split(/\./) as List
        def pts2 = v2.split(/\./) as List
        def normalizeParts = { ->
            def len1 = pts1.size(), len2 = pts2.size()
            if (len1 != len2) {
                int diff = (len1 - len2).abs()
                def smaller = len1 > len2 ? pts2 : pts1
                diff.times { smaller << '0' }
            }
            assert pts1.size() == pts2.size()
        }
        normalizeParts()
        for (int i = 0; i < pts1.size(); i++) {
            def pt1 = pts1[i] as BigInteger
            def pt2 = pts2[i] as BigInteger
            int cmp = pt1 <=> pt2
            if (cmp != 0) { return cmp }
        }
        0
    }

    @Unroll
    def '#v1 should be #compare #v2'() {

        expect:
        compareVersions(v1, v2) == cmp

        where:
        v1                         | v2                          | cmp
        '0.1'                      | '1.1'                       | -1
        '1.2'                      | '1.2'                       | 0
        '1.13.4'                   | '1.13'                      | 1
        '01'                       | '1'                         | 0
        '4444371174137455'         | '5.168'                     | 1
        '444444444444444444444444' | '4444444444444444444444444' | -1
        '1.0'                      | '1'                         | 0

        //noinspection GroovyNestedConditional
        compare = cmp < 0 ? 'less than' : cmp > 0 ? 'greater than' : 'equal to'

    }
}

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

package algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w5.EditDistance.calculateEditDistance

@Narrative('''The edit distance between two strings is the minimum number of insertions, deletions,
              and mismatches in an alignment of two strings.''')
class EditDistanceSpec extends Specification {

    @Unroll
    def "it should compute the edit distance #distance of the 2 strings '#s' and '#t'"() {

        expect:
        calculateEditDistance(s, t) == distance

        where:
        s         | t          | distance
        'ab'      | 'ab'       | 0
        'short'   | 'ports'    | 3
        'editing' | 'distance' | 5

    }
}

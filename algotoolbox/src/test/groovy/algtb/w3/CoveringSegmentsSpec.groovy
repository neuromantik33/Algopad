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

package algtb.w3

import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.CoveringSegments.optimalPoints
import algtb.w3.CoveringSegments.Segment

class CoveringSegmentsSpec extends Specification {

    @Unroll
    def '''given a set of #n segments with integer coordinates on a line,
           find the minimum number #m of points such that each segment contains at least 1 point.'''() {

        given:
        segments = segments.collect { new Segment(it[0], it[1]) } as Segment[]

        expect:
        optimalPoints(segments) == points as int[]

        where:
        segments                         | points
        [[1, 3], [2, 5], [3, 6]]         | [3]
        [[4, 7], [1, 3], [2, 5], [5, 6]] | [3, 6]

        n = segments.size()
        m = points.size()

    }
}

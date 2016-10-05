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

package algtb.w4

import algtb.w4.PointsAndSegments.Segment
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w4.PointsAndSegments.countSegments

@Narrative('''You are organizing an online lottery. To participate, a person bets on a single
              integer. You then draw several ranges of consecutive integers at random.
              A participant’s payoff then is proportional to the number of ranges that
              contain the participant’s number minus the number of ranges that does not
              contain it. You need an efficient algorithm for computing the payoffs for all
              participants. A naive way to do this is to simply scan, for all participants, the
              list of all ranges. However, you lottery is very popular: you have thousands
              of participants and thousands of ranges. For this reason, you cannot afford
              a slow naive algorithm.''')
class PointsAndSegmentsSpec extends Specification {

    @Unroll
    def '''Given a set of points #points on a line and a set of #numSgmts segments on a line,
           it should compute, for each point, the number of segments that contain this point.'''() {

        given:
        segments = segments.collect { new Segment(it[0], it[1]) } as Segment[]
        points = points as int[]

        expect:
        countSegments(segments, points) == count as int[]

        where:
        segments                   | points         | count
        [[0, 5], [7, 10]]          | [1, 6, 11]     | [1, 0, 0]
        [[-10, 10]]                | [-100, 100, 0] | [0, 0, 1]
        [[0, 5], [-3, 2], [7, 10]] | [1, 6]         | [2, 0]
        [[2, 4]]                   | [4]            | [1]
        [[2, 4]]                   | [2]            | [1]
        [[-4, -2], [9, 11]]        | [14, 2, 30, 9] | [0, 0, 0, 1]

        numSgmts = segments.size()

    }
}

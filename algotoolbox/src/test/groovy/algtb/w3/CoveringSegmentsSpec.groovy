/*
 *  algopad.
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

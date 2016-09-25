/*
 *  algopad.
 */

package algtb.w4

import algtb.w4.Closest.Point
import spock.lang.Narrative
import spock.lang.Specification

import static algtb.w4.Closest.minimalDistance

@Narrative('''In this problem, your goal is to find the closest pair of points among the given n points.
              This is a basic primitive in computational geometry having applications in, for example,
              graphics, computer vision, traffic-control systems.''')
class ClosestSpec extends Specification {

    def 'given n points on a plane, it should find the smallest distance between a pair of two (different) points'() {

        given:
        points = points.collect { new Point(*it) }

        expect:
        minimalDistance(points).trunc(6) == minDistance

        where:
        points                             | minDistance
        [[0, 0], [3, 4]]                   | 5.0d
        [[7, 7], [1, 100], [4, 8], [7, 7]] | 0.0d
        [[4, 4], [-2, -2], [-3, -4],
         [-1, 3], [2, 3], [-4, 0],
         [1, 1], [-1, -1], [3, -1],
         [-4, 2], [-2, 4]]                 | 1.414213d

    }
}

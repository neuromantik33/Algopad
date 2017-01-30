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

package algopad.geeks.hash

import groovy.transform.Immutable
import groovy.transform.ToString
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Double.NEGATIVE_INFINITY
import static java.lang.Double.POSITIVE_INFINITY

@See('http://www.geeksforgeeks.org/count-maximum-points-on-same-line')
class MaxPointsOnLine extends Specification {

    @Immutable
    @ToString(includePackage = false)
    class Point implements Comparable<Point> {
        int x, y

        @Override
        int compareTo(final Point o) {
            int cmp = y <=> o.y
            if (cmp == 0) {
                cmp = x <=> o.x
            }
            cmp
        }
    }

    @Subject
    def maxPointsOnLine = { List pts ->
        int n = pts.size()
        if (n == 1) { return 1 }
        def calculateSlope = { Point p1, Point p2 ->
            double slope
            if (p1.x == p2.x) {
                slope = p1.y == p2.y ? NEGATIVE_INFINITY : POSITIVE_INFINITY
            } else if (p1.y == p2.y) {
                slope = 0.0d
            } else {
                slope = (p2.y - p1.y) / (p2.x - p1.x)
            }
            slope
        }
        int maxCnt = 0
        def updateMax = { maxCnt = Math.max(maxCnt, it) }
        def points = pts.collect { new Point(x: it[0], y: it[1]) }.sort()
        for (int i = 0; i < n; i++) {
            int duplicates = 0
            def slopes = [:] as Map<List, Integer>
            for (int j = i + 1; j < n; j++) {
                def slope = calculateSlope(points[i], points[j])
                if (slope == NEGATIVE_INFINITY) {
                    duplicates++
                } else {
                    int cnt = slopes.getOrDefault(slope, 1) + 1
                    slopes[slope] = cnt
                    updateMax cnt
                }
                // If there were duplicate points, update the total count
                // and each individual count per slope
                if (duplicates > 0) {
                    updateMax duplicates + 1
                    for (int count in slopes.values()) {
                        updateMax count + duplicates
                    }
                }
            }
        }
        maxCnt
    }

    @Unroll
    def '''given #n points on a 2D plane, it should return the maximum number of points "#max"
           that lie on the same straight line.'''() {

        expect:
        maxPointsOnLine(points) == max

        where:
        points                                                         | max
        [[20, 13]]                                                     | 1
        [[1, 1], [1, 1]]                                               | 2
        [[1, 1], [2, 2]]                                               | 2
        [[-6, -17], [5, -16], [-18, -17], [2, -4], [5, -13], [-2, 20]] | 2
        [[-1, 1], [0, 0], [1, 1], [2, 2], [3, 3], [3, 4]]              | 4
        [[-1, 1], [0, 0], [1, 1], [2, 2], [3, 3], [3, 3], [3, 4]]      | 5

        n = points.size()

    }
}

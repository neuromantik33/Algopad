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

package algopad.algorithms.pt1.w3

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.Double.NEGATIVE_INFINITY
import static java.lang.Double.POSITIVE_INFINITY
import static java.util.Arrays.sort
import static java.util.Collections.shuffle

class PointSpec extends Specification {

    def 'it should throw an error if compareTo is invoked with null'() {

        when:
        new Point(1, 1).compareTo(null)

        then:
        thrown(NullPointerException)

    }

    /**
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     */
    def 'it should compare points by their y-coordinates, breaking ties by their x-coordinates'() {

        def p0 = new Point(x0, y0)
        def p1 = new Point(x1, y1)

        expect:
        p0.compareTo(p1) == result

        where:
        x0 | y0 | x1 | y1 | result
        0  | 0  | 0  | 0  | 0
        1  | 2  | 0  | 3  | -1
        2  | 4  | 3  | 3  | 1
        1  | 5  | 2  | 5  | -1
        2  | 5  | 1  | 5  | 1
        5  | 5  | 5  | 5  | 0

    }

    /**
     * The slope is given by the formula (y1-y0) / (x1-x0).
     * Treat the slope of a horizontal line segment as positive zero.
     * Treat the slope of a vertical line segment as positive infinity.
     * Treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
     */
    @Unroll
    def 'should return the slope between the invoking point (#x1, #y1) and the argument point (#x2, #y2)'() {

        def p0 = new Point(x0, y0)
        def p1 = new Point(x1, y1)

        expect:
        p0.slopeTo(p1).round(5) == result

        where:
        x0 | y0 | x1 | y1 | result
        0  | 0  | 0  | 0  | NEGATIVE_INFINITY
        5  | 2  | 5  | 7  | POSITIVE_INFINITY
        4  | 3  | 9  | 3  | +0.0D
        5  | 3  | 15 | 7  | 0.4D
        5  | 6  | 15 | 2  | -0.4D
        10 | 5  | 5  | 10 | -1.0D
        8  | 12 | 5  | 5  | 2.33333D

    }

    /**
     * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 ? y0) / (x1 ? x0)
     * is less than the slope (y2 ? y0) / (x2 ? x0). Treat horizontal, vertical, and degenerate line segments as
     * in the slopeTo() method.
     */
    def 'it should return a comparator that compares its two argument points by the slopes with the point'() {

        def p0 = new Point(*p)
        def p1 = new Point(*q)
        def p2 = new Point(*r)

        expect:
        p0.slopeOrder().compare(p1, p2) == result

        where:
        p      | q       | r      | result
        [0, 0] | [1, 1]  | [2, 2] | 0
        [0, 0] | [0, 1]  | [0, 2] | 0
        [0, 0] | [1, 0]  | [2, 0] | 0
        [0, 0] | [10, 0] | [3, 3] | -1
        [0, 0] | [0, 10] | [3, 3] | 1
        [1, 1] | [5, 2]  | [3, 4] | -1
        [1, 1] | [3, 4]  | [5, 2] | 1

    }

    def 'it should sort the following points by slope'() {

        given:
        def p0 = new Point(*p)
        def unsorted = points.clone()
        shuffle(unsorted)

        when:
        points = points as Point[]
        unsorted = unsorted as Point[]
        sort(unsorted, p0.slopeOrder())

        then:
        unsorted == points

        where:
        p      | points
        [0, 0] | [[10, 0], [9, 1], [8, 2], [7, 3], [6, 4], [5, 5], [4, 6], [3, 7], [2, 8], [1, 9], [0, 10]].collect { x, y -> new Point(x, y) }

    }
}

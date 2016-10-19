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

package algopad.algorithms.pt1.w5

import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification
import spock.lang.Unroll

class PointSETSpec extends Specification {

    @Unroll
    def 'it should throw an error if any argument to "#method" is null'() {

        given:
        def set = new PointSET()

        when:
        set."$method"(null)

        then:
        thrown(NullPointerException)

        where:
        method << ['insert', 'contains', 'range', 'nearest']

    }

    def 'it should support some basic operations'() {

        given:
        def set = new PointSET()

        expect:
        set.empty
        set.size() == 0

        when:
        set.insert new Point2D(2, 2)

        then:
        !set.empty
        set.size() == 1
        set.contains new Point2D(2, 2)

        when:
        set.insert new Point2D(3, 2)

        then:
        !set.empty
        set.size() == 2
        set.contains new Point2D(3, 2)

    }

    @Unroll
    def 'it should support range search in linearithmic time for #input'() {

        given:
        def set = parsePointsFile("${input}.txt")

        when:
        def rect = new RectHV(*rectangle)
        def range = set.range(rect)
        def expected = points.collect { new Point2D(*it) }

        then:
        range.size() == expected.size()
        range.sort() == expected

        where:
        input      | rectangle                 | points
        'circle10' | [0.0, 0.0, 1.0, 0.095500] | [[0.5, 0.0], [0.206107, 0.095492], [0.793893, 0.095492]]
        'circle10' | [0.5, 0.5, 1.0, 0.654600] | [[0.975528, 0.654508]]

    }

    @Unroll
    def 'it should support nearest neighbor search in linear time for #input'() {

        given:
        def set = parsePointsFile("${input}.txt")

        when:
        def nearest = set.nearest(new Point2D(*point))

        then:
        nearest == new Point2D(*neighbor)

        where:
        input      | point      | neighbor
        'circle10' | [0.0, 0.0] | [0.206107, 0.095492]
        'circle10' | [0.0, 1.0] | [0.206107, 0.904508]
        'circle10' | [1.0, 0.0] | [0.793893, 0.095492]
        'circle10' | [1.0, 1.0] | [0.793893, 0.904508]

    }

    PointSET parsePointsFile(String name) {
        getClass()
          .getResource(name)
          .withReader { reader ->
            def set = new PointSET()
            reader.eachLine {
                def line = it.trim()
                if (line) {
                    def (x, y) = line.split()
                    set.insert new Point2D(x as double, y as double)
                }
            }
            set
        }
    }
}

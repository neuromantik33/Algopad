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

class BruteCollinearPointsSpec extends Specification {

    def 'it should throw an error if points are null at construction'() {

        when:
        new BruteCollinearPoints(points)

        then:
        thrown(NullPointerException)

        where:
        points << [null, [null] as Point[]]

    }

    def 'it should throw an error if any points are duplicated'() {

        when:
        new BruteCollinearPoints([new Point(1, 1),
                                  new Point(0, 0),
                                  new Point(0, 0)] as Point[])

        then:
        thrown(IllegalArgumentException)

    }

    def 'it should return 0 segments if there are less than 4 points at construction'() {

        given:
        def points = parsePointsFile("${input}.txt")

        when:
        def brute = new BruteCollinearPoints(points)

        then:
        brute.numberOfSegments() == 0
        brute.segments() as List == []

        where:
        input << ['input1', 'input2', 'input3']

    }

    def pt(x, y) { new Point(x, y) }

    def sgmt(p, q) { new LineSegment(p, q) }

    @Unroll
    def 'it should return #n segments for the given #input file'() {

        given:
        def points = parsePointsFile("${input}.txt")

        when:
        def brute = new BruteCollinearPoints(points)

        then:
        brute.numberOfSegments() == n
        "${brute.segments()}" == "$segments"

        where:
        input     | n | segments
        'input5'  | 1 | [sgmt(pt(14000, 10000), pt(32000, 10000))]
        'input8'  | 2 | [sgmt(pt(10000, 0), pt(0, 10000)),
                         sgmt(pt(3000, 4000), pt(20000, 21000))]
        'input40' | 4 | [sgmt(pt(1000, 17000), pt(29000, 17000)),
                         sgmt(pt(1000, 17000), pt(1000, 31000)),
                         sgmt(pt(2000, 24000), pt(25000, 24000)),
                         sgmt(pt(2000, 29000), pt(28000, 29000))]

    }

    Point[] parsePointsFile(String name) {
        getClass()
          .getResource(name)
          .withReader { reader ->
            def size = reader.readLine() as int
            def points = []
            reader.eachLine(1) {
                def line = it.trim()
                if (line) {
                    def (x, y) = line.split()
                    points << new Point(x as int, y as int)
                }
            }
            points.toArray(new Point[size])
        }
    }
}

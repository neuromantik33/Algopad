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

import algtb.w4.Closest.Point
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w4.Closest.minimalDistance
import static java.util.concurrent.TimeUnit.MILLISECONDS

@Narrative('''In this problem, your goal is to find the closest pair of points among the given n points.
              This is a basic primitive in computational geometry having applications in, for example,
              graphics, computer vision, traffic-control systems.''')
class ClosestSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given #n points on a plane, it should find the smallest distance #distance between a pair of two (different) points'() {

        given:
        points = points.collect { new Point(*it) }

        expect:
        minimalDistance(points).trunc(6) == distance

        cleanup:
        println "Time spent = ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        points                                      | distance
        [[0, 0], [3, 4]]                            | 5.0d
        [[7, 7], [1, 100], [4, 8], [7, 7]]          | 0.0d
        [[0, 0], [0, 1], [100, 45], [2, 3], [9, 9]] | 1.0d
        [[0, 0], [-4, 1], [-7, -2], [4, 5], [1, 1]] | 1.414213d
        [[4, 4], [-2, -2], [-3, -4], [-1, 3],
         [2, 3], [-4, 0], [1, 1], [-1, -1],
         [3, -1], [-4, 2], [-2, 4]]                 | 1.414213d

        n = points.size()

    }

    def 'it should find the smallest pairwise distance between a very large set of points'() {

        given:
        def max = Integer.MAX_VALUE
        def rnd = new Random(seed)
        def points = []
        size.times {
            points << new Point(rnd.nextInt(max), rnd.nextInt(max))
        }

        expect:
        minimalDistance(points).trunc(6) == distance

        cleanup:
        println "Time spent = ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        seed | size   | distance
        0L   | 100000 | 11081.939541d

    }
}

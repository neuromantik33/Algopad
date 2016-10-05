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

package algopad.algdsgn2.w4

import algopad.common.graph.Graph
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static FloydMarshall.calculateShortestPathsFor
import static java.lang.Integer.MAX_VALUE
import static java.lang.Math.min
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FloydMarshallSpec extends Specification {

    @Rule
    Stopwatch timer = new Stopwatch() {}

    @Unroll
    def 'it should calculate the shortest path for all vertices in #file'() {

        given:
        def input = FloydMarshallSpec.class.getResource(file)
        def graph = new Graph(input)

        when:
        def shortestPaths = calculateShortestPathsFor(graph)
        def len = shortestPaths.length

        and:
        def minSP = MAX_VALUE
        len.times { i ->
            len.times { j ->
                minSP = min(minSP, shortestPaths[i][j].dist)
            }
        }

        then:
        minSP == shortestSP

        cleanup:
        println "Running time = ${timer.runtime(MILLISECONDS)}ms"

        where:
        file              | shortestSP
        'graph_test1.txt' | -10003
        'graph_test2.txt' | -6
        'graph_test3.txt' | -4
        // SLOW: 'g3.txt'          | -19

    }

    @Unroll
    def 'it should detect a negative cycle path in #file'() {

        given:
        def input = FloydMarshallSpec.class.getResource(file)
        def graph = new Graph(input)

        when:
        calculateShortestPathsFor(graph)

        then:
        thrown(NegativeCycleException)

        cleanup:
        println "Running time = ${timer.runtime(MILLISECONDS)}ms"

        where:
        file << [
          'graph_negative_cycle.txt'
          // SLOW: 'g1.txt',
          // SLOW: 'g2.txt'
        ]

    }
}

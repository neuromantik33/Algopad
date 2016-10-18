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

package algopad.algorithms.pt2.w1

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdRandom
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class SAPSpec extends Specification {

    @Shared
    def wordnetSAP = new SAP(parseDigraphFile('digraph-wordnet.txt'))

    @Unroll
    def '#method () should throw a error if any argument is null'() {

        when:
        new SAP(null)

        then:
        thrown NullPointerException

        when:
        def sap = new SAP(new Digraph(10))
        sap."$method"([], null)

        then:
        thrown NullPointerException

        when:
        sap."$method"(null, [])

        then:
        thrown NullPointerException

        where:
        method << ['length', 'ancestor']

    }

    @Unroll
    def '#method () should throw a error if any argument vertex is invalid'() {

        given:
        def sap = new SAP(new Digraph(5))

        when:
        sap."$method"(0, 5)

        then:
        thrown IndexOutOfBoundsException

        when:
        sap."$method"(5, 0)

        then:
        thrown IndexOutOfBoundsException

        where:
        method << ['ancestor', 'length']

    }

    @Unroll
    def 'it should calculate the ancestor and length for #input'() {

        given:
        def graph = parseDigraphFile("${input}.txt")

        when:
        def sap = new SAP(graph)

        then:
        sap.length(v, w) == length
        sap.ancestor(v, w) == ancestor

        where:
        input             | v     | w     | length | ancestor
        'digraph1'        | 3     | 3     | 0      | 3
        'digraph1'        | 3     | 11    | 4      | 1
        'digraph1'        | 9     | 12    | 3      | 5
        'digraph1'        | 7     | 2     | 4      | 0
        'digraph1'        | 1     | 6     | -1     | -1
        'digraph2'        | 1     | 5     | 2      | 0
        'digraph2'        | 5     | 5     | 0      | 5
        'digraph3'        | 6     | 6     | 0      | 6
        'digraph3'        | 8     | 13    | 5      | 8
        'digraph4'        | 4     | 1     | 3      | 4
        'digraph5'        | 14    | 21    | 8      | 21
        'digraph6'        | 7     | 7     | 0      | 7
        'digraph9'        | 7     | 0     | 2      | 6
        'digraph9'        | 0     | 3     | 1      | 0
        'digraph-wordnet' | 54621 | 25986 | 12     | 81004

    }

    @Unroll
    def 'it should calculate the ancestor and length for the wordnet digraph'() {

        expect:
        wordnetSAP.length(v, w) == distance

        where:
        v              | w              | distance
        [51606, 58991] | [37612, 64964] | 11

    }

    def 'it should handle random values in a small amount of time'() {

        given:
        def tries = 100000
        def graph = wordnetSAP.graph
        def start = System.currentTimeMillis()

        expect:
        def times = []
        tries.times {

            def v = StdRandom.uniform(graph.V())
            def w = StdRandom.uniform(graph.V())
            def now = System.nanoTime()
            wordnetSAP.ancestor(v, w)
            wordnetSAP.length(v, w)
            times << (System.nanoTime() - now)

        }


        cleanup:
        def sum = 0
        times.each { sum += it }
        println "Average time is ${sum / times.size()}ns"
        println "Total time for $tries tries is ${System.currentTimeMillis() - start}ms"

    }

    Digraph parseDigraphFile(String name) {
        def input = new In(getClass().getResource(name))
        def graph = new Digraph(input)
        input.close()
        graph
    }
}

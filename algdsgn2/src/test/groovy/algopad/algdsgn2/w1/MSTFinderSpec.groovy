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

package algopad.algdsgn2.w1

import algopad.common.graph.Graph
import spock.lang.Specification
import spock.lang.Unroll

import static MSTFinder.findMST

class MSTFinderSpec extends Specification {

    @Unroll
    def 'it should calculate total weight for the MST of the graph defined in #file'() {

        given:
        def input = MSTFinderSpec.class.getResource(file)
        def graph = new Graph(input)

        when:
        def mst = findMST(graph)

        then:
        mst.inject(0) { weight, edge -> weight + edge.weight } == weight

        where:
        file = 'edges.txt'
        weight = -3612829

    }
}

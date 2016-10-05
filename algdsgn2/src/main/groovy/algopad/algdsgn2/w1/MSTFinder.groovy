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

import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex
import groovy.transform.CompileStatic

import static algopad.common.misc.Closures.negate

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class MSTFinder {

    /**
     * Calculate the Minimum Spanning Tree (MST) given a weighted {@link Graph}.
     * This algorithm is not optimized for dense graphs where the number of edges
     * is much greater than the number of vertices.
     *
     * @return the list of {@link Edge}s comprising the MST.
     */
    static Iterable<Edge> findMST(Graph graph) {

        def visited = new boolean[graph.numVertices]
        def wasVisited = { Vertex vtx -> visited[vtx.id] }
        def notYetVisited = negate(wasVisited)

        def byWeight = { Edge o1, Edge o2 -> o1.weight <=> o2.weight }
        def heap = new PriorityQueue<Edge>(graph.numEdges, byWeight as Comparator)

        def visit = { Vertex vtx ->
            if (wasVisited(vtx)) { return }
            visited[vtx.id] = true
            vtx.edges
               .each { edge ->
                if (notYetVisited(edge.other(vtx))) {
                    heap.add edge
                }
            }
        }

        visit graph.vertices[0]

        def mst = []
        while (!heap.empty) {
            def edge = heap.poll()
            if (notYetVisited(edge.v) || notYetVisited(edge.w)) {
                mst << edge
                visit edge.v
                visit edge.w
            }
        }
        mst
    }
}

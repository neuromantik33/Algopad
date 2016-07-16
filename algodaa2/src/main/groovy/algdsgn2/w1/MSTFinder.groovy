/*
 *  algopad.
 */

package algdsgn2.w1

import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex
import groovy.transform.CompileStatic

@CompileStatic
class MSTFinder {

    static Iterable<Edge> findMST(Graph graph) {

        def seen = new boolean[graph.numVertices + 1]
        def notYetSeen = { Vertex vtx -> !seen[vtx.id] }

        def edgesByWeight = { Edge o1, Edge o2 -> o1.weight <=> o2.weight }
        def heap = new PriorityQueue<Edge>(graph.numEdges, edgesByWeight as Comparator)

        def visit = { Vertex vtx ->
            if (!notYetSeen(vtx)) { return }
            seen[vtx.id] = true
            vtx.edges.each { edge ->
                if (notYetSeen(edge.other(vtx))) {
                    heap.add edge
                }
            }
        }

        def mst = []

        visit graph.vertices[1]
        while (!heap.empty) {
            def edge = heap.poll()
            if (notYetSeen(edge.v) || notYetSeen(edge.w)) {
                mst << edge
                visit edge.v
                visit edge.w
            }
        }

        mst

    }
}

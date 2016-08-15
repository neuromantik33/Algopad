/*
 *  algopad.
 */

package algdsgn2.w2

import algopad.common.ds.DisjointSet
import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex
import groovy.transform.CompileStatic

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class Clustering {

    /**
     * Creates <i>numClusters</i> clusters for the given <i>graph</i>, grouping vertices by their minimum weight.
     *
     * @return a list of all crossing edges between the remaining clusters.
     */
    static List<Edge> calculateSingleLinkClusteringFor(Graph graph, int numClusters) {

        def byWeight = { Edge o1, Edge o2 -> o1.weight <=> o2.weight }
        def heap = new PriorityQueue<Edge>(graph.numEdges, byWeight as Comparator)
        def set = new DisjointSet<Vertex>(graph.numVertices)

        def processCrossingEdge = { Closure closure ->
            while (!heap.empty) {
                def edge = heap.poll()
                if (set.connected(edge.v, edge.w)) { continue }
                if (!closure.call(edge)) { return }
            }
        }

        // Initialize the heap and disjoint sets
        def edges = [] as Set
        graph.vertices.each { Vertex vertex ->
            set.add vertex
            vertex.edges.each { Edge edge ->
                if (edge in edges) { return }
                heap << edge
                edges << edge
            }
        }

        // Merge points until K clusters are present
        processCrossingEdge { Edge edge ->
            set.union edge.v, edge.w
            set.size > numClusters
        }

        // Iterate over all remaining crossing edges retaining the smallest for each cluster
        Map<Set<Vertex>, Edge> crossingEdges = [:]
        processCrossingEdge { Edge edge ->
            def key = [set.find(edge.v), set.find(edge.w)] as Set
            def existingEdge = crossingEdges[key]
            if (!existingEdge || edge.weight < existingEdge.weight) {
                crossingEdges[key] = edge
            }
            true // Continue processing all edges
        }

        crossingEdges.values()
                     .sort(byWeight)

    }
}

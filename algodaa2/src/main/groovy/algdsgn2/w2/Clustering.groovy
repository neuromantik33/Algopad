/*
 *  algopad.
 */

package algdsgn2.w2

import algopad.common.ds.DisjointSet
import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex
import algopad.common.misc.Ints.HammingCalculator
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

        // Gather the remaining crossing edges and return the sorted list
        def crossingEdges = []
        processCrossingEdge { Edge edge -> crossingEdges << edge }
        crossingEdges.sort byWeight

    }

    /**
     * Counts the number of hamming clusters for the arg <i>ints</i>
     * with hamming distance <= <i>maxDistance</i>.<br>
     * The number of clusters in determined in the following way:
     * <ul>
     *     <li>For each integer it calculates every possible value with hamming distance <= maxDistance</li>
     *     <li>For each calculated value, it checks to see if it is in the original input</li>
     *     <li>If present, it "connects" both integers using a {@link DisjointSet}</li>
     *     <li>When the algorithm terminates, the disjoint set will have all the values with
     *     hamming distance <= maxDistance within the same cluster</i>.
     * </ul>
     *
     * @return
     */
    static int countHammingClustersFor(int[] ints, int maxDistance) {

        // Create all hamming calculators for all hamming distances 1..maxDistance
        List<HammingCalculator> calculators = []
        for (int distance = maxDistance; distance > 0; distance--) {
            calculators << new HammingCalculator(distance, 24)
        }

        def set = new DisjointSet(ints.length, false)
        def indices = [:] as Map<Integer, Integer>
        ints.eachWithIndex { int num, int ix ->
            if (!indices.containsKey(num)) {
                indices[num] = ix
                set << ix
            }
        }

        // Calculates all hamming "neighbors" with distance <= maxDistance
        def neighborsFor = { int num ->
            def neighbors = [] as List<Integer>
            calculators.each { HammingCalculator hc ->
                neighbors.addAll hc.neighborsFor(num)
            }
            neighbors
        }

        for (def entry in indices.entrySet()) {

            int num = entry.key
            int numIx = entry.value

            neighborsFor(num)
              .findAll { indices.containsKey it }
              .each { set.union numIx, indices[it] as int }
        }

        set.size

    }
}

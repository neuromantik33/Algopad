/*
 *  algopad.
 */

package algdsgn2.w4

import algopad.common.graph.Graph
import algopad.common.graph.Vertex
import groovy.transform.CompileStatic

import static algopad.common.misc.Matrices.initMatrix
import static groovyx.gpars.GParsPool.withPool
import static java.lang.Math.min

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings([
  'GroovyParameterNamingConvention',
  'GroovyLocalVariableNamingConvention'
])
class FloydMarshall {

    /**
     * Calculates the all-pairs shortest paths for the input <i>graph</i>.
     *
     * @return a 2D matrix of {@link ShortestPath}s indexed by the vertex ids,
     * ex. for the shortest distance from VI to VJ, it will be located at paths[i][j].
     */
    static def calculateShortestPathsFor(final Graph graph) {

        def V = graph.numVertices
        def vertices = graph.vertices
        def paths = new ShortestPath[V][V]

        // Initialize the path matrix
        initMatrix(paths) { row, col ->
            def path = new ShortestPath()
            if (row == col) {
                path.dist = 0
            }
            path
        }

        vertices.each { Vertex vx ->
            vx.edges.each {
                paths[it.v.id][it.w.id].dist = it.weight
            }
        }

        // Using a GPars fork join pool for parallel execution
        withPool {
            for (int k = 1; k < V; k++) {
                vertices.eachParallel { Vertex src ->
                    calculateShortestPathFor(src, k, paths)
                }
            }
        }

        paths

    }

    /**
     * Given a <i>src</i> vertex and a maximum allowed intermediate vertex indexed at <i>k</i>,
     * calculates the shortest path and updates the <i>paths</i> matrix.
     */
    @CompileStatic
    private static calculateShortestPathFor(Vertex src, int k, ShortestPath[][] paths) {

        def i = src.id
        def ik = paths[i][k]

        // Don't bother proceeding if sub-path is undefined
        if (!ik.defined) { return }

        for (int j = 1; j < paths.length; j++) {
            def ij = paths[i][j]
            def kj = paths[k][j]
            if (kj.defined) {
                ij.dist = min(ij.dist, ik.dist + kj.dist)
            }
        }

        if (paths[i][i].dist < 0) {
            throw new NegativeCycleException()
        }
    }

    private static final int INFINITY = Integer.MAX_VALUE

    /**
     * Shortest path abstraction.
     *
     * TODO Augment with previous edge for path reconstruction.
     */
    static class ShortestPath {

        int dist = INFINITY

        boolean isDefined() {
            dist != INFINITY
        }
    }
}

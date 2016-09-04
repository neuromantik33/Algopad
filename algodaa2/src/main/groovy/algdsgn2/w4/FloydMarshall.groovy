/*
 *  algopad.
 */

package algdsgn2.w4

import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex

import static algopad.common.misc.Matrices.initMatrix
import static groovyx.gpars.GParsPool.withPool
import static java.lang.Math.min

/**
 * @author Nicolas Estrada.
 */
class FloydMarshall {

    /**
     * TODO
     *
     * @param graph
     * @return
     */
    @SuppressWarnings([
      'GroovyParameterNamingConvention',
      'GroovyLocalVariableNamingConvention'
    ])
    static def calculateShortestPathsFor(final Graph graph) {

        def V = graph.numVertices
        def vertices = graph.vertices
        def paths = new ShortestPath[V][V]

        initMatrix(paths) { row, col ->
            def path = new ShortestPath()
            if (row == col) {
                path.dist = 0
            }
            path
        }

        vertices.each { Vertex vx ->
            vx.edges
              .each { Edge edge ->
                paths[edge.v.id][edge.w.id].dist = edge.weight
            }
        }

        withPool {
            for (int k = 1; k < V; k++) {
                (0..<V).eachParallel { int i ->
                    def ik = paths[i][k]
                    if (ik.dist != INFINITY) {
                        for (int j = 1; j < V; j++) {
                            def kj = paths[k][j]
                            def ij = paths[i][j]
                            if (kj.dist != INFINITY) {
                                ij.dist = min(ij.dist, ik.dist + kj.dist)
                            }
                        }
                        if (paths[i][i].dist < 0) {
                            throw new NegativeCycleException()
                        }
                    }
                }
            }
        }

        paths

    }

    private static final int INFINITY = Integer.MAX_VALUE

    static class ShortestPath {
        int dist = INFINITY
    }
}

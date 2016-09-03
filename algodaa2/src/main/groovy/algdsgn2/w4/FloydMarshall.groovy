/*
 *  algopad.
 */

package algdsgn2.w4

import algopad.common.graph.Edge
import algopad.common.graph.Graph
import algopad.common.graph.Vertex

import static algopad.common.misc.Matrices.initMatrix
import static groovyx.gpars.GParsPool.withPool

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
        def infos = new PathInfo[V][V]

        initMatrix(infos) { row, col ->
            def info = new PathInfo()
            if (row == col) {
                info.dist = 0
            }
            info
        }

        vertices.each { Vertex vx ->
            vx.edges
              .each { Edge edge ->
                def info = infos[edge.v.id][edge.w.id]
                info.dist = edge.weight
                info.edgeTo = edge
            }
        }

        withPool {
            vertices.each { Vertex w ->
                vertices.eachParallel { Vertex u ->
                    def uw = infos[u.id][w.id]
                    if (uw.dist == INFINITY) {
                        return
                    }
                    vertices.each { Vertex v ->
                        def wv = infos[w.id][v.id]
                        if (wv.dist == INFINITY) {
                            return
                        }
                        def uv = infos[u.id][v.id]
                        relax(uv, uw, wv)
                    }
                    if (infos[u.id][u.id].dist < 0) {
                        throw new NegativeCycleException()
                    }
                }
            }
        }

        infos

    }

    private static relax(PathInfo path,
                         PathInfo subPath1,
                         PathInfo subPath2) {
        if (path.dist > subPath1.dist + subPath2.dist) {
            path.dist = subPath1.dist + subPath2.dist
            path.edgeTo = subPath2.edgeTo
        }
    }

    private static final int INFINITY = Integer.MAX_VALUE

    static class PathInfo {
        int dist = INFINITY
        Edge edgeTo
    }
}

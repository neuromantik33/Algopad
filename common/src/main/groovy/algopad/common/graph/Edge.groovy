/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic

@CompileStatic
class Edge {

    final Vertex v
    final Vertex w
    final int weight

    Edge(Vertex v, Vertex w, int weight) {
        this.v = v
        this.w = w
        this.weight = weight
    }
}

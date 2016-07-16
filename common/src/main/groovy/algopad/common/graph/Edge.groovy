/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false)
class Edge {

    final Vertex v
    final Vertex w
    final int weight

    Edge(Vertex v, Vertex w, int weight) {
        this.v = v
        this.w = w
        this.weight = weight
    }

    Vertex other(Vertex vertex) {
        assert vertex.is(v) || vertex.is(w)
        return vertex.is(v) ? w : v
    }
}

/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, excludes = 'edges')
class Vertex {

    final int id
    final List<Edge> edges = []

    Vertex(int id) {
        this.id = id
    }
}

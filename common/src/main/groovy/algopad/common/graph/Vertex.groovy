/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, excludes = 'edges')
class Vertex {

    int id
    def edges = []

}

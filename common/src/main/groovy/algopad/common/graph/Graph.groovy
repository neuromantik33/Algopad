/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic

@CompileStatic
class Graph {

    int numV, numE
    Vertex[] vertices

    Graph(URL url) {
        parseAdjacencyList url
    }

    private parseAdjacencyList(URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            numV = scanner.nextInt()
            numE = scanner.nextInt()
            println "V = $numV, E = $numE"
            vertices = new Vertex[numV + 1] // one-indexed
            numE.times {

                def vId = scanner.nextInt()
                def v = vertices[vId]
                if (!v) {
                    v = new Vertex(id: vId)
                    vertices[vId] = v
                }

                def wId = scanner.nextInt()
                def w = vertices[wId]
                if (!w) {
                    w = new Vertex(id: wId)
                    vertices[wId] = w
                }

                def weight = scanner.nextInt()

            }
        }
    }
}

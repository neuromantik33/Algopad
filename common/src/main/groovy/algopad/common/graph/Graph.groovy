/*
 *  algopad.
 */

package algopad.common.graph

import groovy.transform.CompileStatic

@CompileStatic
@SuppressWarnings('GroovyLocalVariableNamingConvention')
class Graph {

    Vertex[] vertices

    Graph(URL url) {
        parseAdjacencyList url
    }

    private parseAdjacencyList(URL url) {
        url.withReader { reader ->

            def scanner = new Scanner(reader)
            def numV = scanner.nextInt()
            vertices = new Vertex[numV + 1] // one-indexed

            def numE = scanner.nextInt()
            numE.times {

                def v = parseVertex(scanner)
                def w = parseVertex(scanner)

                def weight = scanner.nextInt()
                def edge = new Edge(v, w, weight)
                v.edges << edge
                w.edges << edge

            }
        }
    }

    private Vertex parseVertex(Scanner scanner) {
        def id = scanner.nextInt()
        def vertex = vertices[id]
        if (!vertex) {
            vertex = new Vertex(id)
            vertices[id] = vertex
        }
        vertex
    }

    int getNumVertices() {
        vertices.length - 1
    }

    int getNumEdges() {
        def edges = vertices.tail().inject(0) { total, v ->
            total + v.edges.size()
        } as int
        // Divide by 2 since each edge is present twice
        edges >> 1
    }
}

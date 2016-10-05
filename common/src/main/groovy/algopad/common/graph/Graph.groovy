/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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
            vertices = new Vertex[numV]

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
        def id = scanner.nextInt() - 1 // one-indexed
        def vertex = vertices[id]
        if (!vertex) {
            vertex = new Vertex(id)
            vertices[id] = vertex
        }
        vertex
    }

    int getNumVertices() {
        vertices.length
    }

    int getNumEdges() {
        def edges = vertices.inject(0) { total, v ->
            total + v.edges.size()
        } as int
        // Divide by 2 since each edge is present twice
        edges >> 1
    }
}

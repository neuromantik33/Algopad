package algdsgn2.w1

import algopad.common.graph.Graph
import spock.lang.Specification
import spock.lang.Unroll

class MSTFinderSpec extends Specification {

    @Unroll
    def 'it should calculate total weight for the MST of the graph defined in #file'() {

        given:
        def input = MSTFinderSpec.class.getResource(file)
        def graph = new Graph(input)

        expect:
        graph.vertices.length == 500
        graph.numEdges == 2184

        where:
        file = 'edges.txt'

    }
}
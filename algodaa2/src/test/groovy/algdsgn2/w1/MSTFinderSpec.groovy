package algdsgn2.w1

import algopad.common.graph.Graph
import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w1.MSTFinder.findMST

class MSTFinderSpec extends Specification {

    @Unroll
    def 'it should calculate total weight for the MST of the graph defined in #file'() {

        given:
        def input = MSTFinderSpec.class.getResource(file)
        def graph = new Graph(input)

        when:
        def mst = findMST(graph)

        then:
        mst.inject(0) { weight, edge -> weight + edge.weight } == weight

        where:
        file = 'edges.txt'
        weight = -3612829

    }
}
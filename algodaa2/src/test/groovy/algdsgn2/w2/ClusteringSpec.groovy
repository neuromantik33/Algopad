package algdsgn2.w2

import algopad.common.graph.Graph
import spock.lang.Specification

import static algdsgn2.w2.Clustering.calculateSingleLinkClusteringFor

class ClusteringSpec extends Specification {

    def 'it should calculate the max-spacing K clustering'() {

        given:
        def input = ClusteringSpec.class.getResource(file)
        def graph = new Graph(input)

        expect:
        graph.numVertices == V

        when:
        def crossingEdges = calculateSingleLinkClusteringFor(graph, numClusters)

        then:
        crossingEdges.first().weight == maxSpacing

        where:
        file                   | V   | maxSpacing
        'clustering_test1.txt' | 5   | 2
        'clustering_test2.txt' | 10  | 5
        'clustering1.txt'      | 500 | 106

        numClusters = 4

    }
}
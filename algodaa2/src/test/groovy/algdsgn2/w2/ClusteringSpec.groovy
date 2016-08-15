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
        graph.numVertices == 500
        graph.numEdges == (int) 500 * 499 / 2 // Maximum number of edges in undirected graph

        when:
        def crossingEdges = calculateSingleLinkClusteringFor(graph, numClusters)

        then:
        println crossingEdges
        crossingEdges.last().weight == maxSpacing

        where:
        file              | numClusters | maxSpacing
        'clustering1.txt' | 4           | 9999

    }

    private static int maxNumEdges(int numV) {
        numV * (numV - 1) / 2 as int
    }
}
package algdsgn2.w2

import algopad.common.graph.Graph
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w2.Clustering.calculateSingleLinkClusteringFor
import static algdsgn2.w2.Clustering.countHammingClustersFor
import static java.lang.Integer.parseInt
import static java.util.concurrent.TimeUnit.SECONDS

class ClusteringSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'it should calculate the max-spacing #maxSpacing of a K-clustering'() {

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

    @Unroll
    def 'it should cluster a large list of integers by their hamming distance #dist'() {

        given:
        def input = ClusteringSpec.class.getResource(file)
        def ints = parseHammingFile(input)

        expect:
        countHammingClustersFor(ints, maxDistance) == numClusters

        cleanup:
        println "Time spent = ${stopwatch.runtime(SECONDS)}s"

        where:
        file                 | maxDistance | numClusters
        'hamming1.txt'       | 2           | 2
        'hamming2.txt'       | 2           | 2
        // SLOW: 'clustering_big.txt' | 2           | 6118

    }

    private static parseHammingFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def readTokens = { scanner.nextLine().split() }
            def (size, numBits) = readTokens().collect { parseInt it }
            assert numBits < 32

            def ints = new int[size]
            size.times { ix ->
                def binaryString = readTokens().join()
                ints[ix] = parseInt(binaryString, 2)
            }
            ints
        }
    }
}

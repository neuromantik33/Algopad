package algdsgn2.w5

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static java.lang.Math.pow
import static java.lang.Math.sqrt
import static java.util.concurrent.TimeUnit.MILLISECONDS

class TSPSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'it should calculate the minimum distance of a traveling salesman tour given a distance matrix'() {

        given:
        def tsp = new TSP(distances as float[][])

        expect:
        tsp.calculateMinimumTour() == tour

        cleanup:
        println "Time spent ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        distances                                     | tour
        [[0, 2, 9, 10],
         [1, 0, 6, 4],
         [15, 7, 0, 8],
         [6, 3, 12, 0]]                               | 21
        [[0, 29, 20, 21, 16, 31, 100, 12, 4, 31, 18],
         [29, 0, 15, 29, 28, 40, 72, 21, 29, 41, 12],
         [20, 15, 0, 15, 14, 25, 81, 9, 23, 27, 13],
         [21, 29, 15, 0, 4, 12, 92, 12, 25, 13, 25],
         [16, 28, 14, 4, 0, 16, 94, 9, 20, 16, 22],
         [31, 40, 25, 12, 16, 0, 95, 24, 36, 3, 37],
         [100, 72, 81, 92, 94, 95, 0, 90, 101, 99, 84],
         [12, 21, 9, 12, 9, 24, 90, 0, 15, 25, 13],
         [4, 29, 23, 25, 20, 36, 101, 15, 0, 35, 18],
         [31, 41, 27, 13, 16, 3, 99, 25, 35, 0, 38],
         [18, 12, 13, 25, 22, 37, 84, 13, 18, 38, 0]] | 253

    }

    def 'it should calculate the minimum distance of a traveling salesman tour given a coordinate list'() {

        given:
        def input = TSPSpec.class.getResource(file)
        def distances = parseTSPFile(input)
        def tsp = new TSP(distances)

        expect:
        tsp.calculateMinimumTour() as int == tour

        cleanup:
        println "Time spent ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        file           | tour
        'tsp_test.txt' | 20000
        'tsp.txt'      | 26442

    }

    private static parseTSPFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def n = scanner.nextInt()
            def points = new float[n][2]
            n.times {
                points[it] = [scanner.nextFloat(), scanner.nextFloat()] as float[]
            }

            def distances = new float[n][n]
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        distances[i][j] = 0.0f
                    } else {
                        def dx = points[i][0] - points[j][0]
                        def dy = points[i][1] - points[j][1]
                        distances[i][j] = sqrt(dx * dx + dy * dy)
                    }
                }
            }

            distances

        }
    }
}

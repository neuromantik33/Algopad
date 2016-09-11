package algdsgn2.w5

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static algdsgn2.w5.TSP.calculateMinimumTour
import static java.lang.Math.pow
import static java.lang.Math.sqrt

class TSPSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'it should calculate the minimum distance of a traveling salesman tour given a distance matrix'() {

        given:
        distances = distances as float[][]

        expect:
        calculateMinimumTour(distances) == tour

        cleanup:
        println "Time spent ${stopwatch.runtime(TimeUnit.MILLISECONDS)}ms"

        where:
        distances = [
          [0, 2, 9, 10],
          [1, 0, 6, 4],
          [15, 7, 0, 8],
          [6, 3, 12, 0]
        ]
        tour = 21

    }

    def 'it should calculate the minimum distance of a traveling salesman tour given a coordinate list'() {

        given:
        def input = TSPSpec.class.getResource(file)
        def distances = parseTSPFile(input)

        expect:
        calculateMinimumTour(distances) == tour

        cleanup:
        println "Time spent ${stopwatch.runtime(TimeUnit.MILLISECONDS)}ms"

        where:
        file           | tour
        'tsp_test.txt' | 20000
        //'tsp.txt'      | 0

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
                        def dx = pow(points[i][0] - points[j][0], 2)
                        def dy = pow(points[j][1] - points[j][1], 2)
                        distances[i][j] = sqrt(dx + dy)
                    }
                }
            }

            distances

        }
    }
}

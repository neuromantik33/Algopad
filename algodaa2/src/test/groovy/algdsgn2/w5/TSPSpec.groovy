package algdsgn2.w5

import spock.lang.Specification

class TSPSpec extends Specification {

    def 'it should calculate the minimum distance of a traveling salesman tour'() {

        given:
        def input = TSPSpec.class.getResource(file)
        def data = parseTSPFile(input)

        expect:
        TSP.calculateMinimumDistance(data) == distance

        where:
        file           | distance
        'tsp_test.txt' | 20000

    }

    private parseTSPFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def size = scanner.nextInt()
            def coords = []
            size.times { ix ->
                def lon = scanner.nextFloat()
                def lat = scanner.nextFloat()
                coords[ix] = [lon, lat] as float[]
            }
            coords
        }
    }
}

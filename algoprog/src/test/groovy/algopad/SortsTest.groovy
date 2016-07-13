package algopad

import spock.lang.Shared
import spock.lang.Specification

import static algopad.Sorts.mergeSort

class SortsTest extends Specification {

    @Shared
    def random = new Random()

    def 'it should sort a random list using #algo'() {

        expect:
        algo.call(list) == list.sort(false)

        where:
        algo << [mergeSort]
        list = randomList(50)

    }

    private List randomList(int size) {
        def list = []
        size.times { list << random.nextInt(size) }
        list
    }
}

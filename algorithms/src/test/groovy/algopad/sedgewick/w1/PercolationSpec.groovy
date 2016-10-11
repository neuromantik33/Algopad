package algopad.sedgewick.w1

import algopad.sedgewick.w1.Percolation.BitArray
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class PercolationSpec extends Specification {

    @Unroll
    def 'it should throw an error if N < 1 at construction'() {

        when:
        new Percolation(size)

        then:
        thrown IllegalArgumentException

        where:
        size << [-1, 0]

    }

    @Unroll
    def 'it should throw an error if any argument to "#method" is outside its prescribed range'() {

        given:
        def perc = new Percolation(3)

        when:
        perc."$method"(i, j)

        then:
        thrown IndexOutOfBoundsException

        where:
        method   | i | j
        'open'   | 0 | 0
        'isOpen' | 1 | 4
        'isFull' | 4 | 3

    }

    @Unroll
    def 'it should create a percolation system of size #size with the following behavior and characteristics'() {

        when:
        def perc = new Percolation(size)
        def openAndCheck = { i, j ->
            perc.open i, j
            assert perc.isOpen(i, j)
            assert perc.isFull(i, j)
        }

        then: 'the number of initial components is size * size'
        perc.unionFind.count() == size * size

        and: 'all sites must be closed and empty after construction'
        size.times { x ->
            size.times { y ->
                assert !perc.isOpen(x + 1, y + 1)
                assert !perc.isFull(x + 1, y + 1)
            }
        }

        when: 'if the upper left-hand corner is opened, it should be full'
        perc.open 1, 1

        then:
        perc.isOpen 1, 1
        perc.isFull 1, 1

        when: 'if the bottom right-hand corner is opened, it is still empty'
        perc.open size, size

        then:
        perc.isOpen size, size
        !perc.isFull(size, size)

        when: 'opening every site along the diagonal fills the newly opened sites and eventually percolates'
        def i = 1
        def j = 1
        def moveRight = true
        while (i < size || j < size) {
            if (moveRight) {
                j++
                moveRight = false
            } else {
                i++
                moveRight = true
            }
            openAndCheck(i, j)
        }

        then:
        perc.percolates()

        where:
        size << (2..10)

    }

    @Unroll
    def 'it should not percolate using the "#input" test cases'() {

        when:
        def perc = parsePercolationFile("${input}.txt")

        then:
        !perc.percolates()
        toBooleans(perc.openSites)
          .findAll()
          .size() == numOpen

        where:
        input        | numOpen
        'input1-no'  | 0
        'input2-no'  | 2
        'input8-no'  | 33
        'input10-no' | 55
        'heart25'    | 352
        'greeting57' | 2522

    }

    @Unroll
    def 'it should percolate using the "#input" test cases'() {

        when:
        def perc = parsePercolationFile("${input}.txt")

        then:
        perc.percolates()
        toBooleans(perc.openSites)
          .findAll()
          .size() == numOpen

        where:
        input         | numOpen
        'input1'      | 1
        'input2'      | 3
        'input3'      | 6
        'input4'      | 8
        'input5'      | 25
        'input6'      | 18
        'input7'      | 16
        'input8'      | 34
        'input10'     | 56
        'input20'     | 250
        'input50'     | 1412
        'jerry47'     | 1476
        'sedgewick60' | 2408
        'wayne98'     | 5079

    }

    /**
     * Return a boolean array with the same bit values a this BitArray.
     */
    def toBooleans(BitArray bitArray) {
        def len = bitArray.length()
        def bits = []
        len.times { bits.add(bitArray.get(it)) }
        bits
    }

    Percolation parsePercolationFile(String name) {
        getClass()
          .getResource(name)
          .withReader { reader ->
            def size = reader.readLine() as int
            def perc = new Percolation(size)
            reader.eachLine(1) {
                def line = it.trim()
                if (line) {
                    def (i, j) = line.split()
                    perc.open(i as int, j as int)
                }
            }
            perc
        }
    }
}

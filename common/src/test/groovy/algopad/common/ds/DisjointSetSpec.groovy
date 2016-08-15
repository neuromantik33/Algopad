package algopad.common.ds

import spock.lang.Specification
import spock.lang.Subject

import static algopad.common.misc.Closures.negate

class DisjointSetSpec extends Specification {

    @Subject
    def ds = new DisjointSet()

    def 'initially the disjoint set is empty (no connected nodes)'() {
        expect:
        ds.size == 0
    }

    def 'when an object is added, it should be augmented with a rank of 0 and pointer to self'() {

        given:
        def obj = new Object()

        when:
        ds.add obj

        then:
        //noinspection GroovyAccessibility
        def node = ds.nodeMap[obj]
        node.rank == 0
        node.parent == obj

        expect:
        ds.size == 1

    }

    def 'it should support union and connected operations connecting nodes'() {

        def connected = ds.&connected
        def disconnected = negate(connected)

        given:
        def o1 = new Object()
        def o2 = new Object()
        def o3 = new Object()
        [o1, o2, o3].each { ds.add it }

        expect:
        disconnected o1, o2
        disconnected o1, o3
        disconnected o2, o3
        ds.size == 3

        when:
        ds.union o1, o2

        then:
        connected o1, o2
        disconnected o1, o3
        disconnected o2, o3
        ds.size == 2

        when:
        ds.union o1, o3

        then:
        connected o1, o2
        connected o1, o3
        connected o2, o3
        ds.size == 1

    }

    def 'it should merge components by rank applying path compression'() {

        given:
        def ints = (0..9) as List
        def verifyRoots = { int ... ids ->
            def root = ds.find(ints[ids[0]])
            ids.each {
                assert ds.find(ints[it]) == root
            }
        }
        def union = { o1, o2 ->
            ds.union ints[o1], ints[o2]
        }

        when:
        ints.each ds.&add

        then:
        ds.size == 10

        when:
        union 4, 3

        then:
        verifyRoots 3, 4

        when:
        union 3, 8

        then:
        verifyRoots 3, 4, 8

        when:
        union 6, 5

        then:
        verifyRoots 6, 5

        when:
        union 9, 4

        then:
        verifyRoots 3, 4, 8, 9

        when:
        union 2, 1

        then:
        verifyRoots 1, 2

        expect:
        ds.connected ints[8], ints[9]
        !ds.connected(ints[5], ints[4])

        when:
        union 5, 0

        then:
        verifyRoots 0, 5, 6

        when:
        union 7, 2

        then:
        verifyRoots 1, 2, 7

        when:
        union 6, 1

        then:
        verifyRoots 0, 1, 2, 5, 6, 7

        when:
        union 7, 3

        then:
        verifyRoots 0..9 as int[]
        ds.size == 1

    }
}
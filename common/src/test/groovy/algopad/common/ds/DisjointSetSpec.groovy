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
}
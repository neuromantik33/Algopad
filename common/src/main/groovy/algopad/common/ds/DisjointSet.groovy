/*
 *  algopad.
 */

package algopad.common.ds

import groovy.transform.CompileStatic

/**
 * Implementation of a disjoint-set forest with union-by-rank and path compression.
 *
 * Adapted from pseudo-code (released with no restrictions) using an algorithm described here:
 * T. Cormen, C. Leiserson, R. Rivest, C. Stein, Introduction to Algorithms, 3rd edition (MIT Press, 2009), pp. 571
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class DisjointSet<T> {

    private Map<T, DSNode> nodeMap
    private int size

    DisjointSet(int expectedMaxSize = 32) {
        this.nodeMap = new IdentityHashMap(expectedMaxSize)
    }

    /**
     * Adds the object to the disjoint set, making it a singleton component in the process.
     */
    def add(T obj) {
        if (nodeMap[obj]) { return }
        def node = new DSNode()
        node.parent = obj
        nodeMap[obj] = node
        size += 1
    }

    /**
     * @return the number of connected components.
     */
    int getSize() { size }

    /**
     * @return {@code true} if the components containing <i>o1</i> and <i>o2</i> are connected,
     * {@code false} otherwise.
     */
    boolean connected(T o1, T o2) {
        verifyMembers o1, o2
        def p1 = find(o1)
        def p2 = find(o2)
        p1.is p2
    }

    /**
     * Connects the components containing <i>o1</i> and <i>o2</i>.
     */
    def union(T o1, T o2) {
        def p1 = find(o1)
        def p2 = find(o2)
        link p1, p2
        size -= 1
    }

    /**
     * Finds the root member of the component containing <i>obj</i>,
     * optimizing with path compression per operation.
     */
    def T find(T obj) {
        verifyMembers obj
        def node = nodeMap[obj]
        if (!obj.is(node.parent)) {
            node.parent = find(node.parent)
        }
        node.parent
    }

    // Useful method for debugging
    @SuppressWarnings("GroovyUnusedDeclaration")
    Map rootMap() {
        def inverse = [:]
        nodeMap.each { key, val ->
            def list = inverse.get(val.parent, [])
            list << key
        }
        inverse
    }

    private verifyMembers(T... members) {
        members.each {
            assert nodeMap[it], "$it was not added to disjoint-set"
        }
    }

    // Union by rank
    private link(T o1, T o2) {
        def node1 = nodeMap[o1]
        def node2 = nodeMap[o2]
        if (node1.rank > node2.rank) {
            node2.parent = o1
        } else {
            node1.parent = o2
            if (node1.rank == node2.rank) {
                node2.rank += 1
            }
        }
    }

    private class DSNode {
        int rank
        T parent

        @Override
        public String toString() {
            parent.toString()
        }
    }
}

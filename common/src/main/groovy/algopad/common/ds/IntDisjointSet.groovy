/*
 *  algopad.
 */

package algopad.common.ds

import groovy.transform.CompileStatic

/**
 * Implementation of a disjoint-set forest for integers with union-by-rank and path compression.
 *
 * Adapted from pseudo-code (released with no restrictions) using an algorithm described here:
 * T. Cormen, C. Leiserson, R. Rivest, C. Stein, Introduction to Algorithms, 3rd edition (MIT Press, 2009), pp. 571
 *
 * TODO Need to refactor with {@link DisjointSet}.
 * @author Nicolas Estrada.
 */
@CompileStatic
class IntDisjointSet {

    private DSNode[] nodes
    private int size

    IntDisjointSet(int expectedMaxSize = 32) {
        this.nodes = new DSNode[expectedMaxSize]
    }

    /**
     * Adds the object to the disjoint set, making it a singleton component in the process.
     */
    def add(int num) {
        if (nodes[num]) { return }
        def node = new DSNode()
        node.parent = num
        nodes[num] = node
        size += 1
    }

    // Shortcut for adding
    def leftShift(int num) { add num }

    /**
     * @return the number of connected components.
     */
    int getSize() { size }

    /**
     * @return {@code true} if the components containing <i>num1</i> and <i>num2</i> are connected,
     * {@code false} otherwise.
     */
    boolean connected(int num1, int num2) {
        verifyMembers num1, num2
        int p1 = find(num1)
        int p2 = find(num2)
        p1 == p2
    }

    /**
     * Connects the components containing <i>num1</i> and <i>num2</i>.
     */
    def union(int num1, int num2) {
        def p1 = find(num1)
        def p2 = find(num2)
        if (p1 != p2) {
            link p1, p2
            size -= 1
        }
    }

    /**
     * Finds the root member of the component containing <i>num</i>,
     * optimizing with path compression per operation.
     */
    def int find(int num) {
        verifyMembers num
        def node = nodes[num]
        if (num != node.parent) {
            node.parent = find(node.parent)
        }
        node.parent
    }

    private verifyMembers(int ... members) {
        members.each { int member ->
            assert nodes[member], "$member was not added to disjoint-set"
        }
    }

    // Union by rank
    private link(int num1, int num2) {
        def node1 = nodes[num1]
        def node2 = nodes[num2]
        if (node1.rank > node2.rank) {
            node2.parent = num1
        } else {
            node1.parent = num2
            if (node1.rank == node2.rank) {
                node2.rank += 1
            }
        }
    }

    private class DSNode {
        int rank
        int parent

        @Override
        public String toString() { "$parent" }
    }
}

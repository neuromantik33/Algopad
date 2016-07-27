/*
 *  algopad.
 */

package algopad.common.ds

/**
 * Implementation of a disjoint-set forest with union-by-rank and path compression.
 *
 * Adapted from pseudo-code (released with no restrictions) using an algorithm described here:
 * T. Cormen, C. Leiserson, R. Rivest, C. Stein, Introduction to Algorithms, 3rd edition (MIT Press, 2009), pp. 571
 *
 * @author Nicolas Estrada.
 */
class DisjointSet {

    private int size

    /**
     * Adds the object to the disjoint set, making it a singleton component in the process.
     */
    def add(obj) {
        obj.metaClass.mixin DSNode
        obj.parent = obj
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
    boolean connected(o1, o2) {
        def p1 = find(o1)
        def p2 = find(o2)
        p1.is p2
    }

    /**
     * Connects the components containing <i>o1</i> and <i>o2</i>.
     */
    def union(o1, o2) {
        def p1 = find(o1)
        def p2 = find(o2)
        link p1, p2
        size -= 1
    }

    // Path compression
    private find(obj) {
        if (!obj.is(obj)) {
            obj.parent = find(obj.parent)
        }
        obj.parent
    }

    // Union by rank
    private static link(o1, o2) {
        if (o1.rank > o2.rank) {
            o2.parent = o1
        } else {
            o1.parent = o2
            if (o1.rank == o2.rank) {
                o2.rank += 1
            }
        }
    }

    private static class DSNode {
        int rank
        def parent
    }
}

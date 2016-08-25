/*
 *  algopad.
 */

package algdsgn2.w3

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import groovy.transform.ToString

import static java.lang.Math.max
import static java.lang.System.arraycopy

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class Knapsack {

    /**
     * Given the <i>items</i> and a knapsack <i>maxCapacity</i>, this method returns the maximum value
     * belonging of a subset of {@link Item}s that can fit into the knapsack.
     *
     * @return the maximum value constructed from the optimal solution.
     */
    static int calculateMaxValueWithCapacity(final Item[] items, final int maxCapacity) {

        def cache = new int[maxCapacity]
        def buf = new int[maxCapacity]
        def updateCache = { arraycopy(buf, 0, cache, 0, maxCapacity) }

        items.each { Item item ->
            for (int capacity = 0; capacity < maxCapacity; capacity++) {
                if (item.weight <= capacity) {
                    def solutionWithoutItem = cache[capacity]
                    def solutionWithItem = cache[capacity - item.weight] + item.value
                    buf[capacity] = max(solutionWithoutItem, solutionWithItem)
                }
            }
            updateCache()
        }

        cache[-1]

    }

    /**
     * Knapsack item representation.
     */
    @Immutable
    @ToString(includePackage = false, includeNames = true)
    static class Item {
        int value
        int weight
    }
}

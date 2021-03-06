/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.algdsgn2.w3

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

        def cache = new int[maxCapacity + 1]
        def buf = new int[maxCapacity + 1]

        items.each { Item item ->
            for (int capacity = 1; capacity <= maxCapacity; capacity++) {
                if (item.weight <= capacity) {
                    def solutionWithoutItem = cache[capacity]
                    def solutionWithItem = cache[capacity - item.weight] + item.value
                    buf[capacity] = max(solutionWithoutItem, solutionWithItem)
                }
            }
            // Update the cache with the buffer contents
            arraycopy buf, 0, cache, 0, maxCapacity + 1
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

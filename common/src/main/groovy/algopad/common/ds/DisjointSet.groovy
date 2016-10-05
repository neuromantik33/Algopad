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
    private Closure<Boolean> areSame;
    private int size

    /**
     * @param useIdentity if {@code true} then the object identity is used for equality testing,
     * otherwise it falls back to {@link Object#equals(java.lang.Object)}.
     */
    DisjointSet(int expectedMaxSize = 32, boolean useIdentity = true) {
        if (useIdentity) {
            this.nodeMap = new IdentityHashMap(expectedMaxSize)
            this.areSame = { T o1, T o2 -> o1.is o2 }
        } else {
            this.nodeMap = new HashMap(expectedMaxSize + (int) (expectedMaxSize / 3))
            this.areSame = { T o1, T o2 -> o1 == o2 }
        }
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

    // Shortcut for adding
    def leftShift(T obj) { add obj }

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
        areSame p1, p2
    }

    /**
     * Connects the components containing <i>o1</i> and <i>o2</i>.
     */
    void union(T o1, T o2) {
        def p1 = find(o1)
        def p2 = find(o2)
        if (!areSame(p1, p2)) {
            link p1, p2
            size -= 1
        }
    }

    /**
     * Finds the root member of the component containing <i>obj</i>,
     * optimizing with path compression per operation.
     */
    def T find(T obj) {
        verifyMembers obj
        def node = nodeMap[obj]
        if (!areSame(obj, node.parent)) {
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

    private verifyMembers(Object... members) {
        members.each { T elmt ->
            assert nodeMap[elmt], "$elmt was not added to disjoint-set"
        }
    }

    // Union by rank
    private void link(T o1, T o2) {
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

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
        ds << obj

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
        [o1, o2, o3].each { ds << it }

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

    @SuppressWarnings('GroovyOverlyLongMethod')
    def 'it should merge components by rank applying path compression'() {

        given:
        def uf = new DisjointSet(10, false)
        def verifyRoots = { int ... ids ->
            def root = uf.find(ids[0])
            ids.each {
                assert uf.find(it) == root
            }
        }

        when:
        (0..9).each(uf.&add)

        then:
        uf.size == 10

        when:
        uf.union 4, 3

        then:
        verifyRoots 3, 4

        when:
        uf.union 3, 8

        then:
        verifyRoots 3, 4, 8

        when:
        uf.union 6, 5

        then:
        verifyRoots 6, 5

        when:
        uf.union 9, 4

        then:
        verifyRoots 3, 4, 8, 9

        when:
        uf.union 2, 1

        then:
        verifyRoots 1, 2

        expect:
        uf.connected 8, 9
        !uf.connected(5, 4)

        when:
        uf.union 5, 0

        then:
        verifyRoots 0, 5, 6

        when:
        uf.union 7, 2

        then:
        verifyRoots 1, 2, 7

        when:
        uf.union 6, 1

        then:
        verifyRoots 0, 1, 2, 5, 6, 7

        when:
        uf.union 7, 3

        then:
        verifyRoots 0..9 as int[]
        uf.size == 1

    }
}

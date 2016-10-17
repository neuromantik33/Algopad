/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
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

package algopad.algorithms.pt1.w5

import algopad.algorithms.pt1.w5.KdTree.Node
import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.Queue
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings("GroovyAccessibility")
class KdTreeSpec extends Specification {

    @Unroll
    def 'it should throw an error if any argument to "#method" is null'() {

        given:
        def tree = new KdTree()

        when:
        tree."$method"(null)

        then:
        thrown(NullPointerException)

        where:
        method << ['insert', 'contains', 'range', 'nearest']

    }

    def 'it should support some basic operations'() {

        given:
        def tree = new KdTree()

        expect:
        tree.empty
        tree.size() == 0

        when:
        tree.insert new Point2D(0.2, 0.2)

        then:
        !tree.empty
        tree.size() == 1
        tree.contains new Point2D(0.2, 0.2)

        when:
        tree.insert new Point2D(0.3, 0.2)

        then:
        !tree.empty
        tree.size() == 2
        tree.contains new Point2D(0.3, 0.2)

    }

    def toPoint = { new Point2D(*it) }
    def toRect = { new RectHV(*it) }

    def 'it should build the 2d-tree alternating bisection rectangles after every insert'() {

        given:
        def tree = new KdTree()
        List<Node> nodes = []
        def insert = { x, y ->
            tree.insert new Point2D(x, y)
            nodes = levelOrder(tree)
        }

        when:
        insert 0.7, 0.2

        then:
        [[0.7, 0.2]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.5, 0.4

        then:
        [[0.7, 0.2],
         [0.5, 0.4]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.7, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.2, 0.3

        then:
        [[0.7, 0.2],
         [0.5, 0.4],
         [0.2, 0.3]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.7, 1.0],
         [0.0, 0.0, 0.7, 0.4]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.4, 0.7

        then:
        [[0.7, 0.2],
         [0.5, 0.4],
         [0.2, 0.3],
         [0.4, 0.7]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.7, 1.0],
         [0.0, 0.0, 0.7, 0.4],
         [0.0, 0.4, 0.7, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.9, 0.6

        then:
        [[0.7, 0.2],
         [0.5, 0.4],
         [0.9, 0.6],
         [0.2, 0.3],
         [0.4, 0.7]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.7, 1.0],
         [0.7, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.7, 0.4],
         [0.0, 0.4, 0.7, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

    }

    def 'it should build the 2d-tree alternating bisection rectangles after every insert (case #2)'() {

        given:
        def tree = new KdTree()
        List<Node> nodes = []
        def insert = { x, y ->
            tree.insert new Point2D(x, y)
            nodes = levelOrder(tree)
        }

        when:
        insert 0.2, 0.3

        then:
        [[0.2, 0.3]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.4, 0.2

        then:
        [[0.2, 0.3],
         [0.4, 0.2]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.2, 0.0, 1.0, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.4, 0.5

        then:
        [[0.2, 0.3],
         [0.4, 0.2],
         [0.4, 0.5]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.2, 0.0, 1.0, 1.0],
         [0.2, 0.2, 1.0, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.3, 0.3

        then:
        [[0.2, 0.3],
         [0.4, 0.2],
         [0.4, 0.5],
         [0.3, 0.3]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.2, 0.0, 1.0, 1.0],
         [0.2, 0.2, 1.0, 1.0],
         [0.2, 0.2, 0.4, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.1, 0.5

        then:
        [[0.2, 0.3],
         [0.1, 0.5],
         [0.4, 0.2],
         [0.4, 0.5],
         [0.3, 0.3]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.2, 1.0],
         [0.2, 0.0, 1.0, 1.0],
         [0.2, 0.2, 1.0, 1.0],
         [0.2, 0.2, 0.4, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

        when:
        insert 0.4, 0.4

        then:
        [[0.2, 0.3],
         [0.1, 0.5],
         [0.4, 0.2],
         [0.4, 0.5],
         [0.3, 0.3],
         [0.4, 0.4]].collect(toPoint) == nodes*.point
        [[0.0, 0.0, 1.0, 1.0],
         [0.0, 0.0, 0.2, 1.0],
         [0.2, 0.0, 1.0, 1.0],
         [0.2, 0.2, 1.0, 1.0],
         [0.2, 0.2, 0.4, 1.0],
         [0.4, 0.2, 1.0, 1.0]].collect(toRect) == nodes*.bounds
        tree.size() == nodes.size()

    }

    @Unroll
    def 'it should support range search in logarithmic time for #input'() {

        given:
        def tree = parsePointsFile("${input}.txt")

        when:
        def rect = new RectHV(*rectangle)
        def range = tree.range(rect)
        def expected = points.collect { new Point2D(*it) }

        then:
        range.size() == expected.size()
        range.sort() == expected

        where:
        input      | rectangle                 | points
        'exercise' | [0.15, 0.25, 0.35, 0.35]  | [[0.2, 0.3], [0.3, 0.3]]
        'circle10' | [0.0, 0.0, 1.0, 0.095500] | [[0.5, 0.0], [0.206107, 0.095492], [0.793893, 0.095492]]
        'circle10' | [0.5, 0.5, 1.0, 0.654600] | [[0.975528, 0.654508]]

    }

    @Unroll
    def 'it should support nearest neighbor search in logarithmic time for #input'() {

        given:
        def tree = parsePointsFile("${input}.txt")

        when:
        def nearest = tree.nearest(new Point2D(*point))

        then:
        nearest == new Point2D(*neighbor)

        where:
        input      | point      | neighbor
        'circle10' | [0.0, 0.0] | [0.206107, 0.095492]
        'circle10' | [0.0, 1.0] | [0.206107, 0.904508]
        'circle10' | [1.0, 0.0] | [0.793893, 0.095492]
        'circle10' | [1.0, 1.0] | [0.793893, 0.904508]

    }

    def 'it should arrange the letters in the following level order'() {

        def invert = { Map map ->
            def inv = [:]
            map.each { k, v -> inv[v] = k }
            inv
        }

        given:
        def points = invert([A: [0.92, 0.8],
                             B: [0.91, 0.89],
                             C: [0.13, 0.57],
                             D: [0.68, 0.68],
                             E: [0.16, 0.23],
                             F: [0.23, 0.15],
                             G: [0.08, 0.87],
                             H: [0.09, 0.38]])
        def tree = new KdTree()

        when:
        points.each { point, letter ->
            def pt = new Point2D(*point)
            tree.insert(pt)
        }

        then:
        def order = levelOrder(tree)*.point
        order.collect { points[[it.x() as BigDecimal, it.y() as BigDecimal]] }
             .join(' ') == 'A B C G D H E F'

    }

    List<Node> levelOrder(KdTree tree) {

        def nodes = []
        def queue = new Queue<>()

        queue.enqueue tree.root
        while (!queue.empty) {
            def x = queue.dequeue()
            if (x) {
                nodes << x
                queue.enqueue x.left
                queue.enqueue x.right
            }
        }

        nodes

    }

    KdTree parsePointsFile(String name) {
        getClass()
          .getResource(name)
          .withReader { reader ->
            def tree = new KdTree()
            reader.eachLine {
                def line = it.trim()
                if (line) {
                    def (x, y) = line.split()
                    tree.insert new Point2D(x as double, y as double)
                }
            }
            tree
        }
    }
}

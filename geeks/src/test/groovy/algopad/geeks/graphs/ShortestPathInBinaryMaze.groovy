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

package algopad.geeks.graphs

import algopad.common.ds.LinkedQueue
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/shortest-path-in-a-binary-maze')
class ShortestPathInBinaryMaze extends Specification {

    @Subject
    def findShortestPathInMaze = { int[][] matrix, List src, List dest ->

        def m = matrix.length
        def n = matrix[0].length
        def distances = new Double[m][n]
        def infinity = Double.POSITIVE_INFINITY
        def offsets = [
          [-1, 0], /* UP */
          [0, 1], /* RIGHT */
          [1, 0], /* DOWN */
          [0, -1] /* LEFT */
        ]

        def eachAdjacentCell = { List coord, Closure closure ->
            for (offset in offsets) {
                def x = coord[0] + offset[0]
                def y = coord[1] + offset[1]
                def validX = x >= 0 && x < m
                def validY = y >= 0 && y < n
                if (validX && validY && matrix[x][y] == 1) {
                    closure.call x, y
                }
            }
        }

        def queue = new LinkedQueue<>()
        distances[src[0]][src[1]] = 0
        queue.offer src

        while (!queue.empty) {
            def coord = queue.poll()
            def dist = distances[coord[0]][coord[1]]
            if (coord == dest) { break }
            eachAdjacentCell(coord) { int x, int y ->
                if (distances[x][y] == null) {
                    distances[x][y] = dist + 1
                    queue.offer([x, y])
                }
            }
        }

        distances[dest[0]][dest[1]] ?: infinity

    }

    @Unroll
    def '''given a #size matrix where each element is either 0 or 1, it should find the shortest
           path #distance between a given source cell #src to a destination cell #dest.
           The path can only be created out of a cell if its value is 1.'''() {

        given:
        matrix = matrix as int[][]

        expect:
        findShortestPathInMaze(matrix, src, dest) == distance

        where:
        src    | dest   | distance
        [0, 0] | [3, 4] | 11
        [0, 0] | [8, 1] | 23
        [0, 0] | [8, 9] | Double.POSITIVE_INFINITY

        matrix = [[1, 0, 1, 1, 1, 1, 0, 1, 1, 1],
                  [1, 0, 1, 0, 1, 1, 1, 0, 1, 1],
                  [1, 1, 1, 0, 1, 1, 0, 1, 0, 1],
                  [0, 0, 0, 0, 1, 0, 0, 0, 0, 1],
                  [1, 1, 1, 0, 1, 1, 1, 0, 1, 0],
                  [1, 0, 1, 1, 1, 1, 0, 1, 0, 0],
                  [1, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                  [1, 0, 1, 1, 1, 1, 0, 1, 1, 1],
                  [1, 1, 0, 0, 0, 0, 1, 0, 0, 1]]
        size = "${matrix.size()}x${matrix[0].size()}"

    }
}

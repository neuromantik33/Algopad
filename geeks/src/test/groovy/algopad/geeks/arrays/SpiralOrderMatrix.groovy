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

package algopad.geeks.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/print-a-given-matrix-in-spiral-form')
class SpiralOrderMatrix extends Specification {

    @Subject
    def spiralOrder = { List<List> matrix ->
        //noinspection GroovyLocalVariableNamingConvention
        final RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3

        int m = matrix.size(), n = matrix[0].size()
        int left = 0, right = n - 1, top = 0, bottom = m - 1
        int dir = RIGHT
        final result = []

        def total = m * n
        while (result.size() < total) {
            switch (dir) {
                case RIGHT:
                    left.upto(right) { result << matrix[top][it] }
                    top++
                    break
                case DOWN:
                    top.upto(bottom) { result << matrix[it][right] }
                    right--
                    break
                case LEFT:
                    right.downto(left) { result << matrix[bottom][it] }
                    bottom--
                    break
                case UP:
                    bottom.downto(top) { result << matrix[it][left] }
                    left++
                    break
                default:
                    assert false // Impossible
            }
            // Next direction depends on constant values above
            dir = (dir + 1) % 4
        }
        result
    }

    @Unroll
    def 'given a matrix of #m x #n, return all elements of the matrix in spiral order.'() {

        expect:
        spiralOrder(matrix) == result

        where:
        matrix                     | result
        [[1, 2, 3, 4, 5],
         [10, 9, 8, 7, 6]]         | 1..10
        [[1, 2, 3],
         [4, 5, 6],
         [7, 8, 9]]                | [1, 2, 3, 6, 9, 8, 7, 4, 5]
        [[1, 2, 3, 4],
         [5, 6, 7, 8],
         [9, 10, 11, 12],
         [13, 14, 15, 16]]         | [1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]
        [[1, 2, 3, 4, 5, 6],
         [7, 8, 9, 10, 11, 12],
         [13, 14, 15, 16, 17, 18]] | [1, 2, 3, 4, 5, 6, 12, 18, 17, 16, 15, 14, 13, 7, 8, 9, 10, 11]
        m = matrix.size()
        n = matrix[0].size()

    }
}

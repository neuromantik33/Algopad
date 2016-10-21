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

package algopad.intvbit.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll

class SimulationArraySpec extends Specification {

    @Unroll
    @See('http://www.interviewbit.com/problems/spiral-order-matrix-i')
    def 'given a matrix of #m x #n (#total elements), return all elements of the matrix in spiral order.'() {

        given:
        def spiralOrder = { List<List> matrix ->
            //noinspection GroovyLocalVariableNamingConvention
            final RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3
            def left = 0, right = n - 1, top = 0, bottom = m - 1
            def dir = RIGHT
            final result = []
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
                dir = (dir + 1) % 3
            }
            result
        }

        expect:
        spiralOrder(matrix) == result

        where:
        matrix             | result
        [[1, 2, 3],
         [4, 5, 6],
         [7, 8, 9]]        | [1, 2, 3, 6, 9, 8, 7, 4, 5]
        [[1, 2, 3, 4, 5],
         [10, 9, 8, 7, 6]] | 1..10
        m = matrix.size()
        n = matrix[0].size()
        total = m * n

    }
}

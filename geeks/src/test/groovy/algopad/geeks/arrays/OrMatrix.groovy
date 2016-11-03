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

package algopad.geeks.arrays

import algopad.common.ds.BitArray
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.util.Collections.fill

@See('http://www.geeksforgeeks.org/a-boolean-matrix-question')
class OrMatrix extends Specification {

    @Subject
    def orMatrix = { List<List> matrix ->
        int m = matrix.size()
        int n = matrix[0].size()
        def x = new BitArray(m)
        def y = new BitArray(n)
        m.times { i ->
            n.times { j ->
                if (matrix[i][j] == 1) {
                    x[i] = true
                    y[j] = true
                }
            }
        }
        m.times { i ->
            if (x[i]) { fill(matrix[i], 1) }
            n.times { j ->
                if (y[j]) { matrix[i][j] = 1 }
            }
        }
    }

    @Unroll
    def '''given a boolean matrix of #m x #n, it should modify it such that if a matrix cell
           mat[i][j] is 1 (or true) then make all the cells of ith row and jth column as 1.'''() {

        when:
        orMatrix(matrix)

        then:
        matrix == transformed

        where:
        matrix << [
          [[1, 0],
           [0, 0]],
          [[0, 0, 0],
           [0, 0, 1]],
          [[1, 0, 0, 1],
           [0, 0, 1, 0],
           [0, 0, 0, 0]]
        ]
        transformed << [
          [[1, 1],
           [1, 0]],
          [[0, 0, 1],
           [1, 1, 1]],
          [[1, 1, 1, 1],
           [1, 1, 1, 1],
           [1, 0, 1, 1]]
        ]
        m = matrix.size()
        n = matrix[0].size()
    }
}

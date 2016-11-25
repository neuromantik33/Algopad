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

package algopad.interviewbit.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/anti-diagonals')
class AntiDiagonals extends Specification {

    @Subject
    def antiDiagonals = { List<List> matrix ->
        int n = matrix.size()
        def diagonals = []
        // There are 2n - 1 diagonals
        diagonals.ensureCapacity n + n - 1
        n.times { i ->
            n.times { j ->
                int idx = i + j
                if (!diagonals[idx]) {
                    diagonals[idx] = []
                }
                diagonals[idx] << matrix[i][j]
            }
        }
        diagonals
    }

    @Unroll
    def 'given a matrix of #n x #n, it should return an array of its anti-diagonals.'() {

        expect:
        antiDiagonals(matrix) == diagonals

        where:
        matrix      | diagonals
        [[1, 2],
         [3, 4]]    | [[1], [2, 3], [4]]
        [[1, 2, 3],
         [4, 5, 6],
         [7, 8, 9]] | [[1], [2, 4], [3, 5, 7], [6, 8], [9]]
        n = matrix.size()

    }
}

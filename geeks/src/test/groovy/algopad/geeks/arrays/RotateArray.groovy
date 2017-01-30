/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

@See('http://www.geeksforgeeks.org/inplace-rotateLeft-square-matrix-by-90-degrees')
class RotateArray extends Specification {

    @Subject
    def rotateLeft = { List<List<Integer>> m ->
        int n = m.size()
        if (n < 2) { return }
        int top = 0, bottom = n - 1, left = 0, right = n - 1
        while (top < bottom) {
            for (int i = 0; i < right - left; i++) {
                int tmp = m[top][left + i]
                m[top][left + i] = m[top + i][right]
                m[top + i][right] = m[bottom][right - i]
                m[bottom][right - i] = m[bottom - i][left]
                m[bottom - i][left] = tmp
            }
            left++
            right--
            top++
            bottom--
        }
    }

    @Unroll
    def 'given a #n x #n square matrix, it should rotate it by 90 degrees counter-clockwise in place.'() {

        when:
        rotateLeft(matrix)

        then:
        matrix == rotated

        where:
        matrix             | rotated
        []                 | []
        [[1]]              | [[1]]
        [[1, 2],
         [3, 4]]           | [[2, 4], [1, 3]]
        [[1, 2, 3],
         [4, 5, 6],
         [7, 8, 9]]        | [[3, 6, 9], [2, 5, 8], [1, 4, 7]]
        [[1, 2, 3, 4],
         [5, 6, 7, 8],
         [9, 10, 11, 12],
         [13, 14, 15, 16]] | [[4, 8, 12, 16], [3, 7, 11, 15], [2, 6, 10, 14], [1, 5, 9, 13]]

        left = true
        n = matrix.size()

    }

    @Subject
    def rotateRight = { List<List<Integer>> m ->
        int n = m.size()
        if (n < 2) { return }
        int top = 0, bottom = n - 1, left = 0, right = n - 1
        while (top < bottom) {
            for (int i = 0; i < right - left; i++) {
                int tmp = m[top][left + i]
                m[top][left + i] = m[bottom - i][left]
                m[bottom - i][left] = m[bottom][right - i]
                m[bottom][right - i] = m[top + i][right]
                m[top + i][right] = tmp
            }
            left++
            right--
            top++
            bottom--
        }
    }

    @Unroll
    def 'given a #n x #n square matrix, it should rotate it by 90 degrees clockwise in place.'() {

        when:
        rotateRight(matrix)

        then:
        matrix == rotated

        where:
        matrix             | rotated
        []                 | []
        [[1]]              | [[1]]
        [[1, 2],
         [3, 4]]           | [[3, 1], [4, 2]]
        [[1, 2, 3],
         [4, 5, 6],
         [7, 8, 9]]        | [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
        [[1, 2, 3, 4],
         [5, 6, 7, 8],
         [9, 10, 11, 12],
         [13, 14, 15, 16]] | [[13, 9, 5, 1], [14, 10, 6, 2], [15, 11, 7, 3], [16, 12, 8, 4]]

        left = true
        n = matrix.size()

    }
}

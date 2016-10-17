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

package algopad.algorithms.pt1.w1

import edu.princeton.cs.algs4.QuickFindUF
import edu.princeton.cs.algs4.WeightedQuickUnionUF
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class UnionFindSpec extends Specification {

    @Unroll
    def 'quick-find : it should generate the following ids #array after "#unions" union operations'() {

        given:
        def unionFind = new QuickFindUF(10)

        when:
        def ops = unions.split(' ')
        ops.each { op ->
            def (x, y) = op.split('-')
            unionFind.union x as int, y as int
        }

        then:
        unionFind.id == array as int[]

        where:
        unions                    | array
        '4-7 5-3 6-0 7-8 8-5 9-5' | [0, 1, 2, 3, 3, 3, 0, 3, 3, 3]
        '8-5 2-4 5-0 2-0 8-1 5-9' | [9, 9, 9, 3, 9, 9, 6, 7, 9, 9]

    }

    @Unroll
    def 'weighted-quick-union : it should generate the following ids #array after "#unions" union operations'() {

        given:
        def unionFind = new WeightedQuickUnionUF(10)

        when:
        def ops = unions.split(' ')
        ops.each { op ->
            def (x, y) = op.split('-')
            unionFind.union x as int, y as int
        }

        then:
        unionFind.parent == array as int[]

        where:
        unions                                | array
        '0-1 2-9 7-4 3-4 0-7 8-5 5-9 5-0 2-6' | [7, 0, 8, 7, 7, 8, 7, 7, 7, 2]
        '7-8 0-9 9-8 2-6 3-2 1-3 5-3 7-1 9-4' | [2, 2, 2, 2, 2, 2, 2, 0, 7, 0]

    }
}

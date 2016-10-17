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

package algopad.algorithms.pt1.w4

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings("GroovyAccessibility")
class SolverSpec extends Specification {

    @Unroll
    def 'it should solve the puzzle in #n dimensions'() {

        given:
        def name = "puzzle${n}x${n}"
        Map files = range.inject([:]) { result, moves ->
            def suffix = (moves as String).padLeft(2, '0')
            result["$name-${suffix}.txt"] = moves
            result
        }

        expect:
        files.each { file, moves ->
            def board = parseBoardFile(file)
            def solver = new Solver(board)
            assert solver.moves() == moves
        }

        where:
        n | range
        2 | (0..6)
        3 | (0..20)
        4 | (0..20)

    }

    @Unroll
    def 'it should return the solution for #input as a list of boards'() {

        given:
        def board = parseBoardFile("${input}.txt")

        when:
        def solver = new Solver(board)

        then:
        solver.moves() == moves
        solver.solution() as List == solution.collect { new Board(dim, it as short[]) }

        where:
        dim | input      | moves | solution
        2   | 'puzzle01' | 1     | [[1, 0, 3, 2], [1, 2, 3, 0]]
        2   | 'puzzle03' | 3     | [[2, 0, 1, 3], [0, 2, 1, 3], [1, 2, 0, 3], [1, 2, 3, 0]]
        3   | 'puzzle04' | 4     | [[0, 1, 3, 4, 2, 5, 7, 8, 6],
                                    [1, 0, 3, 4, 2, 5, 7, 8, 6],
                                    [1, 2, 3, 4, 0, 5, 7, 8, 6],
                                    [1, 2, 3, 4, 5, 0, 7, 8, 6],
                                    [1, 2, 3, 4, 5, 6, 7, 8, 0]]

    }

    @Unroll
    def 'it should mark #input instances as unsolvable'() {

        given:
        def board = parseBoardFile("${input}.txt")

        when:
        def solver = new Solver(board)

        then:
        !solver.solvable
        solver.moves() == -1
        solver.solution() == null

        where:
        input << [
          'puzzle2x2-unsolvable1',
          'puzzle2x2-unsolvable2',
          'puzzle2x2-unsolvable3',
          'puzzle3x3-unsolvable',
          'puzzle3x3-unsolvable1',
          'puzzle3x3-unsolvable2'
        ]
    }

    @Unroll
    @Ignore
    def 'it should solve the hard #input in #moves moves'() {

        given:
        def board = parseBoardFile("${input}.txt")

        when:
        def solver = new Solver(board)

        then:
        solver.moves() == moves

        where:
        input          | moves
        'puzzle4x4-38' | 38
        'puzzle4x4-47' | 47

    }

    Board parseBoardFile(String name) {
        getClass()
          .getResource(name)
          .withReader { reader ->
            def dim = reader.readLine() as int
            def blocks = new int[dim][dim]
            def row = 0
            reader.eachLine(1) {
                def line = it.trim()
                if (line) {
                    def tiles = line.split()
                    assert tiles.length == dim
                    for (int i = 0; i < tiles.length; i++) {
                        blocks[row][i] = tiles[i] as int
                    }
                }
                row += 1
            }
            new Board(blocks)
        }
    }
}

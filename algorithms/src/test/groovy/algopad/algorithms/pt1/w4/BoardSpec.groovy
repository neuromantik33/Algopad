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

import edu.princeton.cs.algs4.StdRandom
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings("GroovyAccessibility")
class BoardSpec extends Specification {

    def 'it should throw an error if tiles are null at construction'() {

        when:
        new Board(null)

        then:
        thrown NullPointerException

    }

    @Unroll
    def 'it should convert coordinates #coords into #index and vice versa'() {

        given:
        def blocks = [[1, 2, 3], [4, 5, 6], [7, 8, 9]] as int[][]
        def board = new Board(blocks)

        expect:
        board.coordToIndex(*coords) == index
        board.tiles == [1, 2, 3, 4, 5, 6, 7, 8, 9] as short[]

        where:
        coords | index
        [0, 0] | 0
        [0, 1] | 1
        [0, 2] | 2
        [1, 0] | 3
        [1, 1] | 4
        [1, 2] | 5
        [2, 0] | 6
        [2, 1] | 7
        [2, 2] | 8

    }

    @Unroll
    def 'it should score 0 for both hamming and manhattan if the board of size #dim is the goal'() {

        given:
        def blocks = toBlocks(dim, tiles)
        def board = new Board(blocks)

        expect:
        board.dimension() == dim
        board.hamming() == 0
        board.manhattan() == 0
        board.goal
        board.tiles.collect { it as int } == tiles

        where:
        tiles                                                  | dim
        [1, 2, 3, 0]                                           | 2
        [1, 2, 3, 4, 5, 6, 7, 8, 0]                            | 3
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0] | 4

    }

    @Unroll
    def 'it should calculate the manhattan and hamming distances #manhattan and #hamming for the following boards'() {

        given:
        def blocks = toBlocks(dim, tiles)
        def board = new Board(blocks)

        expect:
        board.manhattan() == manhattan as int
        board.hamming() == hamming as int

        where:
        dim | tiles                       | manhattan                         | hamming
        3   | [8, 1, 3, 4, 0, 2, 7, 6, 5] | [3, 1, 0, 0, 0, 2, 0, 2, 2].sum() | [1, 1, 0, 0, 0, 1, 0, 1, 1].sum()
        3   | [0, 1, 3, 4, 2, 5, 7, 8, 6] | 4                                 | 4
        3   | [1, 0, 3, 4, 2, 5, 7, 8, 6] | 3                                 | 3
        2   | [2, 3, 0, 1]                | 5                                 | 3

    }

    @Unroll
    def 'it should have a string representation matching #input'() {

        given:
        def file = "${input}.txt"

        when:
        def board = parseBoardFile(file)
        def expected = getClass().getResource(file).text

        then:
        board.toString() == expected

        where:
        input << ['puzzle03', 'puzzle04', 'puzzle00']

    }

    def 'it should implement equality by comparing dimensions, scores and actual tile values'() {

        given:
        def board1 = new Board(toBlocks(3, [1, 2, 3, 4, 5, 6, 7, 8, 0]))
        def board2 = new Board(toBlocks(3, [1, 2, 3, 4, 5, 6, 7, 8, 0]))
        def board3 = new Board(toBlocks(3, [1, 2, 4, 3, 5, 6, 7, 8, 0]))

        expect: 'Degraded tests'
        board1 != null
        board1.equals board1

        and:
        board1 == board2
        board1 != board3

    }

    @Unroll
    def 'it should return the corresponding neighbors for #tiles given the location of the empty block'() {

        given:
        def blocks = toBlocks(dim, tiles)
        def board = new Board(blocks)

        and:
        def expected = neighbors.collect {
            new Board(toBlocks(dim, it))
        }

        when:
        def adjacentBoards = board.neighbors()

        then:
        adjacentBoards == expected

        where:
        dim | tiles                       | neighbors
        2   | [1, 2, 0, 3]                | [[1, 2, 3, 0], [0, 2, 1, 3]]
        3   | [0, 1, 3, 4, 8, 2, 7, 6, 5] | [[1, 0, 3, 4, 8, 2, 7, 6, 5], [4, 1, 3, 0, 8, 2, 7, 6, 5]]
        3   | [1, 0, 3, 4, 8, 2, 7, 6, 5] | [[0, 1, 3, 4, 8, 2, 7, 6, 5],
                                             [1, 3, 0, 4, 8, 2, 7, 6, 5],
                                             [1, 8, 3, 4, 0, 2, 7, 6, 5]]
        3   | [1, 8, 3, 4, 2, 0, 7, 6, 5] | [[1, 8, 3, 4, 0, 2, 7, 6, 5],
                                             [1, 8, 0, 4, 2, 3, 7, 6, 5],
                                             [1, 8, 3, 4, 2, 5, 7, 6, 0]]
        3   | [1, 8, 3, 0, 2, 4, 7, 6, 5] | [[1, 8, 3, 2, 0, 4, 7, 6, 5],
                                             [0, 8, 3, 1, 2, 4, 7, 6, 5],
                                             [1, 8, 3, 7, 2, 4, 0, 6, 5]]
        3   | [1, 8, 3, 4, 0, 2, 7, 6, 5] | [[1, 8, 3, 0, 4, 2, 7, 6, 5],
                                             [1, 8, 3, 4, 2, 0, 7, 6, 5],
                                             [1, 0, 3, 4, 8, 2, 7, 6, 5],
                                             [1, 8, 3, 4, 6, 2, 7, 0, 5]]

    }

    @Unroll
    def 'it should return the corresponding neighbors for #input'() {

        given:
        def board = parseBoardFile("${input}.txt")

        and:
        def expected = neighbors.collect {
            new Board(toBlocks(board.dimension(), it))
        }

        when:
        def adjacentBoards = board.neighbors()

        then:
        adjacentBoards == expected

        where:
        input << ['puzzle4x4-01', 'puzzle4x4-14']
        neighbors << [
          [
            [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 11, 13, 14, 15, 12],
            [1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 8, 13, 14, 15, 12],
            [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0]
          ],
          [
            [1, 2, 8, 3, 5, 11, 6, 4, 10, 0, 7, 12, 9, 13, 14, 15],
            [1, 2, 8, 3, 0, 11, 6, 4, 5, 10, 7, 12, 9, 13, 14, 15],
            [1, 2, 8, 3, 5, 11, 6, 4, 9, 10, 7, 12, 0, 13, 14, 15]
          ]
        ]
    }

    @Unroll
    def 'it should generate a twin #similar with some random tiles swapped'() {

        given:
        def seed = StdRandom.seed
        StdRandom.seed = 1L

        when:
        def board = new Board(toBlocks(3, tiles))
        def twin = board.twin()

        then:
        twin == new Board(toBlocks(3, similar))

        cleanup:
        StdRandom.seed = seed

        where:
        tiles                       | similar
        [8, 1, 3, 4, 0, 2, 7, 6, 5] | [8, 7, 3, 4, 0, 2, 1, 6, 5]

    }

    def toBlocks = { dim, tiles ->
        def blocks = new int[dim][dim]
        def idx = 0
        dim.times { i ->
            dim.times { j ->
                blocks[i][j] = tiles[idx]
                idx += 1
            }
        }
        blocks
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

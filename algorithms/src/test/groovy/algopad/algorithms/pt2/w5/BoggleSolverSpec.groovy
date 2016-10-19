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

package algopad.algorithms.pt2.w5

import org.junit.Rule
import org.junit.rules.TestName
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static edu.princeton.cs.algs4.StdStats.mean
import static java.lang.System.nanoTime
import static java.util.concurrent.TimeUnit.NANOSECONDS

@SuppressWarnings('GroovyAccessibility')
class BoggleSolverSpec extends Specification {

    @Rule
    TestName name = new TestName()

    @Shared
    def solvers = [
      common: createSolver('common'),
      algs4 : createSolver('algs4'),
      yawl  : createSolver('yawl')
    ]

    @Shared
    def times = []

    def cleanupSpec() {
        def avg = mean(times as double[]) as long
        println "Average execution time : ${NANOSECONDS.toNanos(avg)}ns"
    }

    def start

    def setup() {
        start = nanoTime()
    }

    def cleanup() {
        def time = nanoTime() - start
        times << time
        println "Time to execute ${name.methodName} : ${time}ns"
    }

    def 'it should create an empty trie if dictionary contains only 2 letter words'() {

        given:
        def solver = createSolver('2letters')

        expect:
        solver.trie.next.findAll() == []

    }

    @Unroll
    def "it should properly score words given the dictionary '#dict'"() {

        given:
        def solver = solvers[dict]

        expect:
        scores.each { word, score ->
            assert solver.scoreOf(word) == score
        }

        where:
        dict     | scores
        'algs4'  | [ZZZ: 0, ACT: 1, ACID: 1, ACTOR: 2, ABACUS: 3, ABANDON: 5, ABANDONED: 11]
        'common' | [TOTO: 0, CAT: 1, FISH: 1, EVERY: 2, ACTUAL: 3, ACYCLIC: 5, ABRASION: 11]

    }

    @Unroll
    def "it should find the unique '#word' given the board '#board'"() {

        given:
        def solver = new BoggleSolver([word] as String[])
        def bb = new BoggleBoard(board as char[][])

        expect:
        solver.getAllValidWords(bb) as List == [word]

        where:
        word   | board
        'CAT'  | [['C', 'A', 'T']]
        'QUIT' | [['Q', 'I'], ['T', 'X']]

    }

    @Unroll
    def "it should find the word '#word' given the board '#board'"() {

        given:
        def solver = solvers[dict]
        def bb = createBoard(board)

        expect:
        word in solver.getAllValidWords(bb)

        where:
        dict     | word                                            | board
        'yawl'   | 'ANTIDISESTABLISHMENTARIANISMS'                 | 'antidisestablishmentarianisms'
        'common' | 'QUA'                                           | 'aqua' // We are not meant to find QUIT and AQUA
        'common' | 'COUSCOUS'                                      | 'couscous'
        'common' | 'THEN'                                          | 'diagonal'
        'yawl'   | 'DICHLORODIPHENYLTRICHLOROETHANES'              | 'dichlorodiphenyltrichloroethanes'
        'common' | 'DODO'                                          | 'dodo'
        'yawl'   | 'INCONSEQUENTIALLY'                             | 'inconsequentially'
        'common' | 'NOON'                                          | 'noon'
        'common' | 'DATA'                                          | 'horizontal'
        'yawl'   | 'QUINQUEVALENCIES'                              | 'quinquevalencies'
        'yawl'   | 'AZERTY'                                        | 'qwerty' // We are not meant to find QWERTY
        'yawl'   | 'TREE'                                          | 'vertical'
        'yawl'   | 'PNEUMONOULTRAMICROSCOPICSILICOVOLCANOCONIOSIS' | 'pneumonoultramicroscopicsilicovolcanoconiosis'
        'yawl'   | 'ESTRANGERS'                                    | 'estrangers'
        'yawl'   | 'ROTAVATOR'                                     | 'rotavator'

    }

    @Unroll
    def "it should calculate the score of '#score' given the board '#board' using '#dict'"() {

        given:
        def solver = solvers[dict]
        def bb = createBoard(board)

        when:
        def words = solver.getAllValidWords(bb)

        then:
        score == words.inject(0) { sum, word ->
            sum + solver.scoreOf(word)
        }

        where:
        dict    | score | board
        'algs4' | 33    | '4x4'
        'algs4' | 84    | 'q'
        'yawl'  | 0     | 'points0'
        'yawl'  | 1     | 'points1'
        'yawl'  | 2     | 'points2'
        'yawl'  | 3     | 'points3'
        'yawl'  | 4     | 'points4'
        'yawl'  | 5     | 'points5'
        'yawl'  | 100   | 'points100'
        'yawl'  | 200   | 'points200'
        'yawl'  | 300   | 'points300'
        'yawl'  | 400   | 'points400'
        'yawl'  | 500   | 'points500'
        'yawl'  | 750   | 'points750'
        'yawl'  | 777   | 'points777'
        'yawl'  | 1000  | 'points1000'
        'yawl'  | 1111  | 'points1111'
        'yawl'  | 1250  | 'points1250'
        'yawl'  | 1500  | 'points1500'
        'yawl'  | 2000  | 'points2000'
        'yawl'  | 4410  | 'points4410'
        'yawl'  | 4527  | 'points4527'
        'yawl'  | 4540  | 'points4540'
        'yawl'  | 13464 | 'points13464'
        'yawl'  | 26539 | 'points26539'

    }

    @Unroll
    def 'it should solve a random board'() {

        given:
        def solver = solvers[dict]

        when:
        solver.getAllValidWords(board)

        then:
        notThrown Throwable

        where:
        dict = 'yawl'
        board << (0..200).inject([]) { boards, val ->
            boards << new BoggleBoard()
            boards
        }
    }

    BoggleSolver createSolver(name) {
        def start = nanoTime()
        def file = "dictionary-${name}.txt"
        def words = getClass().getResource(file).readLines('US-ASCII')
        def dict = words.toArray new String[words.size()]
        def solver = new BoggleSolver(dict)
        println "Time to build solver with '$file' : ${NANOSECONDS.toMillis(nanoTime() - start)}ms"
        solver
    }

    BoggleBoard createBoard(name) {
        def file = "board-${name}.txt"
        def board = getClass().getResource(file).file
        new BoggleBoard(board)
    }
}

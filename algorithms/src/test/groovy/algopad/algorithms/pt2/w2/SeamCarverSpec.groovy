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

package algopad.algorithms.pt2.w2

import edu.princeton.cs.algs4.Picture
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit

import static java.lang.Runtime.runtime

@SuppressWarnings('GroovyAccessibility')
class SeamCarverSpec extends Specification {

    @Unroll
    def "it should support basic operations '#input'"() {

        given:
        def pic = readPicture("${input}.png")

        when:
        def carver = new SeamCarver(pic)

        then:
        carver.picture() == pic
        carver.width() == width
        carver.height() == height

        where:
        input       | width | height
        '1x1'       | 1     | 1
        '1x8'       | 1     | 8
        '3x4'       | 3     | 4
        '3x7'       | 3     | 7
        '4x6'       | 4     | 6
        '5x6'       | 5     | 6
        '6x5'       | 6     | 5
        '7x3'       | 7     | 3
        'chameleon' | 600   | 300

    }

    def 'it should throw an error if the indices are invalid'() {

        given:
        def pic = readPicture('1x1.png')
        def carver = new SeamCarver(pic)

        when:
        carver.energy(1, 0)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        carver.energy(0, 1)

        then:
        thrown(IndexOutOfBoundsException)

    }

    @Unroll
    def "it should throw an error if the #dir seam for '#input' are invalid"() {

        given:
        def pic = readPicture("${input}.png")
        def carver = new SeamCarver(pic)

        when:
        carver."$method" seam as int[]

        then:
        thrown(IllegalArgumentException)

        where:
        input   | method                 | seam
        '1x1'   | 'removeVerticalSeam'   | [0, 0]
        '1x1'   | 'removeHorizontalSeam' | [0, 0]
        '1x1'   | 'removeVerticalSeam'   | [0]
        '1x1'   | 'removeHorizontalSeam' | [0]

        '10x10' | 'removeVerticalSeam'   | [4, 3, 3, 3, 4, 5, 5, 4, 6, 7]
        '3x7'   | 'removeVerticalSeam'   | [-1, 0, 0, 1, 0, 0, 0]
        '7x3'   | 'removeVerticalSeam'   | [-1, 0, 0]
        '10x12' | 'removeVerticalSeam'   | [3, 4, 4, 3, 3, 4, 5, 4, 5, 5, 7, 8]
        '12x10' | 'removeVerticalSeam'   | [11, 11, 11, 11, 11, 13, 11, 11, 10, 9]
        '1x8'   | 'removeVerticalSeam'   | [-1, 0, 0, 0, 0, 0, 0, 1]

        '10x10' | 'removeHorizontalSeam' | [8, 8, 7, 7, 8, 9, 9, 7, 8, 8]
        '3x7'   | 'removeHorizontalSeam' | [-1, 0, 1]
        '7x3'   | 'removeHorizontalSeam' | [-1, 0, 1, 2, 2, 2, 2]
        '10x12' | 'removeHorizontalSeam' | [5, 6, 7, 8, 8, 9, 11, 11, 11, 11]
        '12x10' | 'removeHorizontalSeam' | [7, 7, 8, 7, 7, 8, 7, 6, 8, 9, 9, 9]
        '8x1'   | 'removeHorizontalSeam' | [-1, 0, 0, 0, 0, 0, 0, 0]

        dir = method == 'removeVerticalSeam' ? 'vertical' : 'horizontal'

    }

    def 'it should throw an error if any arguments are null'() {

        given:
        def pic = readPicture('1x1.png')
        def carver = new SeamCarver(pic)

        when:
        carver.removeVerticalSeam(null)

        then:
        thrown(NullPointerException)

        when:
        carver.removeHorizontalSeam(null)

        then:
        thrown(NullPointerException)

    }

    @Unroll
    def 'it should calculate the energy function using a dual-gradient function for #input'() {

        given:
        def pic = readPicture("${input}.png")
        def carver = new SeamCarver(pic)
        def matrix = createMatrix(energy)

        expect:
        matrix.eachWithIndex { row, y ->
            row.eachWithIndex { energy, x ->
                assert carver.energy(x, y).round(2) == energy
            }
        }

        where:
        input | energy
        '1x1' | [[1000.0]]
        '1x8' | [[1000.0], [1000.0], [1000.0], [1000.0], [1000.0], [1000.0], [1000.0], [1000.0]]
        '3x4' | [[1000.0, 1000.0, 1000.0],
                 [1000.0, 228.53, 1000.0],
                 [1000.0, 228.09, 1000.0],
                 [1000.0, 1000.0, 1000.0]]

        '3x7' | [[1000.0, 1000.0, 1000.0],
                 [1000.0, 294.32, 1000.0],
                 [1000.0, 236.17, 1000.0],
                 [1000.0, 325.15, 1000.0],
                 [1000.0, 251.36, 1000.0],
                 [1000.0, 279.64, 1000.0],
                 [1000.0, 1000.0, 1000.0]]

        '4x6' | [[1000.0, 1000.0, 1000.0, 1000.0],
                 [1000.0, 275.66, 173.21, 1000.0],
                 [1000.0, 173.21, 321.01, 1000.0],
                 [1000.0, 171.80, 195.63, 1000.0],
                 [1000.0, 270.93, 188.15, 1000.0],
                 [1000.0, 1000.0, 1000.0, 1000.0]]

        '5x6' | [[1000.0, 1000.0, 1000.0, 1000.0, 1000.0],
                 [1000.0, 300.07, 265.33, 289.67, 1000.0],
                 [1000.0, 311.94, 94.36, 309.61, 1000.0],
                 [1000.0, 295.49, 312.36, 193.36, 1000.0],
                 [1000.0, 264.36, 216.49, 299.43, 1000.0],
                 [1000.0, 1000.0, 1000.0, 1000.0, 1000.0]]

        '6x5' | [[1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0],
                 [1000.0, 237.35, 151.02, 234.09, 107.89, 1000.0],
                 [1000.0, 138.69, 228.10, 133.07, 211.51, 1000.0],
                 [1000.0, 153.88, 174.01, 284.01, 194.50, 1000.0],
                 [1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0]]

        '7x3' | [[1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0],
                 [1000.0, 237.12, 268.77, 218.95, 265.27, 292.37, 1000.0],
                 [1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0]]

    }

    double[][] createMatrix(List<List<BigDecimal>> energy) {
        def matrix = new double[energy.size()][energy[0].size()]
        energy.eachWithIndex { row, y ->
            row.eachWithIndex { col, x ->
                matrix[y][x] = col as double
            }
        }
        matrix
    }

    @Unroll
    def "it should calculate the vertical and horizontal seams for '#input'"() {

        given:
        def pic = readPicture("${input}.png")
        def carver = new SeamCarver(pic)

        expect:
        carver.findVerticalSeam() as List == vSeam
        carver.findHorizontalSeam() as List == hSeam

        where:
        input       | vSeam                                | hSeam
        '1x1'       | [0]                                  | [0]
        '1x8'       | [0, 0, 0, 0, 0, 0, 0, 0]             | [0]
        '3x4'       | [0, 1, 1, 0]                         | [1, 2, 1]
        '3x7'       | [0, 1, 1, 1, 1, 1, 0]                | [1, 2, 1]
        '4x6'       | [1, 2, 1, 1, 2, 1]                   | [1, 2, 1, 0]
        '5x6'       | [1, 2, 2, 3, 2, 1]                   | [2, 3, 2, 3, 2]
        '6x5'       | [3, 4, 3, 2, 1]                      | [1, 2, 1, 2, 1, 0]
        '7x3'       | [2, 3, 2]                            | [0, 1, 1, 1, 1, 1, 0]
        '7x10'      | [2, 3, 4, 3, 4, 3, 3, 2, 2, 1]       | [6, 7, 7, 7, 8, 8, 7]
        '10x10'     | [6, 7, 7, 7, 7, 7, 8, 8, 7, 6]       | [0, 1, 2, 3, 3, 3, 3, 2, 1, 0]
        '10x12'     | [5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5] | [8, 9, 10, 10, 10, 9, 10, 10, 9, 8]
        '12x10'     | [6, 7, 7, 6, 6, 7, 7, 7, 8, 7]       | [7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3]
        'diagonals' | [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0] | [0, 1, 1, 1, 1, 1, 1, 1, 0]
        'stripes'   | [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0] | [0, 1, 1, 1, 1, 1, 1, 1, 0]

    }

    @Unroll
    def "it should successfully remove the vertical seam for '#input'"() {

        given:
        def pic = readPicture("${input}.png")
        def carver = new SeamCarver(pic)

        when:
        carver.with {
            removeVerticalSeam findVerticalSeam()
        }

        then:
        carver.width() == old(carver.width()) - 1

        where:
        input << ['3x4', '8x1']

    }

    @Unroll
    def "it should successfully remove the horizontal seam for '#input'"() {

        given:
        def pic = readPicture("${input}.png")
        def carver = new SeamCarver(pic)

        when:
        carver.with {
            removeHorizontalSeam findHorizontalSeam()
        }

        then:
        carver.height() == old(carver.height()) - 1

        where:
        input << ['3x4']

    }

    @Unroll
    def 'it should successfully find seams in good time'() {

        given:
        println "initial memory = ${usedMemory}"
        def pic = readPicture("chameleon.png")
        def carver = new SeamCarver(pic)
        def time = System.nanoTime()

        when:
        def xDelta = 100
        xDelta.times {
            def seam = carver.findVerticalSeam()
            carver.removeVerticalSeam seam
        }

        then:
        carver.width() == old(carver.width()) - xDelta

        when:
        def yDelta = 50
        yDelta.times {
            def seam = carver.findHorizontalSeam()
            carver.removeHorizontalSeam seam
        }

        then:
        carver.height() == old(carver.height()) - yDelta

        and:
        def delta = System.nanoTime() - time
        TimeUnit.NANOSECONDS.toMillis(delta) < 3000

        cleanup:
        println "used memory = ${usedMemory}"

    }

    Picture readPicture(name) {
        def file = new File(getClass().getResource(name).file)
        new Picture(file)
    }

    private static long getUsedMemory() {
        def mb = 1024 * 1024
        (runtime.totalMemory() - runtime.freeMemory()) / mb
    }
}

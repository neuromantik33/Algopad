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

package algopad.algdsgn2.w3

import algopad.algdsgn2.w3.Knapsack.Item
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static Knapsack.calculateMaxValueWithCapacity
import static java.util.concurrent.TimeUnit.MILLISECONDS

class KnapsackSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'it should calculate the maximum value #value for a given knapsack data set'() {

        given:
        def input = KnapsackSpec.class.getResource(file)
        def (capacity, items) = parseKnapsackFile(input)

        expect:
        calculateMaxValueWithCapacity(items, capacity) == value

        cleanup:
        println "Time spent = ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        file                 | value
        'knapsack_test1.txt' | 51
        'knapsack_test2.txt' | 141
        'knapsack1.txt'      | 2493893
        // SLOW: 'knapsack_big.txt'   | 4243395

    }

    private static parseKnapsackFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def capacity = scanner.nextInt()
            def size = scanner.nextInt()
            def items = new Item[size]
            size.times { ix ->
                def value = scanner.nextInt()
                def weight = scanner.nextInt()
                items[ix] = new Item(value: value, weight: weight)
            }
            [capacity, items]
        }
    }
}

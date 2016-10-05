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

package algopad.algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static Fibonacci.calculateFibonacci
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given an integer #n, it should find the nth fibonacci number'() {

        expect:
        calculateFibonacci(n) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n  | val
        0  | 0
        1  | 1
        2  | 1
        3  | 2
        4  | 3
        5  | 5
        6  | 8
        7  | 13
        8  | 21
        9  | 34
        10 | 55
        11 | 89
        12 | 144
        13 | 233
        14 | 377
        15 | 610
        16 | 987
        17 | 1597
        18 | 2584
        19 | 4181
        20 | 6765
        21 | 10946
        22 | 17711
        23 | 28657
        24 | 46368
        25 | 75025
        26 | 121393
        27 | 196418
        28 | 317811
        29 | 514229
        30 | 832040
        31 | 1346269
        32 | 2178309
        33 | 3524578
        34 | 5702887
        35 | 9227465
        36 | 14930352
        37 | 24157817
        38 | 39088169
        39 | 63245986
        40 | 102334155
        41 | 165580141
        42 | 267914296
        43 | 433494437
        44 | 701408733
        45 | 1134903170
        46 | 1836311903
        47 | 2971215073
        48 | 4807526976
        49 | 7778742049
        50 | 12586269025

    }
}

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

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w2.FibonacciSum.getFibonacciSumLastDigit
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciSumSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given an integer #n, it should find the last digit of the sum Fib0 + Fib1 + · · · + Fibn'() {

        expect:
        getFibonacciSumLastDigit(n) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n            | val
        3            | 4
        100          | 5
        832564823476 | 3
        614162383528 | 9

    }
}

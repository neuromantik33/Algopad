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

import static algtb.w2.FibonacciPartialSum.getFibonacciPartialSumLastDigit
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciPartialSumSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def '''given two non-negative integers m and n where m < n,
           it should find the last digit of the sum Fibm + Fibm+1 + · · · + Fibn'''() {

        expect:
        getFibonacciPartialSumLastDigit(from, to) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        from | to          | val
        3    | 7           | 1
        10   | 10          | 5
        10   | 200         | 2
        1    | 10000000000 | 5

    }
}

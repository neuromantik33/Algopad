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

import static FibonacciHuge.getFibonacciHuge
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciHugeSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given two integers #n and #m, it should output Fib(n) mod m, that is the remainder of Fib(n) divided by m'() {

        expect:
        getFibonacciHuge(n, m) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n          | m     | val
        1          | 239   | 1
        15         | 2     | 0
        15         | 3     | 1
        12         | 4     | 0
        54         | 5     | 2
        61         | 10    | 1
        239        | 1000  | 161
        2816213588 | 30524 | 10249

    }
}

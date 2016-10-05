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

import static GCD.calculateGCD
import static java.util.concurrent.TimeUnit.MILLISECONDS

class GCDSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given two integers #a and #b, it should find their greatest common divisor'() {

        expect:
        calculateGCD(a, b) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        a          | b          | val
        18         | 35         | 1
        243532     | 0          | 243532
        1368       | 339        | 3
        2345       | 72         | 1
        55534      | 434334     | 2
        1406700    | 164115     | 23445
        28851538   | 1183019    | 17657
        30315475   | 24440870   | 31415
        2147483647 | 1145413628 | 1

    }
}

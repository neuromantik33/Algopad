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

import static LCM.calculateLCM
import static java.util.concurrent.TimeUnit.MILLISECONDS

class LCMSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'given two integers #a and #b, it should find their least common multiple #val'() {

        expect:
        calculateLCM(a, b) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        a        | b        | val
        6        | 8        | 24
        28851538 | 1183019  | 1933053046
        230984   | 3984134  | 460135603928
        18       | 35       | 630
        243532   | 0        | 0
        1368     | 339      | 154584
        2345     | 72       | 168840
        55534    | 434334   | 12060152178
        1406700  | 164115   | 9846900
        28851538 | 1183019  | 1933053046
        30315475 | 24440870 | 23585439550

    }
}

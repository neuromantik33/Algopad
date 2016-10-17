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

package algopad.algorithms.pt1.w1

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.Math.abs

class PercolationStatsSpec extends Specification {

    @Unroll
    def 'it should throw an error if N < 1 or T < 1 at construction'() {

        when:
        new PercolationStats(size, 1)

        then:
        thrown(IllegalArgumentException)

        where:
        size << [-1, 0]

    }

    @Unroll
    def 'given an #T number of trials, it should return a mean #mean and standard deviation #dev'() {

        given:
        def epsilon = 0.003
        def perc = new PercolationStats(150, T)

        expect:
        abs(perc.mean() - mean) < epsilon
        abs(perc.stddev() - stddev) < epsilon
        println "95% confidence interval = ${perc.confidenceLo()}, ${perc.confidenceHi()}"

        where:
        T   | mean  | stddev
        100 | 0.593 | 0.012

    }
}

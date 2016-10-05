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

package algopad.algdsgn2.w1

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algopad.algdsgn2.w1.GreedyJobScheduler.Heuristic.DIFF
import static algopad.algdsgn2.w1.GreedyJobScheduler.Heuristic.RATIO
import static java.util.concurrent.TimeUnit.MILLISECONDS

class GreedyJobSchedulerSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def '''it should calculate the sum of weighted completion times (using #heuristic)
           of the job schedule defined in #file'''() {

        given:
        def input = GreedyJobSchedulerSpec.class.getResource(file)
        def scheduler = new GreedyJobScheduler(input, heuristic)

        expect:
        scheduler.totalWeightedCompletionTime == time

        and:
        stopwatch.runtime(MILLISECONDS) < 1000

        where:
        file             | heuristic | time
        'jobs_test1.txt' | DIFF      | 31814
        'jobs_test1.txt' | RATIO     | 31814
        'jobs_test2.txt' | DIFF      | 61545
        'jobs_test2.txt' | RATIO     | 60213
        'jobs_test3.txt' | DIFF      | 688647
        'jobs_test3.txt' | RATIO     | 674634
        'jobs.txt'       | DIFF      | 69119377652
        'jobs.txt'       | RATIO     | 67311454237

    }
}

package algdsgn2.w1

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w1.GreedyJobScheduler.Heuristic.DIFF
import static algdsgn2.w1.GreedyJobScheduler.Heuristic.RATIO
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
        stopwatch.runtime(MILLISECONDS) < 500

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
package algdsgn2.w1

import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w1.GreedyJobScheduler.DIFF_HEURISTIC
import static algdsgn2.w1.GreedyJobScheduler.RATIO_HEURISTIC

class GreedyJobSchedulerSpec extends Specification {

    @Unroll
    def '''it should calculate the sum of weighted completion times (using #heuristic)
           of the job schedule defined in #file'''() {

        given:
        def input = GreedyJobSchedulerSpec.class.getResource(file)
        def scheduler = new GreedyJobScheduler(input, heuristic)

        expect:
        scheduler.totalWeightedCompletionTime == time

        where:
        file             | heuristic       | time
        'jobs_test1.txt' | DIFF_HEURISTIC  | 31814
        'jobs_test1.txt' | RATIO_HEURISTIC | 31814
        'jobs_test2.txt' | DIFF_HEURISTIC  | 61545
        'jobs_test2.txt' | RATIO_HEURISTIC | 60213
        'jobs_test3.txt' | DIFF_HEURISTIC  | 688647
        'jobs_test3.txt' | RATIO_HEURISTIC | 674634
        'jobs.txt'       | DIFF_HEURISTIC  | 69119377652

    }
}
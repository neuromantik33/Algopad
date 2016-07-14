package algdsgn2.w1

import spock.lang.Specification
import spock.lang.Unroll

class GreedyJobSchedulerSpec extends Specification {

    @Unroll
    def "it should calculate the sum of weighted completion time #time of the resulting schedule defined in '#file'"() {

        given:
        def input = GreedyJobSchedulerSpec.class.getResource(file)
        def scheduler = new GreedyJobScheduler(input)

        expect:
        scheduler.totalWeightedCompletionTime == time

        where:
        file             | time
        'jobs_test1.txt' | 31814
        'jobs_test2.txt' | 61545
        'jobs_test3.txt' | 688647
        'jobs.txt'       | 69119377652

    }
}
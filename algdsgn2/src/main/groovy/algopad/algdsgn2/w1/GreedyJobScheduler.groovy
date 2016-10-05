package algopad.algdsgn2.w1

import groovy.transform.CompileStatic
import groovy.transform.ToString

import static java.util.Arrays.sort

/**
 * Implementation of a greedy job scheduler which minimizes the sum of weight completion times given a
 * {@link GreedyJobScheduler.Heuristic}.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class GreedyJobScheduler {

    Job[] jobs

    GreedyJobScheduler(URL url, Heuristic heuristic) {
        parseJobsFile url
        sort jobs, heuristic
        updateWeightedCompletionTimes()
    }

    private parseJobsFile(URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            int len = scanner.nextInt()
            jobs = new Job[len]
            len.times {
                jobs[it] = new Job(scanner.nextInt(), scanner.nextInt())
            }
        }
    }

    private updateWeightedCompletionTimes() {
        def totalCompletionTime = 0L
        jobs.each { Job job ->
            totalCompletionTime += job.length
            job.weightedCompletionTime = job.weight * totalCompletionTime
        }
    }

    long getTotalWeightedCompletionTime() {
        jobs.inject(0L) { total, job -> total + job.weightedCompletionTime } as long
    }

    @ToString(includePackage = false)
    static class Job {

        final int weight
        final int length

        long weightedCompletionTime

        Job(final int weight, final int length) {
            this.weight = weight
            this.length = length
        }
    }

    enum Heuristic implements Comparator<Job> {

        DIFF({ Job o1, Job o2 ->
            def diff1 = o1.weight - o1.length
            def diff2 = o2.weight - o2.length
            def result = diff2 <=> diff1
            result ?: o2.weight <=> o1.weight
        }),

        RATIO({ Job o1, Job o2 ->
            def ratio1 = (double) o1.weight / o1.length
            def ratio2 = (double) o2.weight / o2.length
            ratio2 <=> ratio1
        })

        private final Closure<Integer> closure

        private Heuristic(Closure closure) {
            this.closure = closure
        }

        @Override
        int compare(final Job o1, final Job o2) {
            closure.call o1, o2
        }
    }
}

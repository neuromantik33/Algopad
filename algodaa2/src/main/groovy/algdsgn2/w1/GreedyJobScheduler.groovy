package algdsgn2.w1

import groovy.transform.CompileStatic
import groovy.transform.ToString

import static java.util.Arrays.sort

@CompileStatic
class GreedyJobScheduler {

    static final DIFF_HEURISTIC = { Job o1, Job o2 ->
        def diff1 = o1.weight - o1.length
        def diff2 = o2.weight - o2.length
        def result = Integer.compare(diff2, diff1)
        result != 0 ? result : Integer.compare(o2.weight, o1.weight)
    }

    static final RATIO_HEURISTIC = { Job o1, Job o2 ->
        def diff1 = (o1.weight / o1.length) as double
        def diff2 = (o2.weight / o2.length) as double
        def result = Double.compare(diff2, diff1)
        result != 0 ? result : Integer.compare(o2.weight, o1.weight)
    }

    Job[] jobs

    GreedyJobScheduler(URL url, heuristic) {
        parseJobsFile url
        sort jobs, heuristic as Comparator
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
}

package algdsgn2.w1

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
class GreedyJobScheduler {

    Job[] jobs
    int[] weightedCompletionTimes

    GreedyJobScheduler(URL url) {
        url.withReader {
            def scanner = new Scanner(it)
            def len = scanner.nextInt()
            jobs = new Job[len]
            len.times {
                jobs[it] = new Job(scanner.nextInt(), scanner.nextInt())
            }
        }
        Arrays.sort(jobs, new Comparator<Job>() {
            @Override
            int compare(final Job o1, final Job o2) {
                def diff1 = o1.weight - o1.length
                def diff2 = o2.weight - o2.length
                def result = Integer.compare(diff1, diff2)
                if (result == 0) {
                    return Integer.compare(o1.weight, o2.weight)
                } else {
                    return result
                }
            }
        })
        jobs.reverse(true)
        def time = 0
        weightedCompletionTimes = new int[jobs.length]
        for (int i = 0; i < weightedCompletionTimes.length; i++) {
            time += jobs[i].length
            weightedCompletionTimes[i] = jobs[i].weight * time
        }
    }

    long getTotalWeightedCompletionTime() {
        weightedCompletionTimes.inject(0L) { long total, int time ->
            total + time
        } as long
    }

    @ToString(includePackage = false)
    static class Job {

        final int weight
        final int length

        Job(final int weight, final int length) {
            this.weight = weight
            this.length = length
        }
    }
}

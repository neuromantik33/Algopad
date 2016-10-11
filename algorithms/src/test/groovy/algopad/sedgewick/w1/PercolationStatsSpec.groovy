package algopad.sedgewick.w1

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

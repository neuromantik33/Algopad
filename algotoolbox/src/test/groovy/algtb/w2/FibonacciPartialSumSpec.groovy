/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.FibonacciPartialSum.getFibonacciPartialSumLastDigit
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciPartialSumSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def '''given two non-negative integers m and n where m < n,
           it should find the last digit of the sum Fibm + Fibm+1 + · · · + Fibn'''() {

        expect:
        getFibonacciPartialSumLastDigit(from, to) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        from | to  | val
        3    | 7   | 1
        10   | 10  | 5
        10   | 200 | 2

    }
}

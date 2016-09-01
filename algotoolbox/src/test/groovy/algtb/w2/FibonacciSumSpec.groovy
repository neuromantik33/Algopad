/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.FibonacciSum.getFibonacciSumLastDigit
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciSumSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given an integer n, it should find the last digit of the sum Fib0 + Fib1 + · · · + Fibn'() {

        expect:
        getFibonacciSumLastDigit(n) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n            | val
        3            | 4
        100          | 5
        832564823476 | 3

    }
}

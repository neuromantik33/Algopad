/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.FibonacciLastDigit.getFibonacciLastDigit
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciLastDigitSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given an integer n, it should find the last digit of the nth fibonacci number ie Fn mod 10'() {

        expect:
        getFibonacciLastDigit(n) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n      | val
        3      | 2
        331    | 9
        327305 | 5

    }
}

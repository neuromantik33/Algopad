/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.FibonacciHuge.getFibonacciHuge
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciHugeSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given two integers n and m, it should output Fib(n) mod m, that is the remainder of Fib(n) divided by m'() {

        expect:
        getFibonacciHuge(n, m) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n          | m     | val
        1          | 239   | 1
        15         | 2     | 0
        15         | 3     | 1
        12         | 4     | 0
        54         | 5     | 2
        239        | 1000  | 161
        2816213588 | 30524 | 10249

    }
}

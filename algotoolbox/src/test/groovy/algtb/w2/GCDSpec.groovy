/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.GCD.calculateGCD
import static java.util.concurrent.TimeUnit.MILLISECONDS

class GCDSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given two integers a and b, it should find their greatest common divisor'() {

        expect:
        calculateGCD(a, b) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        a          | b          | val
        18         | 35         | 1
        243532     | 0          | 243532
        1368       | 339        | 3
        2345       | 72         | 1
        55534      | 434334     | 2
        1406700    | 164115     | 23445
        28851538   | 1183019    | 17657
        30315475   | 24440870   | 31415
        2147483647 | 1145413628 | 1

    }
}

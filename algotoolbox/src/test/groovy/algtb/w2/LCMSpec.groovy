/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.LCM.calculateLCM
import static java.util.concurrent.TimeUnit.MILLISECONDS

class LCMSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given two integers a and b, it should find their least common multiple'() {

        expect:
        calculateLCM(a, b) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        a        | b        | val
        6        | 8        | 24
        28851538 | 1183019  | 1933053046
        230984   | 3984134  | 460135603928
        18       | 35       | 630
        243532   | 0        | 0
        1368     | 339      | 154584
        2345     | 72       | 168840
        55534    | 434334   | 12060152178
        1406700  | 164115   | 9846900
        28851538 | 1183019  | 1933053046
        30315475 | 24440870 | 23585439550

    }
}

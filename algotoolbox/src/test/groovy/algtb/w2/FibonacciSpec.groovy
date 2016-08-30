/*
 *  algopad.
 */

package algtb.w2

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification

import static algtb.w2.Fibonacci.calculateFibonacci
import static java.util.concurrent.TimeUnit.MILLISECONDS

class FibonacciSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    def 'given an integer n, it should find the nth fibonacci number'() {

        expect:
        calculateFibonacci(n) == val

        and:
        stopwatch.runtime(MILLISECONDS) < 1500

        where:
        n  | val
        0  | 0
        1  | 1
        2  | 1
        3  | 2
        4  | 3
        5  | 5
        6  | 8
        7  | 13
        8  | 21
        9  | 34
        10 | 55
        11 | 89
        12 | 144
        13 | 233
        14 | 377
        15 | 610
        16 | 987
        17 | 1597
        18 | 2584
        19 | 4181
        20 | 6765
        21 | 10946
        22 | 17711
        23 | 28657
        24 | 46368
        25 | 75025
        26 | 121393
        27 | 196418
        28 | 317811
        29 | 514229
        30 | 832040
        31 | 1346269
        32 | 2178309
        33 | 3524578
        34 | 5702887
        35 | 9227465
        36 | 14930352
        37 | 24157817
        38 | 39088169
        39 | 63245986
        40 | 102334155
        41 | 165580141
        42 | 267914296
        43 | 433494437
        44 | 701408733
        45 | 1134903170
        46 | 1836311903
        47 | 2971215073
        48 | 4807526976
        49 | 7778742049
        50 | 12586269025

    }
}
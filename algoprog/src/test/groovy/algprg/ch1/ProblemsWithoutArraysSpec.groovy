package algprg.ch1

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.System.nanoTime

class ProblemsWithoutArraysSpec extends Specification {

    @Shared
    def rnd = new Random()

    def '''Consider two integer variables a and b. Write a program block that exchanges the values of a and b
           (i.e., the value of a becomes the value of b and vice versa).'''() {

        given:
        def x = 1
        def y = 2

        when:
        def tmp = x
        x = y
        y = tmp

        then:
        x == 2
        y == 1

    }

    def '''Solve the preceding problem without an auxiliary variable.
           (Assume all variables accept arbitrary integer values.)'''() {

        given:
        def x = 3
        def y = 5

        when:
        x += y
        y = x - y
        x -= y

        then:
        x == 5
        y == 3

    }

    @Unroll
    def '''Let #a be an integer and #n be a non-negative integer. Compute #a**#n. In other words, we ask for a
           program that does not change the values of #a and #n and assigns the value an to another variable (say, b).
           (The program may use other variables as well.)'''() {

        given:
        def pow = { int x, int y ->
            if (y == 0) { return 1 }
            def res = x as BigInteger
            def i = 1
            while (i < y) {
                res *= x
                i++
            }
            res
        }

        expect:
        benchmark("linear pow($a,$n)") { pow(a, n) } == a**n

        where:
        a                 | n
        -99               | 0
        81                | 5
        835               | 13
        18                | 12
        rnd.nextInt(1000) | rnd.nextInt(20).abs()

    }

    def '''Solve the preceding problem with the additional requirement that the number of execution steps
           should be of order log n (i.e., it should not exceed C log n for some constant C).'''() {

        given:
        def pow
        pow = { int x, int y ->

            if (y == 0) { return 1 }
            if (y == 1) { return x }
            if (y == 2) { return x * x }

            // y is even
            if (y % 2 == 0) {
                int exp = y / 2
                BigInteger val = pow(x, exp)
                return val * val
            }

            // y is odd
            int exp = (y - 1) / 2
            BigInteger val = pow(x, exp)
            x * val * val

        }

        /* Book solution
        def pow = { int x, int y ->
            if (y == 0) { return 1 }
            def res = 1 as BigInteger
            def exp = x as BigInteger
            int i = y
            while (i > 0) {
                if (i % 2 == 0) {
                    i /= 2
                    exp *= exp
                } else {
                    i -= 1
                    res *= exp
                }
            }
            res
        }*/

        expect:
        benchmark("log pow($a,$n)") { pow(a, n) } == a**n

        where:
        a                 | n
        -99               | 0
        81                | 5
        835               | 13
        18                | 12
        rnd.nextInt(1000) | rnd.nextInt(20).abs()

    }

    private static <V> V benchmark(String name, Closure<V> closure) {
        def start = nanoTime()
        V val = closure.call()
        println "Time spent on $name \t: ${nanoTime() - start}ns"
        val
    }
}
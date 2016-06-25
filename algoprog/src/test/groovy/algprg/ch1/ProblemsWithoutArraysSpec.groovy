package algprg.ch1

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.System.nanoTime

class ProblemsWithoutArraysSpec extends Specification {

    @Shared
    def random = new Random()

    private rand1000() { random.nextInt(1000).abs() }

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
        pow(a, n) == a**n

        where:
        a          | n
        -99        | 0
        81         | 5
        835        | 13
        18         | 12
        rand1000() | random.nextInt(20)

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
        pow(a, n) == a**n

        where:
        a          | n
        -99        | 0
        -81        | 5
        835        | 13
        18         | 12
        rand1000() | random.nextInt(20)

    }

    @Unroll
    def '''Two non-negative integers #a and #b are given. Compute the product a*b
           (only +, -, =, <> are allowed).'''() {

        given:
        def multiply = { int x, int y ->
            def res = 0
            for (int i = 0; i < y; i++) {
                res += x
            }
            res
        }

        expect:
        multiply(a, b) == a * b

        where:
        a          | b
        161        | 95
        774        | 892
        730        | 942
        rand1000() | rand1000()

    }

    @Unroll
    def '''Two non-negative integers #a and #b are given. Compute a + b. Only assignments of the form are allowed.
           variable1 = variable2
           variable = {number}
           variable1 = variable2 + 1'''() {

        given:
        def add = { int x, int y ->
            def res = x
            for (int i = 0; i < y; i++) {
                res += 1
            }
            res
        }

        expect:
        add(a, b) == a + b

        where:
        a          | b
        517        | 647
        123        | 908
        0          | 9
        rand1000() | rand1000()

    }

    @Unroll
    def '''A non-negative integer #a and positive integer #d are given. Compute the quotient q and the remainder r
           when #a is divided by #d. Do not use the operations div or mod.'''() {

        given:
        def quotientAndRemainder = { int x, int y ->
            assert x >= 0 && y > 0
            def quot = 0
            def rem = x
            while (y <= rem) {
                quot += 1
                rem -= y
            }
            [quot, rem]
        }

        expect:
        def (quot, rem) = quotientAndRemainder(a, d)
        quot == a / d as int
        rem == a % d

        where:
        a          | d
        0          | 2
        2          | 2
        15         | 5
        823        | 348
        rand1000() | rand1000() + 1

    }

    @Unroll
    def '''For a given non-negative integer #n, compute n!
           (n! is the product 1*2*3...n; we assume that 0! = 1).'''() {

        given:
        def fact = { x ->
            if (x < 2) { return 1 }
            def val = 1, i = x
            while (i > 0) {
                val *= i
                i -= 1
            }
            val
        }

        expect:
        fact(n) == (1..n).inject(1) { memo, n -> memo * n } || 1

        where:
        n << (0..50)

    }

    private static <V> V benchmark(String name, Closure<V> closure) {
        def start = nanoTime()
        V val = closure.call()
        println "Time spent on $name \t: ${nanoTime() - start}ns"
        val
    }
}
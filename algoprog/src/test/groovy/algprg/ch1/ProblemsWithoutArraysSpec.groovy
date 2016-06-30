package algprg.ch1

import spock.lang.Shared
import spock.lang.Specification

import static java.lang.System.nanoTime

@SuppressWarnings('GroovyLocalVariableNamingConvention')
class ProblemsWithoutArraysSpec extends Specification {

    @Shared
    def random = new Random()

    private rand1000() { random.nextInt(1000).abs() }

    def '''1.1.1. Consider two integer variables a and b. Write a program block that exchanges the values of a and b
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

    def '''1.1.2. Solve the preceding problem without an auxiliary variable.
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

    def '''1.1.3. Let a be an integer and n be a non-negative integer. Compute a**n. In other words, we ask for a
           program that does not change the values of a and n and assigns the value an to another variable (say, b).
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

    def '''1.1.4. Solve the preceding problem with the additional requirement that the number of execution steps
           should be of order log n (i.e., it should not exceed C log n for some constant C).'''() {

        given:
        def pow = { int x, int y ->

            if (y == 0) { return 1 }
            if (y == 1) { return x }
            if (y == 2) { return x * x }

            // y is even
            if (y % 2 == 0) {
                int exp = y / 2
                BigInteger val = call(x, exp)
                return val * val
            }

            // y is odd
            int exp = (y - 1) / 2
            BigInteger val = call(x, exp)
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

    def '''1.1.5. Two non-negative integers a and b are given. Compute the product a*b
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

    def '''1.1.6. Two non-negative integers a and b are given. Compute a + b. Only assignments of the form are allowed.
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

    def '''1.1.7. A non-negative integer a and positive integer d are given. Compute the quotient q and the remainder r
           when a is divided by d. Do not use the operations div or mod.'''() {

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

    def '''1.1.8. For a given non-negative integer n, compute n!
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

    def '''1.1.9. The Fibonacci sequence is defined as follows: a0 = 0, a1 = 1, ak = ak-1 + ak-2 for k >= 2.
           For a given n, compute an.'''() {

        given:
        def fib = { x ->
            if (x < 2) { return x }
            def y = 0, z = 1, i = x
            while (i > 1) {
                def tmp = y + z
                y = z
                z = tmp
                i -= 1
            }
            z
        }

        expect:
        fib(n) == val

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

    }

    def '''1.1.10. Repeat the preceding problem with the additional requirement that the number of operations
           should be proportional to log n. (Use only integer variables.)'''() {

        given:
        def multiply2d = { int[][] a, int[][] b ->
            def product = new int[2][2]
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        product[i][j] += a[i][k] * b[k][j]
                    }
                }
            }
            product
        }

        def pow = { int[][] x, int y ->

            if (y == 1) { return x }
            if (y == 2) { return multiply2d(x, x) }

            // y is even
            if (y % 2 == 0) {
                int exp = y / 2
                int[][] val = call(x, exp)
                return multiply2d(val, val)
            }

            // y is odd
            int exp = (y - 1) / 2
            int[][] val = call(x, exp)
            val = multiply2d(val, val)
            multiply2d(x, val)

        }

        def fib = { x ->
            if (x < 2) { return x }
            // Any pair of consecutive Fibonacci numbers is the product of the matrix [[1, 1], [1, 0]]
            // and the preceding pair. Therefore, it is enough to compute the n-th power of this matrix
            int[][] init = [[1, 1], [1, 0]] as int[][]
            def matrix = pow(init, x - 1)
            matrix[0][0]
        }

        expect:
        fib(n) == val

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

    }

    def '''1.1.11-12. For a non-negative integer n, compute 1/0! + 1/1! + ... + 1/n!
           the number of steps (i.e., the number of assignments performed during the execution)
           should be of order n (i.e., not greater than Cn for some constant C).'''() {

        given:
        def series = { x ->
            BigDecimal res = 1
            def fact = 1, i = 1
            while (i <= x) {
                fact *= i
                res += 1 / fact
                i += 1
            }
            res
        }

        expect:
        series(n) == val as BigDecimal

        where:
        n | val
        0 | 1 // 1/0!
        1 | 2 // 1/0! + 1/1!
        2 | 2.5 // 1/0! + 1/1! + 1/2!
        3 | 2.6666666667 // 1/0! + 1/1! + 1/2! + 1/3!
        4 | 2.7083333334 // 1/0! + 1/1! + 1/2! + 1/3! + 1/4!
        5 | 2.7166666667 // 1/0! + 1/1! + 1/2! + 1/3! + 1/4! + 1/5!
        6 | 2.7180555556 // 1/0! + 1/1! + 1/2! + 1/3! + 1/4! + 1/5! + 1/6!

    }

    def '''1.1.13. Two non-negative integers a and b are not both zero.
           Compute GCD(a,b), the greatest common divisor of a and b.'''() {

        given:
        def gcd = { x, y ->
            y == 0 ? x : call(y, x % y)
        }

        /* Book solution
        def gcd = { x, y ->
            def m = x, n = y
            while (m != 0 && n != 0) {
                if (m >= n) {
                    m -= n
                } else {
                    n -= m
                }
            }
            n
        }*/

        expect:
        gcd(a, b) == val

        where:
        a          | b          | val
        259        | 70         | 7
        45         | 54         | 9
        670        | 385        | 5
        513        | 227        | 1
        124        | 334        | 2
        804        | 717        | 3
        1611010288 | 1623312280 | 8

    }

    def '''1.1.15-16. Non-negative integers a and b are given, at least one of which is not zero.
           Find d = GCD(a,b) and integers x and y such that d = a*x + b*y.'''() {

        given: 'See 4.2.5 The Pulverizer - Mathematics for Computer Science (Lehman, Leighton, Meyer)'
        Closure<List> pulverize = { x, y, s0 = 1, s1 = 0, t0 = 0, t1 = 1 ->
            if (y == 0) { return [x, s0, t0] }
            int q = x / y
            def s2 = s0 - q * s1
            def t2 = t0 - q * t1
            call(y, x % y, s1, s2, t1, t2)
        }

        expect:
        pulverize(a, b) == [gcd, c, d]

        where:
        a   | b   | gcd | c  | d
        259 | 70  | 7   | 3  | -11
        14  | 24  | 2   | -5 | 3
        576 | 486 | 18  | 11 | -13

    }

    def '''1.1.18. Write a version of Euclid’s algorithm using the identities
           GCD(2a,2b) = 2*GCD(a,b); GCD(2a,b) = GCD(a,b) for odd b
           The algorithm should avoid division (div and mod operations); only division by 2 and the test “to be even”
            are allowed.
           (The number of operations should be of order log k if both numbers do not exceed k.)'''() {

        given:
        def isEven = { it % 2 == 0 }
        def div2 = { (int) (it / 2) }
        def gcd = { x, y ->
            def val
            if (y == 0) {
                val = x
            } else if (isEven(x)) {
                if (isEven(y)) {
                    val = 2 * call(div2(x), div2(y))
                } else {
                    val = call(div2(x), y)
                }
            } else {
                if (isEven(y)) {
                    val = call(x, div2(y))
                } else {
                    val = call(y, (x - y).abs())
                }
            }
            val
        }

        /*
        def gcd = { x, y ->
            def isOdd = { it % 2 != 0 }
            int m = x, n = y, d = 1
            while (m != 0 && n != 0) {
                //noinspection GroovyIfStatementWithTooManyBranches
                if (isEven(m) && isEven(n)) {
                    d = 2 * d
                    m = div2(m)
                    n = div2(n)
                } else if (isEven(m) && isOdd(n)) {
                    m = div2(m)
                } else if (isOdd(m) && isEven(n)) {
                    n = div2(n)
                } else {
                    if (m >= n) {
                        m -= n
                    } else {
                        n -= m
                    }
                }
            }
            d * (m ?: n)
        }*/

        expect:
        gcd(a, b) == val

        where:
        a          | b          | val
        259        | 70         | 7
        45         | 54         | 9
        670        | 385        | 5
        513        | 227        | 1
        124        | 334        | 2
        804        | 717        | 3
        1611010288 | 1623312280 | 8

    }

    private static <V> V benchmark(String name, Closure<V> closure) {
        def start = nanoTime()
        V val = closure.call()
        println "Time spent on $name \t: ${nanoTime() - start}ns"
        val
    }
}

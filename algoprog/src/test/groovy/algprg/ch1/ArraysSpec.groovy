package algprg.ch1

import spock.lang.Shared
import spock.lang.Specification

import static java.lang.Integer.MIN_VALUE

class ArraysSpec extends Specification {

    @Shared
    def random = new Random()

    def '''1.2.1. Fill the array x with zeros. (Write a program fragment whose execution guarantees
           that all values x[1]..x[n] are zero independent of the initial value of x.)'''() {

        given:
        def zero = { x ->
            x.eachWithIndex { int val, int i ->
                x[i] = 0
            }
        }

        when:
        zero(a)

        then:
        a == new int[a.length]

        where:
        a = randomArray(20)

    }

    def '''1.2.2. Count the number of zeros in an array x. (Write a program fragment that does not
           change the value of x and guarantees that the integer variable k contains the number of
           zeros among x[1]..x[n].)'''() {

        given:
        def countZeros = { x ->
            def num = 0
            x.eachWithIndex { int val, int i ->
                if (val == 0) {
                    num += 1
                }
            }
            num
        }

        expect:
        countZeros(a) == numZeros

        where:
        a               | numZeros
        [1, 1, 1]       | 0
        [0, 2, 0, 3, 0] | 3

    }

    def '''1.2.3. Not using assignment statement for arrays, write a program that is equivalent
           to the statement x = y.'''() {

        given:
        def copy = { int[] x ->
            def b = new int[x.length]
            x.eachWithIndex { int val, int i ->
                b[i] = val
            }
            b
        }

        expect:
        copy(a) == a

        where:
        a = (0..9) as int[]

    }

    def '1.2.4. Find the maximum value among x[1]..x[n].'() {

        given:
        def max = { x ->
            def mx = MIN_VALUE
            x.each {
                if (it > mx) { mx = it }
            }
            mx
        }

        expect:
        max(a) == Collections.max(a as List)

        where:
        a = randomArray(20)

    }

    def '''1.2.5. An array x: array[1..n] of integer is given such that x[1] <= x[2] <= ... <= x[n].
           Find the number of different elements among x[1]..x[n].'''() {

        given:
        def countDistinct = { x ->
            def last = null
            def num = 0
            x.each {
                if (it != last) {
                    //noinspection GrReassignedInClosureLocalVar
                    last = it
                    num += 1
                }
            }
            num
        }

        /* Book solution
        def countDistinct = { x ->
            def i = 1, k = 1
            while (i < x.size()) {
                if (x[i] != x[i - 1]) {
                    k += 1
                }
                i += 1
            }
            k
        }*/

        expect:
        countDistinct(a) == num

        where:
        a                  | num
        [1, 1, 2, 2, 3, 3] | 3
        (1..5)             | 5

    }

    private randomArray(int size) {
        def array = new int[size]
        size.times { i ->
            array[i] = random.nextInt(size)
        }
        array
    }
}
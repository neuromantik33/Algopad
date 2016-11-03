/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.geeks.math

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Math.sqrt

@See('http://www.geeksforgeeks.org/sieve-of-eratosthenes')
class SieveOfEratosthenes extends Specification {

    @Subject
    static def calculatePrimes = { int n ->

        boolean[] notPrime = new boolean[n + 1]
        int len = notPrime.length
        def disqualify = { notPrime[it] = true }

        // All even numbers after 2 are not prime
        4.step len, 2, disqualify

        def nextPrime = 3;
        def threshold = sqrt(n)
        while (nextPrime <= threshold) {

            // Remove all factors of this prime
            (nextPrime * 2).step len, nextPrime, disqualify

            // Find next prime
            nextPrime += 2
            while (nextPrime <= n &&
                   notPrime[nextPrime]) {
                nextPrime += 2
            }
        }

        // Copy the result
        def result = []
        2.upto(n) {
            !notPrime[it] && result << it
        }

        result

    }

    @Unroll
    def 'given a number #n, it should calculate all primes smaller, ie. #primes'() {

        expect:
        calculatePrimes(n) == primes

        where:
        n  | primes
        10 | [2, 3, 5, 7]
        20 | [2, 3, 5, 7, 11, 13, 17, 19]

    }
}

/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
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
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static algopad.geeks.math.SieveOfEratosthenes.calculatePrimes
import static java.lang.Math.sqrt

@See('http://www.geeksforgeeks.org/primality-test-set-1-introduction-and-school-method')
class CheckPrimality extends Specification {

    @Shared
    List primes = calculatePrimes(100)

    void setup() {
        Integer.metaClass.isFactor = {
            it % delegate == 0
        }
    }

    @Subject
    def checkPrimality = { int n ->
        if (n <= 1) { return false }
        if (n <= 3) { return true }
        if (2.isFactor(n) || 3.isFactor(n)) { return false }
        int limit = sqrt(n)
        for (int i = 5; i <= limit; i += 6) {
            if (i.isFactor(n) || (i + 2).isFactor(n)) {
                return false
            }
        }
        true
    }

    @Unroll
    def 'given a positive integer #n, it should check if the number is #prime'() {

        expect:
        checkPrimality(n) == isPrime

        where:
        n << (2..100)
        isPrime = primes.contains(n)
        prime = isPrime ? 'prime' : 'not prime'

    }
}

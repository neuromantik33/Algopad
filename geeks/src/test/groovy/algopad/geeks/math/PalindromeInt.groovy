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

@See('http://www.geeksforgeeks.org/check-if-a-number-is-palindrome')
class PalindromeInt extends Specification {

    @Subject
    def isPalindrome = { int n ->
        if (n < 10) { return true }
        def s = n.toString()
        int i = 0, mid = s.length() >> 1
        while (i < mid) {
            if (s[i] != s[-1 - i]) {
                return false
            }
            i++
        }
        true
    }

    @Unroll
    def 'given an integer #n, it should return true if the given number is palindrome, false otherwise.'() {

        expect:
        isPalindrome(n) == palindrome

        where:
        n     | palindrome
        1     | true
        10    | false
        11    | true
        12321 | true
        1451  | false

    }
}

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

package algopad.geeks.arrays

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.util.Collections.shuffle

@See('http://www.geeksforgeeks.org/find-the-missing-number')
class MissingNumber extends Specification {

    @Subject
    def missingNumber = { List a ->
        def n = a.size()
        int missing = 0
        1.upto(n) { int i ->
            missing += i - a[i - 1]
        }
        missing
    }

    @Unroll
    def '''given a list of numbers between 0 and #n, with exactly one number #missing removed,
           it should return the missing number'''() {

        given:
        def list = (0..n) - [missing]
        shuffle(list)

        expect:
        missingNumber(list) == missing

        where:
        n  | missing
        10 | 3
        50 | 32

    }
}

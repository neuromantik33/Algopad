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

@See('http://www.geeksforgeeks.org/given-an-array-of-numbers-arrange-the-numbers-to-form-the-biggest-number')
class BiggestNumber extends Specification {

    @Subject
    def biggestNumber = { List a ->

        def joinedCmp = { String o1, String o2 ->
            o1.size() == o2.size() ? o2 <=> o1 : (o2 + o1) <=> (o1 + o2)
        }
        def sorted = a.collect { "$it" as String }
                      .sort(joinedCmp)

        // Join and remove preceding zeros
        def value = sorted.join().replaceFirst(/^0*/, '')

        value ?: '0'

    }

    @Unroll
    def 'given #input, arrange them such that they form the largest number #largest'() {

        expect:
        biggestNumber(input) == largest

        where:
        input                        | largest
        [0, 0, 0]                    | '0'
        [3, 30, 34, 5, 9]            | '9534330'
        [54, 546, 548, 60]           | '6054854654'
        [1, 34, 3, 98, 9, 76, 45, 4] | '998764543431'

    }
}

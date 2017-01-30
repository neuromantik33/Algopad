/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

package algopad.geeks.strings

import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Narrative('https://en.wikipedia.org/wiki/Roman_numerals#Roman_numeric_system')
@See('http://www.geeksforgeeks.org/converting-roman-numerals-decimal-lying-1-3999')
class RomanNumerals extends Specification {

    def symbols = [
      I: 1,
      V: 5,
      X: 10,
      L: 50,
      C: 100,
      D: 500,
      M: 1000
    ]

    @Subject
    def romanToInt = { String s ->
        int n = s.length()
        if (n == 0) { return 0 }
        if (n == 1) { return symbols[s[0]] }
        int value = symbols[s[-1]]
        for (int i = n - 2; i >= 0; i--) {
            int val = symbols[s[i]]
            int prev = symbols[s[i + 1]]
            value += val >= prev ? val : -val
        }
        value
    }

    @Unroll
    def 'given a roman numeral "#numeral", it should return its corresponding decimal value #value'() {

        expect:
        romanToInt(numeral) == value

        where:
        numeral  | value
        ''       | 0
        'I'      | 1
        'XX'     | 20
        'IV'     | 4
        'IX'     | 9
        'XL'     | 40
        'XC'     | 90
        'CD'     | 400
        'CM'     | 900
        'MCMIV'  | 1904
        'MCMLIV' | 1954
        'MCMXC'  | 1990
        'MMXIV'  | 2014

    }

    def values = [
      1   : 'I',
      4   : 'IV',
      5   : 'V',
      9   : 'IX',
      10  : 'X',
      40  : 'XL',
      50  : 'L',
      90  : 'XC',
      100 : 'C',
      400 : 'CD',
      500 : 'D',
      900 : 'CM',
      1000: 'M',
    ] as TreeMap

    @Subject
    def intToRoman = { Integer value ->
        if (!value) { return '' }
        def s = new StringBuilder()
        values.descendingKeySet()
              .each { int num ->
            int div = value.intdiv(num)
            if (div > 0) {
                div.times { s.append values[num] }
                //noinspection GroovyAssignmentToMethodParameter
                value %= num
            }
        }
        s.toString()
    }

    @Unroll
    def 'given a decimal value #value, it should return its corresponding roman numeral "#numeral"'() {

        expect:
        intToRoman(value) == numeral

        where:
        value | numeral
        0     | ''
        1     | 'I'
        20    | 'XX'
        4     | 'IV'
        9     | 'IX'
        40    | 'XL'
        90    | 'XC'
        400   | 'CD'
        900   | 'CM'
        1904  | 'MCMIV'
        1954  | 'MCMLIV'
        1990  | 'MCMXC'
        2014  | 'MMXIV'

    }
}

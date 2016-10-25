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

package algopad.geeks.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/given-a-string-find-its-first-non-repeating-character')
class NonRepeatingCharSpec extends Specification {

    @Subject
    def findNonRepeatingChar = { String s ->
        def counts = [:] as LinkedHashMap
        for (char c in s) {
            counts[c] = counts.get(c, 0) + 1
        }
        counts.find { it.value == 1 }.key as char
    }

    @Unroll
    def 'given a string "#input", it should find the first non-repeating character #c in it'() {

        expect:
        findNonRepeatingChar(input) == c as char

        where:
        input           | c
        'GeeksforGeeks' | 'f'
        'GeeksQuiz'     | 'G'

    }
}

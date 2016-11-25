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

package algopad.interviewbit.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.Character.isWhitespace

@See('https://www.interviewbit.com/problems/length-of-last-word')
class LastWordLength extends Specification {

    @Subject
    def lastWordLength = { String s ->
        //noinspection GroovyAssignmentToMethodParameter
        s = s.trim()
        int len = 0
        for (int i = s.length() - 1; i >= 0; i--) {
            if (isWhitespace(s.charAt(i))) { break }
            len++
        }
        len
    }

    @Unroll
    def '''given a string "#s" consists of upper/lower-case alphabets and empty space characters,
           return the length #len of last word in the string. If the last word does not exist, return 0.
           Note: A word is defined as a character sequence consists of non-space characters only.'''() {

        expect:
        lastWordLength(s) == len

        where:
        s                                    | len
        ''                                   | 0
        'Hello World'                        | 5
        '  Some crazy phrase with padding  ' | 7

    }
}

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

package algopad.geeks.strings

import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Narrative('''
A string is said to be palindrome if reverse of the string is same as string.
For example, “abba” is palindrome, but “abbc” is not palindrome.''')
@See('http://quiz.geeksforgeeks.org/c-program-check-given-string-palindrome')
class IsPalindrome extends Specification {

    @Subject
    def isPalindrome = { String s ->

        int n = s.length()
        if (n < 2) { return true }
        def isLetter = { Character.isLetter(it as char) }

        int i = 0, j = n - 1
        while (i < j) {
            while (i < n && !isLetter(s[i])) { i++ }
            while (j >= 0 && !isLetter(s[j])) { j-- }

            if (i >= j) { break }
            if (!s[i].equalsIgnoreCase(s[j])) { return false }

            i += 1
            j -= 1
        }
        true
    }

    @Unroll
    def 'given a string "#s", it should verify that it #is palindrome.'() {

        expect:
        isPalindrome(s) == palindrome

        where:
        s                                | palindrome
        ''                               | true
        '.,'                             | true
        'z'                              | true
        'abba'                           | true
        'abbc'                           | false
        'A man, a plan, a canal: Panama' | true
        'race a car'                     | false

        is = palindrome ? 'is' : "isn't"

    }
}

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

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import static java.lang.Math.max

@See('http://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters')
class LongestSubstringNoRepeat extends Specification {

    /* @Subject Dumb naive quadratic solution, ie mine :(
    def longestSubstringLength = { String s ->
        int n = s.length()
        int maxLen = 0
        def seen = [] as HashSet
        for (int i = 0; i < n; i++) {
            seen << s[i]
            int j = i + 1
            while (j < n) {
                if (s[j] in seen) { break }
                seen << s[j]
                j++
            }
            maxLen = Math.max(maxLen, j - i)
            seen.clear()
        }
        maxLen
    }*/

    @Subject
    // Great solution and analysis here :
    // http://blog.gainlo.co/index.php/2016/10/07/facebook-interview-longest-substring-without-repeating-characters
    def longestSubstringLength = { String s ->
        int n = s.length()
        int start = 0, end = 0, longest = 0
        def seen = [] as HashSet
        while (end < n) {
            def ch = s[end]
            end += 1
            if (ch in seen) {
                while (start < end && ch != s[start]) {
                    start += 1
                }
            } else {
                seen << ch
                longest = max(longest, end - start)
            }
        }
        longest
    }

    @Unroll
    def 'given a string #s, it should find the length #len of the longest substring without repeating characters.'() {

        expect:
        longestSubstringLength(s) == len

        where:
        s            | len
        'a'          | 1
        'abcd'       | 4
        'abcabcbb'   | 3
        'ABDEFGABEF' | 6

    }
}

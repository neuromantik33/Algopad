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

@See('http://www.geeksforgeeks.org/anagram-substring-search-search-permutations')
class FindingAnagrams extends Specification {

    @Subject
    def findAnagramIndices = { String text, String pattern, int radix = Character.MAX_VALUE ->

        def n = text.length()
        def m = pattern.length()

        int[] countTW = new int[radix]
        int[] countP = new int[radix]
        def indices = []

        // Compares current pattern and text window char occurrences
        def saveIndexIfEqualCounts = { int idx ->
            for (int i = 0; i < radix; i++) {
                if (countTW[i] != countP[i]) {
                    return
                }
            }
            indices << (idx - m)
        }

        // Build occurrences for initial text window
        for (int i = 0; i < m; i++) {
            countTW[text[i]] += 1
            countP[pattern[i]] += 1
        }

        for (int i = m; i < n; i++) {
            saveIndexIfEqualCounts i
            countTW[text[i]] += 1 // Add current char to window
            countTW[text[i - m]] -= 1 // Remove first character from window
        }
        saveIndexIfEqualCounts n // Check last window

        indices

    }

    @Unroll
    def '''given a string "#text" and a pattern it should output all occurrences of "#pattern"
           and its permutations (anagrams) in the text'''() {

        expect:
        findAnagramIndices(text, pattern) == indices

        where:
        text         | pattern | radix | indices
        'BACDGABCDA' | 'ABCD'  | 128   | [0, 5, 6]
        'AAABABAA'   | 'AABA'  | 128   | [0, 1, 4]

    }
}

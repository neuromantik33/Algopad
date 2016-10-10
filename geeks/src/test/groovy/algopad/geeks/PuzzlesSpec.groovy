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

package algopad.geeks

import algopad.common.ds.CharStack
import spock.lang.See
import spock.lang.Specification
import spock.lang.Unroll

class PuzzlesSpec extends Specification {

    @Unroll
    @See('http://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression')
    def '''given an expression string #exp, it should examine whether the pairs and the orders of
           '{','}','(',')','[',']' are #correct.'''() {

        given:
        def areParenthesesBalanced = { String s ->
            def pairs = ['}': '{', ')': '(', ']': '[']
            def stack = new CharStack(s.length())
            for (char c in s) {
                def closeCh = pairs[c as String]
                if (closeCh) {
                    if (stack.empty || stack.pop() != closeCh as char) {
                        return false
                    }
                } else {
                    stack.push c
                }
            }
            true
        }

        expect:
        areParenthesesBalanced(exp) == balanced

        where:
        exp                | balanced
        '[()]{}{[()()]()}' | true
        '[(])'             | false
        '()]{}{[()()]()}'  | false

        correct = balanced ? 'balanced' : 'unbalanced'

    }

    @Unroll
    @See('http://www.geeksforgeeks.org/given-a-string-find-its-first-non-repeating-character')
    def 'given a string "#input", it should find the first non-repeating character #c in it'() {

        given:
        def findNonRepeatingChar = { String s ->
            def counts = [:] as LinkedHashMap
            for (char c in s) {
                counts[c] = counts.get(c, 0) + 1
            }
            counts.find { it.value == 1 }.key as char
        }

        expect:
        findNonRepeatingChar(input) == c as char

        where:
        input           | c
        'GeeksforGeeks' | 'f'
        'GeeksQuiz'     | 'G'

    }

    @Unroll
    @See('http://www.geeksforgeeks.org/segregate-even-and-odd-elements-in-a-linked-list')
    def '''given a linked list #list, it should modify the list such that all even numbers appear
           before all the odd numbers in the modified linked list, preserving relative order.'''() {

        given:
        def segregate = { LinkedList list ->
            int i = 0, j = 0
            def len = list.size()
            while (i < len) {
                def val = list[j]
                if (val % 2 != 0) {
                    list.remove j
                    list.add val
                } else {
                    j += 1
                }
                i += 1
            }
        }

        and:
        list = list as LinkedList
        segregated = segregated as LinkedList

        when:
        segregate list

        then:
        list == segregated

        where:
        list                               | segregated
        [8, 12, 10]                        | [8, 12, 10]
        [1, 3, 5, 7]                       | [1, 3, 5, 7]
        [8, 12, 10, 5, 4, 1, 6]            | [8, 12, 10, 4, 6, 5, 1]
        [17, 15, 8, 12, 10, 5, 4, 1, 7, 6] | [8, 12, 10, 4, 6, 17, 15, 5, 1, 7]

    }

    @Unroll
    @See('http://www.geeksforgeeks.org/stock-buy-sell')
    def '''given an array of the cost of a stock each day, it should find the max profit #maxProfit that can be made
           by buying and selling in those days.'''() {

        given:
        def findSellingIntervals = { List stocks ->
            def intervals = []
            def last = 0
            def len = stocks.size() - 1
            for (int i = 0; i < len; i++) {
                if (stocks[i] > stocks[i + 1]) {
                    if (last != i) {
                        intervals << [last, i]
                    }
                    last = i + 1
                }
            }
            if (last != len) {
                intervals << [last, len]
            }
            intervals
        }

        expect:
        findSellingIntervals(stocks) == intervals

        where:
        stocks                             | intervals
        (9..0)                             | []
        [100, 180, 260, 310, 40, 535, 695] | [[0, 3], [4, 6]]

        maxProfit = intervals.inject(0) { profit, interval ->
            def max = stocks[interval[1]]
            def min = stocks[interval[0]]
            profit + (max - min)
        }
    }

    @Unroll
    @See('http://www.geeksforgeeks.org/anagram-substring-search-search-permutations')
    def '''given a string "#text" and a pattern it should output all occurrences of "#pattern"
           and its permutations (anagrams) in the text'''() {

        // I never would have thought of this, this is completely lifted from website :/

        given:
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

        expect:
        findAnagramIndices(text, pattern) == indices

        where:
        text         | pattern | radix | indices
        'BACDGABCDA' | 'ABCD'  | 128   | [0, 5, 6]
        'AAABABAA'   | 'AABA'  | 128   | [0, 1, 4]

    }
}

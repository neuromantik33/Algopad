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

package algopad.interviewbit.hash

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/substring-concatenation')
class SubstringConcat extends Specification {

    @Subject
    def findSubstringConcat = { String s, List<String> words ->

        int n = s.length()
        int len = words[0]?.length() ?: 0
        int size = words.size() * len
        if (n == 0 || words.empty || size > n) {
            return []
        }

        def indices = []

        def hashCh = Character.&hashCode
        def hashStr = { String str, int from, int to ->
            long hash = 0
            for (int i = from; i < to; i++) {
                hash += hashCh(str.charAt(i))
            }
            hash
        }
        def verify = { int index ->
            def wordCnt = [:]
            words.each { wordCnt[it] = wordCnt.getOrDefault(it, 0) + 1 }
            for (int i = index; i < index + size; i += len) {
                def key = s.substring(i, i + len)
                if (!wordCnt.containsKey(key)) {
                    break
                }
                int cnt = wordCnt[key] - 1
                if (cnt == 0) {
                    wordCnt.remove key
                } else {
                    wordCnt[key] = cnt
                }
            }
            wordCnt.size() == 0
        }

        long targetHash = words.inject(0) { h, w -> h + hashStr(w, 0, w.length()) }
        long currentHash = hashStr(s, 0, size)
        for (int i = 0; i < n - size + 1; i++) {
            if (currentHash == targetHash && verify(i)) {
                indices << i
            }
            if (i + size < n) {
                currentHash -= hashCh(s.charAt(i))
                currentHash += hashCh(s.charAt(i + size))
            }
        }

        indices

    }

    @Unroll
    def '''given a string "#s" and words #words (all the same length), it should return
           all indices of substrings such that is a concatenation of each word exactly once
           and without any intervening characters.'''() {

        expect:
        findSubstringConcat(s, words) == indices

        where:
        s                    | words                               | indices
        ''                   | ['help']                            | []
        'somechars'          | []                                  | []
        'hello'              | ['hel', 'lo', 'hi']                 | []
        'barfoothefoobarman' | ['foo', 'bar']                      | [0, 9]
        'abbaccaaabcabbbccbabbccabbacabca' +
        'cbbaabbbbbaaabaccaacbccabcbababb' +
        'babccabacbbcabbaacaccccbaabcabaa' +
        'baaaabcaabcacabaa'  | ['cac', 'aaa', 'aba', 'aab', 'abc'] | [97]

    }
}

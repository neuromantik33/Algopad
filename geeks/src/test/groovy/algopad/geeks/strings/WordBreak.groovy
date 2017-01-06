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

@See('http://www.geeksforgeeks.org/dynamic-programming-set-32-word-break-problem')
class WordBreak extends Specification {

    @Subject
    def toPhrase = { String s, Set dict ->

        def words = []
        def find = { String str ->
            int i = 0, n = str.length()
            for (; i < n; i++) {
                def prefix = str[0..i]
                if (prefix in dict) {
                    words << prefix
                    break
                }
            }
            if (i + 1 < n) {
                call str[i + 1..-1]
            }
        }

        find(s)

        int totalChars = words.inject(0) { int total, String word ->
            total + word.size()
        }

        s.length() == totalChars ? words.join(' ') : false

    }

    @Unroll
    def '''given a string "#s" and a dictionary (#size words), it should break the
           string into a space-separated sequence of dictionary words'''() {

        expect:
        toPhrase(s, dict) == phrase

        where:
        s                  | phrase
        'ilike'            | 'i like'
        'ilikesamsung'     | 'i like sam sung'
        'ilikesamsungtogo' | false

        dict = ['i', 'like', 'sam', 'sung', 'samsung',
                'mobile', 'ice', 'cream', 'icecream',
                'man', 'go', 'mongo'] as Set
        size = dict.size()

    }
}

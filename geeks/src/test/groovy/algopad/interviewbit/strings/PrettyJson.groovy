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

package algopad.interviewbit.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/pretty-json')
class PrettyJson extends Specification {

    @Subject
    def prettyJson = { String s ->
        def st = new StringTokenizer(s, '{}[],', true)
        def lines = []
        def indent = ''
        while (st.hasMoreTokens()) {

            def token = st.nextToken().trim()
            if (token.empty) { continue }

            //noinspection GroovyIfStatementWithTooManyBranches
            if (token in ['{', '[']) {
                lines << indent + token
                indent += '\t'
            } else if (token in ['}', ']']) {
                indent = indent[0..<-1]
                lines << indent + token
            } else if (token == ',') {
                lines[-1] += ','
            } else {
                lines << indent + token
            }
        }
        lines
    }

    @Unroll
    def '''Pretty print a json object using proper indentation.
           - Every inner brace should increase one indentation to the following lines.
           - Every close brace should decrease one indentation to the same line and the following lines.
           - The indents can be increased with an additional "\\t"'''() {

        expect:
        prettyJson(input)
          .join('\n') == output.trim()
                               .replaceAll(' {4}', '\t') // Not very pretty :P

        where:
        input << [
          '{}',
          '{A:"B",C:{D:"E",F:{G:"H",I:"J"}}}',
          '["foo", {"bar":["baz",null,1.0,2]}]'
        ]
        output << [
          '''
{
}
''',
          '''
{
    A:"B",
    C:
    {
        D:"E",
        F:
        {
            G:"H",
            I:"J"
        }
    }
}
''',
          '''
[
    "foo",
    {
        "bar":
        [
            "baz",
            null,
            1.0,
            2
        ]
    }
]
''']

    }
}

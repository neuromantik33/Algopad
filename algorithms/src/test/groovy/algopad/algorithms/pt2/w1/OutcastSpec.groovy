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

package algopad.algorithms.pt2.w1

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class OutcastSpec extends Specification {

    @Shared
    def wordNet = new WordNet(filePathFor('synsets.txt'),
                              filePathFor('hypernyms.txt'))

    def filePathFor(name) {
        return getClass().getResource(name).file
    }

    @Unroll
    def "it should find the outcast listed in '#input'"() {

        given:
        def outcast = new Outcast(wordNet)
        def nouns = getClass().getResource("${input}.txt")
                              .readLines() as String[]

        expect:
        outcast.outcast(nouns) == farthest

        where:
        input       | farthest
        'outcast5'  | 'table'
        'outcast8'  | 'bed'
        'outcast11' | 'potato'
        'outcast29' | 'acorn'

    }
}

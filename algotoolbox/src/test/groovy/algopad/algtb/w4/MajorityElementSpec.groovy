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

package algopad.algtb.w4

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static MajorityElement.hasMajorityElement

@Narrative('''Majority rule is a decision rule that selects the alternative which has a majority,
              that is, more than half the votes''')
class MajorityElementSpec extends Specification {

    @Unroll
    def 'Given an array #ints, it should check whether it contains an int that appears more than n/2 times'() {

        expect:
        hasMajorityElement(ints as int[]) == hasMajority

        where:
        ints            | hasMajority
        [2, 3, 9, 2, 2] | true
        [1, 2, 3, 4]    | false
        [1, 2, 3, 1]    | false

    }
}

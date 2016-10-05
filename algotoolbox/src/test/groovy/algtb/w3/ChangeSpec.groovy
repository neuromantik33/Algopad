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

package algtb.w3

import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w3.Change.getChange

class ChangeSpec extends Specification {

    @Unroll
    def '''it should calculate the minimum number of coins needed to change the input value #m
           into coins with denominations 1, 5, and 10.'''() {

        expect:
        getChange(m) == numCoins

        where:
        m  | numCoins
        2  | 2
        28 | 6

    }
}

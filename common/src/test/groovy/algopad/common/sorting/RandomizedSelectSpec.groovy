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

package algopad.common.sorting

import algopad.common.misc.RandomOps
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class RandomizedSelectSpec extends Specification {

    @Shared
    def rnd = new Random()

    @Unroll
    def 'it should find the #kth smallest element from the list #a'() {

        new RandomizedSelect(rnd)(a, k) as int == val

        where:
        a                                | k  | val
        [2, 1, 4, 3, 2]                  | 3  | 2
        [72, 10, 91, 11, 64, 39, 57, 14, 57, 67, 18, 12,
         57, 10, 60, 82, 31, 51, 60, 68, 33, 24, 17, 98,
         29, 70, 44, 7, 3, 80, 88, 96, 78, 65, 15, 43,
         77, 62, 32, 60, 77, 65, 30, 77] | 22 | 57
        //noinspection GroovyNestedConditional
        kth = k == 1 ? '1st' : k == 2 ? '2nd' : k == 3 ? '3rd' : "kth"

    }

    def 'it should randomly select a random kth selection on a large list of ints'() {

        int size = 10000

        given:
        int k = 0
        int[] a = null
        use(RandomOps) {
            k = rnd.nextInt(size - 1) + 1
            a = rnd.nextInts(size)
        }

        expect:
        new RandomizedSelect(rnd)(a as List, k) as int == (a as List).sort()[k - 1]

    }
}

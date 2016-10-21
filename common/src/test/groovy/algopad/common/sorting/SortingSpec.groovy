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

package algopad.common.sorting

import algopad.common.DefaultStopwatch
import algopad.common.misc.RandomOps
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GrUnresolvedAccess')
class SortingSpec extends Specification {

    @Shared
    def rnd = new Random()

    @Rule
    Stopwatch stopwatch = new DefaultStopwatch()

    @Unroll
    def 'it should sort a random list using #name'() {

        given:
        def sorted = list.sort(false)

        when:
        sort(list)

        then:
        list == sorted

        where:
        sort << [
          new MergeSort(),
          new SelectionSort(),
          new InsertionSort(),
          new BubbleSort()
        ]
        list = use(RandomOps) { rnd.nextInts(20000) }.toList()
        /*list = use(RandomOps) {
            def l = (0..9).toList()
            rnd.shuffle(l)
            l
        }*/
        name = sort.class.simpleName

    }
}

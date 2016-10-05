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

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GrUnresolvedAccess')
class SortingSpec extends Specification {

    @Shared
    def random = new Random()

    @Unroll
    def 'it should sort a random list using #name'() {

        expect:
        algo.call(list) == list.sort(false)

        where:
        algo = MergeSort.&sort
        list = randomIntList(100)
        name = algo.owner.simpleName

    }

    private List randomIntList(int size) {
        def list = []
        size.times { list << random.nextInt() }
        list.trimToSize()
        list
    }
}

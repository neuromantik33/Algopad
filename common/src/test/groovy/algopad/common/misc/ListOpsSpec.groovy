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

package algopad.common.misc

import spock.lang.Specification
import spock.lang.Unroll

class ListOpsSpec extends Specification {

    @Unroll
    def '#list should be able to specify whether it is #sort'() {

        expect:
        use(ListOps) {
            list.isSorted() == sorted
        }

        where:
        list                  | sorted
        []                    | true
        [1]                   | true
        (0..9)                | true
        [3, 3, 3]             | true
        (9..0)                | false
        [1, 2, 3, 5, 4, 6, 7] | false

        sort = sorted ? 'sorted' : 'unsorted'

    }
}

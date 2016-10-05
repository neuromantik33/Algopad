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

class RandomOpsSpec extends Specification {

    @Unroll
    def 'it should randomly shuffle an array #items'() {

        given:
        items = items as Integer[]
        def rnd = new Random(seed)

        when:
        use(RandomOps) {
            rnd.shuffle items
        }

        then:
        items == shuffled as Integer[]

        where:
        seed | items   | shuffled
        100  | (1..20) | [16, 4, 13, 20, 15, 12, 17, 18, 1, 7, 3, 10, 14, 19, 5, 2, 11, 8, 9, 6]

    }
}

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

package algopad.algdsgn2.w6

import algopad.common.ds.BitArray
import groovy.transform.CompileStatic

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class Clause {

    int v1, v2
    boolean not1, not2

    boolean evaluate(final BitArray bits) {
        def val1 = bits[v1]
        if (not1) {
            val1 = !val1
        }
        def val2 = bits[v2]
        if (not2) {
            val2 = !val2
        }
        val1 || val2
    }

    String toString() {
        def s1 = (not1 ? '¬' : '') + "x($v1)"
        def s2 = (not2 ? '¬' : '') + "x($v2)"
        "{ $s1 ∨ $s2 }"
    }
}

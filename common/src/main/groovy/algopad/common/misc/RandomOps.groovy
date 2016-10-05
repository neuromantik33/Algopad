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

import groovy.transform.CompileStatic

/**
 * Some useful methods for working with {@link Random}s.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
@Category(Random)
class RandomOps {

    /**
     * Rearranges the elements of the specified <i>array</i> in uniformly random order.
     */
    static void shuffle(Random rnd, Object[] array) {
        int len = array.length
        (0..<len).each { int idx ->
            int randomIx = idx + rnd.nextInt(len - idx)
            array.swap idx, randomIx
        }
    }
}

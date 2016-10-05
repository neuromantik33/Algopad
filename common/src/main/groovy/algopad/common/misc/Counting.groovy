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

/**
 * Some useful methods for working various counting problems (permutations, combinations... etc).
 *
 * @author Nicolas Estrada.
 */
class Counting {

    /**
     * Chooses <i>k</i> items for the input <i>elements</i> and returns a list of unique combinations
     * in accordance with binomial coefficients.
     *
     * @return a list of list of elements of size k.
     */
    static <T> List<List<T>> chooseK(int k, List<T> elements) {

        def len = elements.size()
        def selections = new int[k]
        def results = []
        def choose = { int ix = 0 ->
            if (ix == selections.length) {
                results << selections.collect { int it -> elements[it] }
            } else {
                def start = ix > 0 ? selections[ix - 1] + 1 : 0
                for (int i = start; i < len; i++) {
                    selections[ix] = i
                    call ix + 1
                }
            }
            results
        }

        choose()

    }
}

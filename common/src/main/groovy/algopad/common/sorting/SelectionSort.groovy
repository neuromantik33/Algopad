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

import groovy.transform.CompileStatic

/**
 * Unoptimized selection sort implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class SelectionSort extends Closure<List> {

    SelectionSort() { super(null) }

    @SuppressWarnings('GroovyUnusedDeclaration')
    static List doCall(List list) {
        int k = 0, len = list.size()
        // def invariant = { ListOps.isSorted(list[0..k]) }
        while (k < len) {
            def idx = minIndex(list, k..len)
            list.swap k, idx
            // assert invariant()
            k++
        }
        list
    }

    private static int minIndex(List<? extends Comparable> list, IntRange unselected) {
        int index = unselected.from
        for (int i = index + 1; i < unselected.to; i++) {
            if (list[i] < list[index]) {
                index = i
            }
        }
        index
    }
}

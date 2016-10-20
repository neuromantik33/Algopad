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

import static java.util.Comparator.naturalOrder

/**
 * Unoptimized bubble sort implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class BubbleSort extends Closure<List> {

    final Comparator cmp

    BubbleSort(Comparator cmp = naturalOrder()) {
        super(null)
        this.cmp = cmp
    }

    @SuppressWarnings('GroovyUnusedDeclaration')
    List doCall(List list) {
        // int k = 0
        while (bubble(list)) {
            // assert ListOps.isSorted(list[(list.size() - k)..<list.size()])  /* Loop invariant */
            // k++
        }
        list
    }

    private boolean bubble(List list) {
        def len = list.size()
        def swapped = false
        for (int i = 1; i < len; i++) {
            if (cmp.compare(list[i], list[i - 1]) < 0) {
                swapped = true
                list.swap i, i - 1
            }
        }
        swapped
    }
}

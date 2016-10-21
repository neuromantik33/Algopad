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

import groovy.transform.CompileStatic

import static java.util.Comparator.naturalOrder

/**
 * Unoptimized insertion sort implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class InsertionSort extends Closure<List> {

    final Comparator cmp

    InsertionSort(Comparator cmp = naturalOrder()) {
        super(null)
        this.cmp = cmp
    }

    @SuppressWarnings('GroovyUnusedDeclaration')
    List doCall(List list) {
        int k = 1, len = list.size()
        while (k < len) {
            insert k, list
            // assert ListOps.isSorted(list[0..k]) /* Loop invariant */
            k++
        }
        list
    }

    private void insert(int k, List list) {
        for (int i = k; i > 0; i--) {
            if (cmp.compare(list[i], list[i - 1]) >= 0) {
                break
            }
            list.swap i, i - 1
        }
    }
}

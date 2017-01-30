/*
 * Algopad.
 *
 * Copyright (c) 2017 Nicolas Estrada.
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

package algopad.common

import groovy.transform.CompileStatic

import static java.util.Comparator.naturalOrder

/**
 * Unoptimized binary search algorithm.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class BinarySearch extends Closure<List> {

    final Comparator cmp

    BinarySearch(Comparator cmp = naturalOrder()) {
        super(null)
        this.cmp = cmp
    }

    @SuppressWarnings('GroovyUnusedDeclaration')
    <T> int doCall(List<T> list, T val) {
        // assert ListOps.isSorted(list)
        int n = list.size()
        if (n == 0) { return -1 }
        if (list[0] == val) { return 0 }
        search list, val
    }

    private <T> int search(final List<T> list, final T value) {
        int n = list.size()
        int lo = 0, hi = n - 1
        while (lo <= hi) {
            int mid = (lo + hi) >> 1
            if (value == list[mid]) {
                return mid
            } else if (cmp.compare(value, list[mid]) < 0) {
                hi = mid - 1
            } else {
                lo = mid + 1
            }
        }
        -1
    }
}

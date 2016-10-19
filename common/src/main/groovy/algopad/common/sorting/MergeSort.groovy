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
 * Unoptimized merge sort implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class MergeSort extends Closure<List> {

    final Comparator cmp
    private ArrayList buf = []

    MergeSort(Comparator cmp = naturalOrder()) {
        super(null)
        this.cmp = cmp
    }

    @SuppressWarnings('GroovyUnusedDeclaration')
    List doCall(List list) {

        int n = list.size()
        if (n < 2) { return list }

        buf.ensureCapacity n

        int i = n >> 1
        def left = call(list[0..<i])
        def right = call(list[i..<n])

        // assert invariant(left, right)
        def copy = merge(left, right)

        // Copy items back into the list
        n.times { int j -> list[j] = copy[j] }
        list

    }

    /*private static boolean invariant(List left, List right) {
        isSorted(left) && isSorted(right)
    }*/

    @SuppressWarnings('GroovyResultOfIncrementOrDecrementUsed')
    private List merge(List left, List right) {

        int n = left.size(), m = right.size()
        int i = 0, j = 0

        buf.clear()

        while (i < n && j < m) {
            if (cmp.compare(left[i], right[j]) <= 0) {
                buf << left[i]
                i += 1
            } else {
                buf << right[j]
                j += 1
            }
        }

        while (i < n) { buf << left[i++] }
        while (j < m) { buf << right[j++] }

        buf

    }
}

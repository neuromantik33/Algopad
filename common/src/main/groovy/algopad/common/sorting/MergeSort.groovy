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
 * Unoptimized merge sort implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class MergeSort extends Closure<List> {

    MergeSort() { super(null) }

    @SuppressWarnings('GroovyUnusedDeclaration')
    List doCall(List list) {

        int n = list.size()
        if (n < 2) { return list }

        int i = n >> 1
        def left = call(list[0..<i])
        def right = call(list[i..<n])

        merge left, right

    }

    @SuppressWarnings('GroovyResultOfIncrementOrDecrementUsed')
    private static List merge(List<? extends Comparable> left,
                              List<? extends Comparable> right) {

        int n = left.size(), m = right.size()
        int i = 0, j = 0

        def result = new ArrayList(n + m)

        while (i < n && j < m) {
            if (left[i] <= right[j]) {
                result << left[i]
                i += 1
            } else {
                result << right[j]
                j += 1
            }
        }

        while (i < n) { result << left[i++] }
        while (j < m) { result << right[j++] }

        result

    }
}

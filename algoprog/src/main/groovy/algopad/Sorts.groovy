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

package algopad

/**
 * Collection of unoptimized sorting algorithms.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings(['GroovyParameterNamingConvention', 'GroovyLocalVariableNamingConvention'])
class Sorts {

    static def mergeSort = { x ->

        def n = x.size()
        if (n < 2) { return x }

        int i = n / 2
        def a = call(x[0..<i])
        def b = call(x[i..<n])

        merge(a, b)

    }

    private static merge(a, b) {

        int n = a.size(), m = b.size()
        int i = 0, j = 0

        def result = []
        result.ensureCapacity n + m

        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                result << a[i]
                i += 1
            } else {
                result << b[j]
                j += 1
            }
        }

        while (i < n) {
            result << a[i]
            i += 1
        }

        while (j < m) {
            result << b[j]
            j += 1
        }

        result

    }
}

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
 * Unoptimized randomized select implementation.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class RandomizedSelect extends Closure<List> {

    final Comparator cmp
    final Random rnd

    RandomizedSelect(Random rnd, Comparator cmp = naturalOrder()) {
        super(null)
        this.rnd = rnd
        this.cmp = cmp
    }

    @SuppressWarnings('GroovyUnusedDeclaration')
    def doCall(List list, int k) {
        int n = list.size()
        assert n > 0
        if (n == 1) { list[0] }

        int q = partition(list)
        final kth
        if (k == q + 1) {
            kth = list[q]
        } else if (k < q + 1) {
            kth = doCall(list.subList(0, q), k)
        } else {
            kth = doCall(list.subList(q + 1, n), k - q - 1)
        }
        kth
    }

    private int partition(List list) {
        int n = list.size()
        assert n > 0
        if (n == 1) { return 0 }
        def swap = { int i, int j ->
            if (i != j) { list.swap(i, j) }
        }

        // Choose pivot and move it to the end of the list
        int idx = rnd.nextInt(n)
        def pivot = list[idx]
        swap idx, n - 1

        // All values <= pivot are moved to the front of
        // the list and pivot inserted just after them
        int rank = 0
        for (int i = 0; i < n - 1; i++) {
            if (cmp.compare(list[i], pivot) <= 0) {
                swap i, rank
                rank++
            }
            // assert list[0..<rank].every { cmp.compare(it, pivot) <= 0 } /* Loop invariant */
        }

        list.swap rank, n - 1
        rank
    }
}

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

package algopad.interviewbit.linkedlists

import algopad.common.ds.LinkedList
import algopad.common.ds.LinkedList.Node
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/reorder-list')
class ReorderLists extends Specification {

    @SuppressWarnings('GroovyAccessibility')
    @Subject
    def reorder = { LinkedList ll ->

        int n = ll.size()
        if (n < 3) { return }

        def nil = ll.sentinel
        def a = [] as List<Node>
        def node = nil.next
        while (node != nil) {
            a << node
            node = node.next
        }

        int i = 0, j = n - 1
        while (i < j - 1) {
            def prev = a[i]
            def last = a[j]
            last.next = prev.next
            prev.next = last
            a[j - 1].next = nil
            i++
            j--
        }
    }

    @Unroll
    def '''given a singly-linked list #list, l0->l1->...ln-1->ln, it should reorder it
           to l0->ln->l1->ln-1->..., ie. #reordered.
           It should do this in-place without altering node values'''() {

        given:
        def ll = list as LinkedList

        when:
        reorder(ll)

        then:
        ll == reordered

        where:
        list  | reordered
        1..2  | 1..2
        1..4  | [1, 4, 2, 3]
        1..7  | [1, 7, 2, 6, 3, 5, 4]
        1..10 | [1, 10, 2, 9, 3, 8, 4, 7, 5, 6]

    }
}

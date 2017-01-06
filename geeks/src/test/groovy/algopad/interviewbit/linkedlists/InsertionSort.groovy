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

import groovy.transform.EqualsAndHashCode
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('https://www.interviewbit.com/problems/insertion-sort-list')
class InsertionSort extends Specification {

    // My solution with O(n) space
    /*@Subject
    def sort = { Node head ->
        List<Node> nodes = []
        def node = head
        while (node != null) {
            nodes << node
            node = node.next
        }
        if (nodes.size() < 2) {
            return head
        }

        int k = 1, len = nodes.size()
        while (k < len) {
            for (int i = k; i > 0; i--) {
                def curr = nodes[i]
                def prev = nodes[i - 1]
                if (curr.val >= prev.val) {
                    break
                }
                final int tmp = curr.val
                curr.val = prev.val
                prev.val = tmp
            }
            k++
        }
        nodes[0]
    }*/

    // O(1) space
    @Subject
    def sort = { Node head ->
        if (!head || !head.next) {
            return head
        }
        Node sorted = null
        Node list = head
        while (list) {
            def curr = list
            list = list.next
            // Insert at head of the sorted list
            if (!sorted || sorted.val > curr.val) {
                curr.next = sorted
                sorted = curr
            }
            // Insert at the current point in list
            else {
                def node = sorted
                while (node) {
                    def tmp = node
                    node = node.next
                    if (!tmp.next || tmp.next.val > curr.val) {
                        tmp.next = curr
                        curr.next = node
                        break
                    }
                }
            }
        }
        sorted
    }

    @Unroll
    def 'it should sort the list #list using insertion sort'() {

        given:
        def link = { List list ->
            if (!list) { return null }
            def head = new Node(val: list[0])
            def node = head
            list.tail().each {
                def next = new Node(val: it)
                node.next = next
                node = next
            }
            head

        }
        def node = link(list)

        when:
        def head = sort(node)

        then:
        head == link(ordered)

        where:
        list            | ordered
        []              | []
        10..10          | [10]
        9..0            | 0..9
        [1, 2, 3, 7, 4] | [1, 2, 3, 4, 7]

    }

    @EqualsAndHashCode
    class Node {
        int val
        Node next

        @Override
        String toString() {
            next ? "$val->$next" : "$val"
        }
    }
}

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

package algopad.geeks.linkedlists

import spock.lang.See
import spock.lang.Specification

@See('http://www.geeksforgeeks.org/write-a-c-function-to-detect-loop-in-a-linked-list')
class DetectCycle extends Specification {

    @See('https://en.wikipedia.org/wiki/Cycle_detection#Tortoise_and_hare')
    def findCycle(Node root) {
        Node slow = root, fast = root
        while (slow && fast && fast.next) {
            slow = slow.next
            fast = fast.next.next
            if (slow == fast) {
                // Very subtle trick here...
                fast = root
                while (slow != fast) {
                    slow = slow.next
                    fast = fast.next
                }
                return fast
            }
        }
        null
    }

    def '''given a linked list, check if the the linked list has loop or not.
           it should return the node where the cycle begins, or null if there is no cycle.'''() {

        given:
        Node parent = new Node(val: 0)
        Node node = parent
        [1, 2, 3, 4].each {
            node.next = new Node(val: it)
            node = node.next
        }

        expect: 'There are no cyles'
        findCycle(parent) == null

        when: 'Create a cycle at the 2nd node'
        node.next = parent.next

        then:
        findCycle(parent) == parent.next

    }

    class Node {
        int val
        Node next

        @Override
        String toString() { "$val" }
    }
}

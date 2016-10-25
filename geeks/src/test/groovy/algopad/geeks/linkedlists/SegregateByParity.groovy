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

package algopad.geeks.linkedlists

import algopad.common.ds.LinkedList
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/segregate-even-and-odd-elements-in-a-linked-list')
class SegregateByParity extends Specification {

    @Subject
    def segregate = { LinkedList list ->
        def n = list.size()
        def last = list.nodeIterator()[-1]
        def itr = list.nodeIterator()
        while (n > 0) {
            //noinspection ChangeToOperator
            def node = itr.next()
            if (node.value % 2 != 0) {
                itr.remove()
                last = list.insertAfter(node.value, last)
            }
            n -= 1
        }
    }

    @Unroll
    def '''given a linked list #list, it should modify the list such that all even numbers appear
           before all the odd numbers in the modified linked list, preserving relative order.'''() {

        given:
        list = list as LinkedList

        when:
        segregate list

        then:
        list == segregated

        where:
        list                               | segregated
        [8, 12, 10]                        | [8, 12, 10]
        [1, 3, 5, 7]                       | [1, 3, 5, 7]
        [8, 12, 10, 5, 4, 1, 6]            | [8, 12, 10, 4, 6, 5, 1]
        [17, 15, 8, 12, 10, 5, 4, 1, 7, 6] | [8, 12, 10, 4, 6, 17, 15, 5, 1, 7]

    }

}

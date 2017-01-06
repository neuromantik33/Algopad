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

import algopad.common.ds.LinkedList
import algopad.common.ds.LinkedList.Node
import algopad.common.ds.LinkedStack
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@See('http://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome')
class PalindromeList extends Specification {

    @Subject
    def isPalindrome = { LinkedList list ->
        int n = list.size()
        if (n < 2) { return false }

        def stack = new LinkedStack<Node>()
        list.nodeIterator()
            .each stack.&push

        //noinspection GroovyAccessibility
        def head = list.sentinel.next
        int k = stack.size() - 1
        def n1 = head
        def n2 = stack.pop()
        while (k > 0) {
            if (!n1.value.is(n2.value)) {
                return false
            }
            n1 = n1.next
            n2 = stack.pop()
            k--
        }
        true
    }

    @Unroll
    def 'Given a singly-linked list #list, it should determine that the list #is a palindrome.'() {

        given:
        def ll = new LinkedList()
        list.each { ll << it }

        expect:
        isPalindrome(ll) == palindrome

        where:
        list                      | palindrome
        ['a']                     | false
        ['a', 'a']                | true
        ['r', 'a', 'd']           | false
        ['r', 'a', 'd', 'a']      | false
        ['r', 'a', 'd', 'a', 'r'] | true

        is = palindrome ? 'is' : "isn't"

    }
}

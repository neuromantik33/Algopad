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

package algopad.common.ds;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

import algopad.common.ds.LinkedList.Node;

/**
 * An unbounded singly-linked list backed implementation of a {@link Queue}.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("ReturnOfNull")
public class LinkedQueue<E> extends AbstractQueue<E> {

    private final LinkedList<E> ll   = new LinkedList<>();
    private       Node<E>       tail = ll.sentinel;

    @Override
    public Iterator<E> iterator() {
        return ll.iterator();
    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean offer(final E e) {
        tail = ll.insertAfter(e, tail);
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) { return null; }
        resetTail();
        final E first = ll.getFirst();
        ll.removeAfter(ll.sentinel);
        return first;
    }

    private void resetTail() {
        //noinspection ObjectEquality
        if (ll.sentinel.getNext() == tail) {
            tail = ll.sentinel;
        }
    }

    @Override
    public E peek() {
        return isEmpty() ? null : ll.getFirst();
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(final Object obj) {
        return ll.equals(obj);
    }

    @Override
    public int hashCode() {
        return ll.hashCode();
    }
}

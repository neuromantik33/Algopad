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

import java.util.AbstractCollection;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * An unbounded singly-linked list backed implementation of a {@link Stack}.
 *
 * @author Nicolas Estrada.
 */
public class LinkedStack<E> extends AbstractCollection<E> implements Stack<E> {

    private final LinkedList<E> ll = new LinkedList<>();

    @Override
    public E peek() {
        verifySize();
        return ll.getFirst();
    }

    @Override
    public E pop() {
        verifySize();
        final E elmt = ll.getFirst();
        ll.removeAfter(ll.sentinel);
        return elmt;
    }

    private void verifySize() {
        if (ll.size == 0) { throw new EmptyStackException(); }
    }

    @Override
    public void push(final E elmt) {
        ll.insertAfter(elmt, ll.sentinel);
    }

    @Override
    public Iterator<E> iterator() {
        return ll.iterator();
    }

    @Override
    public int size() {
        return ll.size();
    }
}

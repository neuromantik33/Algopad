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

package algopad.algorithms.pt1.w2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a
 * stack and a queue that supports adding and removing items from either the
 * front or the back of the data structure.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "TypeParameterNamingConvention", "NonBooleanMethodNameMayNotStartWithQuestion" })
public class Deque<Item> implements Iterable<Item> {

    private       int        size;
    private final Node<Item> sentinel;

    public Deque() {
        sentinel = new Node<>(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * @return {@code true} if the deque is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return the number of items on the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Inserts the specified element <i>item</i> at the head of this deque.
     */
    public void addFirst(final Item item) {
        final Item elmt = verifyNotNull(item);
        final Node<Item> node = new Node<>(elmt);
        final Node<Item> oldHead = sentinel.next;
        node.next = oldHead;
        oldHead.prev = node;
        sentinel.next = node;
        node.prev = sentinel;
        size++;
    }

    /**
     * Inserts the specified element <i>item</i> at the tail of this deque.
     */
    public void addLast(final Item item) {
        final Item elmt = verifyNotNull(item);
        final Node<Item> node = new Node<>(elmt);
        final Node<Item> oldTail = sentinel.prev;
        node.prev = oldTail;
        oldTail.next = node;
        sentinel.prev = node;
        node.next = sentinel;
        size++;
    }

    private Item verifyNotNull(final Item item) {
        if (item == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException("Item is null");
        }
        return item;
    }

    /**
     * Retrieves and removes an item from the head of the deque.
     *
     * @return the head of this deque.
     */
    public Item removeFirst() {
        verifySize();
        final Node<Item> removed = removeNode(sentinel.next);
        return removed.elmt;
    }

    /**
     * Retrieves and removes an item from the tail of the deque.
     *
     * @return the tail of this deque.
     */
    public Item removeLast() {
        verifySize();
        final Node<Item> removed = removeNode(sentinel.prev);
        return removed.elmt;
    }

    private Node<Item> removeNode(final Node<Item> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node;
    }

    private void verifySize() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    /**
     * @return an {@link Iterator} over the elements in order from front to end.
     */
    @Override
    public Iterator<Item> iterator() {
        //noinspection ReturnOfInnerClass
        return new DequeIterator();
    }

    /**
     * {@link Iterator} implementation which traverses over the elements in
     * order from front to end.
     */
    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private final class DequeIterator implements Iterator<Item> {

        private Node<Item> node;

        private DequeIterator() {
            node = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return !node.equals(sentinel);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final Node<Item> next = node;
            node = node.next;
            return next.elmt;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private static final class Node<E> {

        private final E       elmt;
        private       Node<E> prev;
        private       Node<E> next;

        private Node(final E elmt) {
            this.elmt = elmt;
        }
    }
}

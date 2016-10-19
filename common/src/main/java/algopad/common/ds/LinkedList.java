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
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.hash;

/**
 * Simple barebones implementation of a singly-linked list. Internals are exposed for operations easy extension.<br>
 * Implementation is a actually a circular linked list with sentinel and disallows {@code null} values.<br>
 * It is unoptimized for bulk operations such as {@link #addAll(Collection)}, {@link #containsAll(Collection)} etc.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("ObjectEquality")
public class LinkedList<E> extends AbstractCollection<E> {

    protected final Node<E> sentinel;
    protected       int     size;

    public LinkedList() {
        this.sentinel = new Node<>(null);
        sentinel.next = sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(final Object o) {
        // More efficient than with iterators
        Node<E> node = sentinel.next;
        while (node != sentinel &&
               !node.value.equals(o)) {
            node = node.next;
        }
        return node != sentinel;
    }

    @Override
    public boolean add(final E e) {

        if (e == null) {
            throw new IllegalArgumentException();
        }

        final Node<E> node = new Node<>(e);
        final Node<E> head = sentinel.next;
        if (sentinel == head) {
            sentinel.next = node;
        } else {
            Node<E> prev = sentinel.next;
            while (prev.next != sentinel) {
                prev = prev.next;
            }
            prev.next = node;
        }

        node.next = sentinel;
        size++;
        return true;

    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<Node<E>> it = new NodeItr();
        //noinspection ReturnOfInnerClass
        return new Iterator<E>() {
            @Override
            public boolean hasNext() { return it.hasNext(); }

            @Override
            public E next() { return it.next().value; }

            @Override
            public void remove() { it.remove(); }
        };
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(final Object obj) {

        if (this == obj) { return true; }
        if (!(obj instanceof Collection)) { return false; }

        final Collection ctn = (Collection) obj;
        if (size() != ctn.size()) { return false; }

        final Iterator<E> it1 = iterator();
        final Iterator it2 = ctn.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            final E o1 = it1.next();
            final Object o2 = it2.next();
            if (!Objects.equals(o1, o2)) {
                return false;
            }
        }

        return true;

    }

    @Override
    public int hashCode() {
        return hash(toArray());
    }

    @Override
    public String toString() {
        return sentinel.next.toString();
    }

    /**
     * @return the first element of the list, or {@code null} if the list is empty.
     */
    public E getFirst() {
        return sentinel.next.value;
    }

    /**
     * Reverses the list in-place.
     */
    public void reverse() {
        if (size < 2) {
            return;
        }
        Node<E> n1 = sentinel.next;
        Node<E> n2 = n1.next;
        n1.next = sentinel;
        while (n2 != sentinel) {
            final Node<E> tmp = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = tmp;
        }
        sentinel.next = n1;
    }

    /**
     * @return an {@link Iterator} over the {@link Node}s.
     */
    public Iterator<Node<E>> nodeIterator() {
        //noinspection ReturnOfInnerClass
        return new NodeItr();
    }

    /**
     * Inserts the element <i>e</i> after the {@link Node} <i>prev</i>.
     *
     * @return the newly created node.
     */
    public Node<E> insertAfter(final E e, final Node<E> prev) {
        final Node<E> node = new Node<>(e);
        node.next = prev.next;
        prev.next = node;
        size++;
        return node;
    }

    /**
     * Removes the {@link Node} following <i>prev</i>.
     */
    @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
    public void removeAfter(final Node<E> prev) {
        final Node<E> removed = prev.next;
        if (removed == sentinel) {
            return;
        }
        prev.next = removed.next;
        //noinspection AssignmentToNull (clear to let GC do its work)
        removed.next = null;
        size--;
    }

    @SuppressWarnings("PublicInnerClass")
    public static final class Node<E> {

        private final E       value;
        private       Node<E> next;

        private Node(final E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public Node<E> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return value != null ? format("%s->%s", value, next) : "[X]";
        }
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private final class NodeItr implements Iterator<Node<E>> {

        private Node<E> prev;
        private Node<E> curr;

        private NodeItr() {
            this.curr = sentinel;
        }

        @Override
        public boolean hasNext() {
            // Sentinel's value is always null
            return curr.next.value != null;
        }

        @Override
        public Node<E> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prev = curr;
            curr = curr.next;
            return curr;
        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }
            removeAfter(prev);
            curr = prev;
        }
    }
}

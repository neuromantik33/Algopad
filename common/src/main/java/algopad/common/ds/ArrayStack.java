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

import algopad.common.ds.itr.ArrayIterator;

import static java.lang.System.arraycopy;
import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.copyOf;

/**
 * Fixed-capacity object array backed implementation of a {@link Stack}.
 *
 * @author Nicolas Estrada.
 */
public class ArrayStack<E> extends AbstractCollection<E> implements Stack<E> {

    private final E[] elements;
    private       int size;

    public ArrayStack(final Class<E[]> clazz, final int capacity) {
        this.elements = clazz.cast(newInstance(clazz.getComponentType(), capacity));
    }

    @Override
    public E peek() {
        verifySize();
        return elements[size - 1];
    }

    @Override
    public E pop() {
        verifySize();
        size--;
        final E e = elements[size];
        //noinspection AssignmentToNull (clear to let GC do its work)
        elements[size] = null;
        return e;
    }

    private void verifySize() {
        if (size == 0) { throw new EmptyStackException(); }
    }

    @Override
    public void push(final E elmt) {
        if (size + 1 > elements.length) {
            throw new FullStackException();
        }
        elements[size] = elmt;
        size++;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elements, size - 1, size, false);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        return copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        if (a.length < size) {
            //noinspection unchecked,SuspiciousArrayCast
            return (T[]) copyOf(elements, size, a.getClass());
        }
        arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            //noinspection AssignmentToNull
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(final E e) {
        try {
            push(e);
            return true;
        } catch (final FullStackException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void clear() {
        size = 0;
    }

}

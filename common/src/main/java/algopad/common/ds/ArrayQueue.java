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

import javax.annotation.Nonnull;

import algopad.common.ds.itr.CyclingArrayIterator;

import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.fill;

/**
 * A fixed-capacity object array backed implementation of a {@link Queue}.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("ReturnOfNull")
public class ArrayQueue<E> extends AbstractQueue<E> {

    private final E[] elements;

    private int size;
    private int tail;
    private int head;

    public ArrayQueue(final Class<E[]> clazz, final int capacity) {
        this.elements = clazz.cast(newInstance(clazz.getComponentType(), capacity));
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return new CyclingArrayIterator<>(elements, head, size, true);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(final E e) {
        if (size == elements.length) {
            return false;
        }
        elements[tail] = e;
        tail = (tail + 1) % elements.length;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        final E e = elements[head];
        //noinspection AssignmentToNull (clear to let GC do its work)
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return elements[head];
    }

    @Override
    public void clear() {
        fill(elements, null);
        head = 0;
        tail = 0;
        size = 0;
    }
}

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

import edu.princeton.cs.algs4.StdRandom;

/**
 * A {@link RandomizedQueue} is similar to a stack or queue, except that the
 * item removed is chosen uniformly at random from items in the data structure.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("TypeParameterNamingConvention")
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elements;
    private int    size;

    public RandomizedQueue() {
        elements = newElementArray(DEFAULT_CAPACITY);
    }

    private void resize(final int capacity) {
        final Item[] tmp = newElementArray(capacity);
        System.arraycopy(elements, 0, tmp, 0, size);
        elements = tmp;
    }

    private Item[] newElementArray(final int capacity) {
        //noinspection unchecked,SuspiciousArrayCast
        return (Item[]) new Object[capacity];
    }

    /**
     * @return {@code true} if the queue is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items on the queue.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an <i>item</i> to the queue.
     */
    public void enqueue(final Item item) {
        if (item == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }
        expandIfNeeded();
        elements[size++] = item;
    }

    private void expandIfNeeded() {
        if (elements.length == size) {
            resize(2 * elements.length);
        }
    }

    /**
     * Remove and returns a random item from the queue.
     */
    public Item dequeue() {
        verifySize();
        final int idx = StdRandom.uniform(size);
        final Item elmt = elements[idx];
        swapLastAt(idx);
        shrinkIfNeeded();
        return elmt;
    }

    private void swapLastAt(final int idx) {
        size--;
        elements[idx] = elements[size];
        //noinspection AssignmentToNull
        elements[size] = null;
    }

    private void shrinkIfNeeded() {
        if (size > 0 && size == elements.length / 4) {
            final int newCapacity = Math.max(elements.length / 2, DEFAULT_CAPACITY);
            resize(newCapacity);
        }
    }

    /**
     * Returns but doesn't remove a random item from the queue.
     */
    public Item sample() {
        verifySize();
        final int idx = StdRandom.uniform(size);
        return elements[idx];
    }

    private void verifySize() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
    }

    /**
     * @return an independent iterator of the items on the queue in random order.
     */
    @Override
    public Iterator<Item> iterator() {
        //noinspection ReturnOfInnerClass
        return new RandomIterator();
    }

    /**
     * {@link Iterator} implementation which traverses over the elements in
     * random order. All iterators are mutually independent; each iterator
     * maintains its own random order.
     */
    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private final class RandomIterator implements Iterator<Item> {

        private final int[] indexes;

        private int cursor;

        private RandomIterator() {
            indexes = new int[size];
            for (int i = 0; i < size; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return cursor < indexes.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final int idx = indexes[cursor++];
            return elements[idx];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}

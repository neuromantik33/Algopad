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

package algopad.common.ds.itr;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

/**
 * {@link Iterator} over a typed-array which iterates from a <i>start</i> index
 * for a given <i>length</i>.<br>
 * It can iterate either forward or backward and it cycles over the array whenever a
 * boundary is encountered and there are still any remaining elements left to read.
 *
 * @author Nicolas Estrada.
 */
public class CyclingArrayIterator<E> implements Iterator<E> {

    private final E[] elements;
    private final int step;

    private int index;
    private int remaining;

    /**
     * Creates a new array iterator instance.
     *
     * @param ascending if {@code true} then the index will increment until the
     * <i>length</i> it iterated, otherwise it will decrement.
     */
    public CyclingArrayIterator(final E[] elements,
                                final int start,
                                final int length,
                                final boolean ascending) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.elements = elements;
        this.remaining = length;
        this.index = start;
        this.step = ascending ? 1 : -1;
    }

    @Override
    public boolean hasNext() {
        return remaining > 0;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        final E e = elements[index];
        index += step;
        resetIndex();
        remaining--;
        return e;
    }

    private void resetIndex() {
        final int capacity = elements.length;
        if (index == capacity) {
            index = 0;
        } else if (index == -1) {
            index = capacity - 1;
        }
    }
}

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

package algopad.common.ds.demos;

import java.util.AbstractCollection;
import java.util.EmptyStackException;
import java.util.Iterator;

import algopad.common.ds.Stack;
import algopad.common.ds.itr.CyclingArrayIterator;

import static java.lang.reflect.Array.newInstance;

/**
 * Implementation of 2 {@link Stack}s using one array A[1..n] in such a way that neither stack overflows unless the
 * total number of elements in both stacks together is n.<br>
 * The {@link Stack#push(Object)} and {@link Stack#pop()} operations run in O(1) time.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "ReturnOfInnerClass", "ReturnOfCollectionOrArrayField" })
public class PairStack<E> {

    private final E[] elements;

    private final SemiStack left;
    private final SemiStack right;

    public PairStack(final Class<E[]> clazz, final int totalCapacity) {
        this.elements = clazz.cast(newInstance(clazz.getComponentType(), totalCapacity));
        this.left = new SemiStack(true);
        this.right = new SemiStack(false);
    }

    public Stack<E> getLeft() {
        return left;
    }

    public Stack<E> getRight() {
        return right;
    }

    public int totalSize() {
        return left.size() + right.size();
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private final class SemiStack extends AbstractCollection<E>
      implements Stack<E> {

        private final boolean ascending;
        private final int     capacity;

        private int size;

        private SemiStack(final boolean ascending) {
            this.ascending = ascending;
            this.capacity = elements.length;
        }

        @Override
        public E peek() {
            verifySize();
            return elements[currentIdx()];
        }

        @Override
        public E pop() {
            verifySize();
            final int idx = currentIdx();
            final E elmt = elements[idx];
            size--;
            //noinspection AssignmentToNull (clear to let GC do its work)
            elements[idx] = null;
            return elmt;
        }

        private void verifySize() {
            if (size == 0) { throw new EmptyStackException(); }
        }

        @Override
        public void push(final E elmt) {
            if (totalSize() + 1 > elements.length) {
                throw new FullStackException();
            }
            size++;
            elements[currentIdx()] = elmt;
        }

        @Override
        public Iterator<E> iterator() {
            return new CyclingArrayIterator<>(elements, currentIdx(), size, ascending);
        }

        private int currentIdx() {
            return ascending ? capacity - size : size - 1;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            while (!isEmpty()) { pop(); }
        }
    }
}

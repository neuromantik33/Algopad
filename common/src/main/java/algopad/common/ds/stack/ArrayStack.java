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

package algopad.common.ds.stack;

import java.util.EmptyStackException;

import static java.lang.reflect.Array.newInstance;

/**
 * Fixed-capacity object array backed implementation of a {@link Stack}.
 *
 * @author Nicolas Estrada.
 */
public class ArrayStack<E> implements Stack<E> {

    private final E[] elements;
    private       int size;

    public ArrayStack(final Class<E[]> clazz, final int capacity) {
        this.elements = clazz.cast(newInstance(clazz.getComponentType(), capacity));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        return elements[size];
    }

    private void verifySize() {
        if (size == 0) { throw new EmptyStackException(); }
    }

    @Override
    public void push(final E elmt) {
        if (size + 1 > elements.length) {
            throw new IllegalArgumentException("Stack is full");
        }
        elements[size] = elmt;
        size++;
    }
}

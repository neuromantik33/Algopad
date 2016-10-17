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

import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;

import static java.lang.reflect.Array.newInstance;

/**
 * A fixed-capacity object array backed implementation of a {@link Queue}.
 *
 * @author Nicolas Estrada.
 */
public class ArrayQueue<E> extends AbstractQueue<E> {

    private final E[] elements;
    private       int size;

    public ArrayQueue(final Class<E[]> clazz, final int capacity) {
        this.elements = clazz.cast(newInstance(clazz.getComponentType(), capacity));
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(final E e) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}

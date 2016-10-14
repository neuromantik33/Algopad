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

import java.util.Collection;
import java.util.EmptyStackException;

/**
 * All data structures wishing to implement a barebones stack must implement this interface.
 *
 * @author Nicolas Estrada.
 */
public interface Stack<E> extends Collection<E> {

    /**
     * Looks at the element at the top of this stack without removing it
     * from the stack.
     *
     * @throws EmptyStackException if this stack is empty.
     */
    E peek();

    /**
     * Removes the element at the top of this stack and returns it.
     *
     * @throws EmptyStackException if this stack is empty.
     */
    E pop();

    /**
     * Pushes a element onto the top of this stack.
     *
     * @throws FullStackException if the stack is full and of fixed-capacity.
     */
    void push(E elmt);

    /**
     * Thrown when attempting to {@link #push(Object)} an element into a full fixed-capacity stack.
     */
    @SuppressWarnings("PublicInnerClass")
    class FullStackException extends RuntimeException {}

}

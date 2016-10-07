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

import java.util.EmptyStackException;

/**
 * Simple barebones implementation of a fixed-capacity stack of characters.
 *
 * @author Nicolas Estrada.
 */
public class CharStack {

    private final char[] chars;
    private       int    size;

    public CharStack(final int capacity) {
        this.chars = new char[capacity];
    }

    /**
     * @return the number of characters in this stack.
     */
    public int size() {
        return size;
    }

    /**
     * @return {@code true} if and only if this stack contains no characters, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Looks at the character at the top of this stack without removing it
     * from the stack.
     *
     * @throws EmptyStackException if this stack is empty.
     */
    public char peek() {
        verifySize();
        return chars[size - 1];
    }

    /**
     * Removes the character at the top of this stack and returns it.
     *
     * @throws EmptyStackException if this stack is empty.
     */
    public char pop() {
        verifySize();
        final char c = chars[size - 1];
        size--;
        return c;
    }

    private void verifySize() {
        if (size == 0) { throw new EmptyStackException(); }
    }

    /**
     * Pushes a character onto the top of this stack.
     */
    public void push(final char c) {
        if (size + 1 > chars.length) {
            throw new IllegalArgumentException("Stack is full");
        }
        chars[size] = c;
        size++;
    }
}

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

package algopad.algorithms.pt2.w6;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Helper class for dealing reading and writing binary data.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "AnonymousInnerClassWithTooManyMethods",
                    "OverlyComplexAnonymousInnerClass" })
public final class BinaryHelper {

    private BinaryHelper() {
    }

    public static ByteSource stdIn() {
        return new ByteSource() {
            @Override
            public boolean isEmpty() { return BinaryStdIn.isEmpty(); }

            @Override
            public char readChar() { return BinaryStdIn.readChar(); }

            @Override
            public int readInt() { return BinaryStdIn.readInt(); }

            @Override
            public String readString() { return BinaryStdIn.readString(); }

            @Override
            public void close() { BinaryStdIn.close(); }
        };
    }

    public static ByteSink stdOut() {
        return new ByteSink() {
            @Override
            public void write(final char x) { BinaryStdOut.write(x); }

            @Override
            public void write(final int x) { BinaryStdOut.write(x); }

            @Override
            public void flush() { BinaryStdOut.flush(); }

            @Override
            public void close() { BinaryStdOut.close(); }
        };
    }
}

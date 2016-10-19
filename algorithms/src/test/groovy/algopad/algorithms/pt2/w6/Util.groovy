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

package algopad.algorithms.pt2.w6

import edu.princeton.cs.algs4.BinaryIn
import edu.princeton.cs.algs4.BinaryOut
import edu.princeton.cs.algs4.StdOut

class Util {

    static def runtime = Runtime.runtime

    static long getUsedMemory() {
        def kb = 1024
        (runtime.totalMemory() - runtime.freeMemory()) / kb
    }

    static dump(BinaryIn is) {
        def bytesPerLine = 16
        def i;
        for (i = 0; !is.empty; i++) {
            if (bytesPerLine == 0) {
                is.readChar()
                continue
            }
            if (i == 0) {
                StdOut.printf('')
            } else if (i % bytesPerLine == 0) {
                StdOut.printf('\n', i)
            } else {
                StdOut.print(' ')
            };
            int c = is.readChar();
            StdOut.printf('%02x', c & 0xff)
        }
        if (bytesPerLine != 0) {
            println()
        }
        println "${i * 8} bits"
    }

    static ByteSource newSource(URL url) {
        def input = new BinaryIn(url)
        new ByteSource() {
            @Override
            boolean isEmpty() {
                input.empty
            }

            @Override
            char readChar() {
                input.readChar()
            }

            @Override
            int readInt() {
                input.readInt()
            }

            @Override
            String readString() {
                input.readString()
            }

            @Override
            void close() {}
        }
    }

    static ByteSink newSink(OutputStream os) {
        def output = new BinaryOut(os)
        new ByteSink() {
            @Override
            void write(final char x) {
                output.write(x)
            }

            @Override
            void write(final int x) {
                output.write(x)
            }

            @Override
            void flush() {
                output.flush()
            }

            @Override
            void close() {
                output.close()
            }
        }
    }
}

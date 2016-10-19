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

/**
 * This class implements the move-to-front codec.<br>
 * The main idea of move-to-front encoding is to maintain an ordered sequence
 * of the characters in the alphabet, and repeatedly read in a character from
 * the input message, print out the position in which that character appears,
 * and move that character to the front of the sequence.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "UtilityClassWithoutPrivateConstructor",
                    "NonFinalUtilityClass" })
public class MoveToFront {

    private static final int RADIX = 256;

    /**
     * Apply move-to-front encoding, reading from standard input
     * and writing to standard output
     */
    private static void encode() {
        final ByteSource in = BinaryHelper.stdIn();
        final ByteSink out = BinaryHelper.stdOut();
        try {
            new Encoder(in, out).encode();
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Apply move-to-front decoding, reading from standard input
     * and writing to standard output
     */
    private static void decode() {
        final ByteSource in = BinaryHelper.stdIn();
        final ByteSink out = BinaryHelper.stdOut();
        try {
            new Decoder(in, out).decode();
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * if args[0] is '-', apply move-to-front encoding.
     * if args[0] is '+', apply move-to-front decoding.
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(final String[] args) {
        final String cmd = args[0];
        if ("-".equals(cmd)) {
            encode();
        } else if ("+".equals(cmd)) {
            decode();
        }
    }

    private static final class Encoder {

        private final ByteSource in;
        private final ByteSink   out;
        private final char[]     alphabet;

        private Encoder(final ByteSource in, final ByteSink out) {
            this.in = in;
            this.out = out;
            this.alphabet = new char[RADIX];
            for (char c = 0; c < RADIX; c++) {
                alphabet[c] = c;
            }
        }

        @SuppressWarnings("MethodWithMultipleLoops")
        public void encode() {

            //noinspection MethodCallInLoopCondition
            while (!in.isEmpty()) {

                final char c = in.readChar();

                char idx = 0;
                char first = alphabet[0];
                while (c != alphabet[idx]) {
                    final char tmp = alphabet[idx];
                    alphabet[idx] = first;
                    first = tmp;
                    idx++;
                }

                alphabet[idx] = first;
                out.write(idx);
                alphabet[0] = c;

            }

            out.flush();

        }
    }

    private static final class Decoder {

        private final ByteSource in;
        private final ByteSink   out;
        private final char[]     alphabet;

        private Decoder(final ByteSource in, final ByteSink out) {
            this.in = in;
            this.out = out;
            this.alphabet = new char[RADIX];
            for (char c = 0; c < RADIX; c++) {
                alphabet[c] = c;
            }
        }

        @SuppressWarnings("MethodWithMultipleLoops")
        public void decode() {

            //noinspection MethodCallInLoopCondition
            while (!in.isEmpty()) {

                int idx = in.readChar();
                final char c = alphabet[idx];
                out.write(c);

                while (idx > 0) {
                    alphabet[idx] = alphabet[idx - 1];
                    idx--;
                }

                alphabet[0] = c;

            }

            out.flush();

        }
    }
}

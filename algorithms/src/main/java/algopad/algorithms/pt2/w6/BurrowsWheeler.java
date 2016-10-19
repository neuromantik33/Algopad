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
 * This class implements the Burrows-Wheeler codec.<br>
 * The goal of the Burrows-Wheeler transform is not to compress a message,
 * but rather to transform it into a form that is more amenable to compression.
 * The transform rearranges the characters in the input so that there are lots
 * of clusters with repeated characters, but in such a way that it is still
 * possible to recover the original input.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "UtilityClassWithoutPrivateConstructor",
                    "NonFinalUtilityClass" })
public class BurrowsWheeler {

    private static final int RADIX = 256;

    /**
     * Apply Burrows-Wheeler encoding, reading from standard input
     * and writing to standard output.
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
     * Apply Burrows-Wheeler decoding, reading from standard input
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
     * if args[0] is '-', apply Burrows-Wheeler encoding.
     * if args[0] is '+', apply Burrows-Wheeler decoding.
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

        private Encoder(final ByteSource in, final ByteSink out) {
            this.in = in;
            this.out = out;
        }

        public void encode() {

            final String s = in.readString();
            final CircularSuffixArray suffixes = new CircularSuffixArray(s);

            writeOriginalIndex(suffixes);
            writeLastSuffixArrayColumn(s, suffixes);

            out.flush();

        }

        private void writeOriginalIndex(final CircularSuffixArray csa) {
            for (int i = 0, len = csa.length(); i < len; i++) {
                final int idx = csa.index(i);
                if (idx == 0) {
                    out.write(i);
                    break;
                }
            }
        }

        private void writeLastSuffixArrayColumn(final CharSequence cs,
                                                final CircularSuffixArray csa) {
            for (int i = 0, len = csa.length(); i < len; i++) {
                final int idx = csa.index(i);
                final char c;
                //noinspection IfMayBeConditional
                if (idx > 0) {
                    c = cs.charAt(idx - 1);
                } else {
                    c = cs.charAt(len - 1);
                }
                out.write(c);
            }
        }
    }

    private static final class Decoder {

        private final ByteSource in;
        private final ByteSink   out;

        private Decoder(final ByteSource in, final ByteSink out) {
            this.in = in;
            this.out = out;
        }

        @SuppressWarnings("MethodWithMultipleLoops")
        public void decode() {

            int first = in.readInt();
            final String s = in.readString();
            final int len = s.length();

            // Compute frequency counts
            final int[] count = new int[RADIX + 1];
            for (int i = 0; i < len; i++) {
                //noinspection CharUsedInArithmeticContext
                count[s.charAt(i) + 1]++;
            }

            // Compute cumulates
            for (int r = 0; r < RADIX; r++) {
                count[r + 1] += count[r];
            }

            // Populate next
            final int[] next = new int[len];
            for (int i = 0; i < len; i++) {
                //noinspection ValueOfIncrementOrDecrementUsed
                next[count[s.charAt(i)]++] = i;
            }

            // Write to stream
            for (int i = 0; i < len; i++) {
                out.write(s.charAt(next[first]));
                first = next[first];
            }

            out.flush();

        }
    }
}

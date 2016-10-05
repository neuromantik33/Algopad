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

package algtb.w5;

import java.util.Scanner;

import static java.util.Arrays.asList;
import static java.util.Collections.max;

/**
 * @author Nicolas Estrada.
 */
public final class LCS3 {

    private LCS3() {}

    @SuppressWarnings("MethodWithMultipleLoops")
    static int getLCSLength(final int[] seq1, final int[] seq2, final int[] seq3) {

        final int len1 = seq1.length;
        final int len2 = seq2.length;
        final int len3 = seq3.length;
        final int[][][] lengths = new int[len1 + 1][len2 + 1][len3 + 1];
        final Integer[] buf = new Integer[7];
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                for (int k = 1; k <= len3; k++) {
                    if (seq1[i - 1] == seq2[j - 1] && seq1[i - 1] == seq3[k - 1]) {
                        lengths[i][j][k] = lengths[i - 1][j - 1][k - 1] + 1;
                    } else {
                        buf[0] = lengths[i][j][k - 1];
                        buf[1] = lengths[i][j - 1][k];
                        buf[2] = lengths[i][j - 1][k - 1];
                        buf[3] = lengths[i - 1][j][k];
                        buf[4] = lengths[i - 1][j][k - 1];
                        buf[5] = lengths[i - 1][j - 1][k];
                        buf[6] = lengths[i - 1][j - 1][k - 1];
                        lengths[i][j][k] = max(asList(buf));
                    }
                }
            }
        }
        return lengths[len1][len2][len3];
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int[] seq1 = nextIntArray(in);
            final int[] seq2 = nextIntArray(in);
            final int[] seq3 = nextIntArray(in);
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getLCSLength(seq1, seq2, seq3));
        }
    }

    private static int[] nextIntArray(final Scanner in) {
        final int n = in.nextInt();
        final int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        return array;
    }
}


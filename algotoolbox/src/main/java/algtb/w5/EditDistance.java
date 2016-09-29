/*
 *  algopad.
 */

package algtb.w5;

import static java.lang.Math.min;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class EditDistance {

    private EditDistance() {}

    @SuppressWarnings("MethodWithMultipleLoops")
    static int calculateEditDistance(final String s, final String t) {

        final int sLen = s.length();
        final int tLen = t.length();
        final int[][] distances = initDistances(sLen, tLen);

        for (int i = 1; i <= sLen; i++) {
            for (int j = 1; j <= tLen; j++) {
                final int match = distances[i - 1][j - 1] + match(s.charAt(i - 1), t.charAt(j - 1));
                final int insert = distances[i][j - 1] + 1;
                final int delete = distances[i - 1][j] + 1;
                distances[i][j] = min(min(match, insert), delete);
            }
        }

        return distances[sLen][tLen];

    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private static int[][] initDistances(final int x, final int y) {
        final int[][] distances = new int[x + 1][y + 1];
        for (int i = 0; i <= x; i++) { distances[i][0] = i; }
        for (int i = 0; i <= y; i++) { distances[0][i] = i; }
        return distances;
    }

    private static int match(final char x, final char y) {
        return x == y ? 0 : 1;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final String s = in.next();
            final String t = in.next();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateEditDistance(s, t));
        }
    }
}

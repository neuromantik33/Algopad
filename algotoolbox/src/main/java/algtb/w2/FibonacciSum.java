/*
 *  algopad.
 */

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class FibonacciSum {

    private FibonacciSum() {}

    private static final int[] PISANO_PERIOD_MOD10 =
      {
        0, 1, 1, 2, 3, 5, 8, 3, 1, 4,
        5, 9, 4, 3, 7, 0, 7, 7, 4, 1,
        5, 6, 1, 7, 8, 5, 3, 8, 1, 9,
        0, 9, 9, 8, 7, 5, 2, 7, 9, 6,
        5, 1, 6, 7, 3, 0, 3, 3, 6, 9,
        5, 4, 9, 3, 2, 5, 7, 2, 9, 1
      };

    static long getFibonacciSumLastDigit(final long n) {
        final int offset = (int) (n + 2) % PISANO_PERIOD_MOD10.length;
        return PISANO_PERIOD_MOD10[offset] - 1;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final long l = in.nextLong();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciSumLastDigit(l));
        }
    }
}


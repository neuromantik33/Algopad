/*
 *  algopad.
 */

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class FibonacciPartialSum {

    private FibonacciPartialSum() {}

    static long getFibonacciPartialSumLastDigit(final long from, final long to) {
        final int fromVal = getLastDigitForFib(from + 1);
        int toVal = getLastDigitForFib(to + 2);
        if (toVal < fromVal) {
            toVal += 10;
        }
        return toVal - fromVal;
    }

    private static int getLastDigitForFib(final long l) {
        final long offset = l % PISANO_PERIOD_MOD10.length;
        return PISANO_PERIOD_MOD10[(int) offset];
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final long from = in.nextLong();
            final long to = in.nextLong();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciPartialSumLastDigit(from, to));
        }
    }

    private static final int[] PISANO_PERIOD_MOD10 =
      {
        0, 1, 1, 2, 3, 5, 8, 3, 1, 4,
        5, 9, 4, 3, 7, 0, 7, 7, 4, 1,
        5, 6, 1, 7, 8, 5, 3, 8, 1, 9,
        0, 9, 9, 8, 7, 5, 2, 7, 9, 6,
        5, 1, 6, 7, 3, 0, 3, 3, 6, 9,
        5, 4, 9, 3, 2, 5, 7, 2, 9, 1
      };
}


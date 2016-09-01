/*
 *  algopad.
 */

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class Fibonacci {

    private Fibonacci() {}

    static long calculateFibonacci(final int n) {

        if (n < 2) {
            return n;
        }

        long nMinus2 = 0;
        long nMinus1 = 1;

        for (int i = 1; i < n; i++) {
            final long tmp = nMinus1 + nMinus2;
            nMinus2 = nMinus1;
            nMinus1 = tmp;
        }

        return nMinus1;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateFibonacci(n));
        }
    }
}

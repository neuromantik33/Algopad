/*
 *  algopad.
 */

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class FibonacciLastDigit {

    private FibonacciLastDigit() {}

    static int getFibonacciLastDigit(final int n) {

        if (n < 2) {
            return n;
        }

        int nMinus2 = 0;
        int nMinus1 = 1;

        for (int i = 1; i < n; i++) {
            final int tmp = nMinus1 + nMinus2;
            nMinus2 = nMinus1;
            nMinus1 = tmp % 10;
        }

        return nMinus1;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciLastDigit(n));
        }
    }
}


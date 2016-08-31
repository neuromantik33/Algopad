/*
 *  algopad.
 */

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings("ClassNamingConvention")
public final class GCD {

    private GCD() {}

    static long calculateGCD(final int x, final int y) {
        int i = x;
        int j = y;
        while (j != 0L) {
            final int tmp = i % j;
            i = j;
            j = tmp;
        }
        return i;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int x = in.nextInt();
            final int y = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateGCD(x, y));
        }
    }
}

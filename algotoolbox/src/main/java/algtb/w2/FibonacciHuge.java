/*
 *  algopad.
 */

package algtb.w2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings("StandardVariableNames")
public final class FibonacciHuge {

    private FibonacciHuge() {}

    static long getFibonacciHuge(final long n, final int m) {

        if (n < 2) {
            return n;
        }

        final List<Integer> cache = new ArrayList<>(100);
        cache.add(0);
        cache.add(1);

        int i;
        for (i = 1; i < n; i++) {
            final int x = cache.get(i - 1);
            final int y = cache.get(i);
            final int z = (x + y) % m;
            cache.add(z);
            if (isEndOfPeriod(y, z)) {
                break;
            }
        }

        final int lastIx = cache.size() - 1;
        //noinspection NumericCastThatLosesPrecision
        return i < n ? cache.get((int) (n % lastIx)) : cache.get(lastIx);

    }

    private static boolean isEndOfPeriod(final int i, final int j) {
        return j == 0 && i == 1;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final long n = in.nextLong();
            final int m = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciHuge(n, m));
        }
    }
}

/*
 *  algopad.
 */

package algtb.w4;

import static java.lang.System.arraycopy;

import java.util.Scanner;

public final class Inversions {

    private Inversions() {}

    static long countInversions(final int[] a,
                                final int[] aux,
                                final int lo,
                                final int hi) {

        if (hi <= lo) {
            return 0L;
        }

        final int mid = (lo + hi) / 2;
        return countInversions(a, aux, lo, mid) +
               countInversions(a, aux, mid + 1, hi) +
               mergeAndCount(a, aux, lo, mid, hi);

    }

    private static long mergeAndCount(final int[] a,
                                      final int[] aux,
                                      final int lo,
                                      final int mid,
                                      final int hi) {

        // copy to aux[]
        arraycopy(a, lo, aux, lo, hi + 1 - lo);

        // merge back to a[]
        int i = lo;
        int j = mid + 1;
        long numInv = 0L;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                numInv += mid + 1 - i;
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }

        return numInv;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int[] a = nextIntArray(in);
            final int[] aux = new int[a.length];
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(countInversions(a, aux, 0, a.length - 1));
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

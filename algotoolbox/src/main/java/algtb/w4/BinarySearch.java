/*
 *  algopad.
 */

package algtb.w4;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class BinarySearch {

    private BinarySearch() {}

    @SuppressWarnings("MethodNamesDifferingOnlyByCase")
    static int binarySearch(final int[] a, final int x) {
        int lo = 0;
        int hi = a.length - 1;
        int index = -1;
        while (hi >= lo) {
            final int median = (hi + lo) / 2;
            final int guess = a[median];
            if (guess == x) {
                index = median;
                break;
            }
            if (guess > x) {
                hi = median - 1;
            } else {
                lo = median + 1;
            }
        }
        return index;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int[] a = nextIntArray(in);
            final int[] input = nextIntArray(in);
            for (final int x : input) {
                //noinspection UseOfSystemOutOrSystemErr
                System.out.print(binarySearch(a, x) + " ");
            }
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

/*
 *  algopad.
 */

package algtb.w3;

import static java.util.Arrays.sort;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class DotProduct {

    private DotProduct() {}

    static long maxDotProduct(final int[] ads, final int[] slots) {
        sort(ads);
        sort(slots);
        return calcDotProduct(ads, slots);
    }

    private static long calcDotProduct(final int[] v1, final int[] v2) {
        long result = 0;
        for (int i = v1.length - 1; i >= 0; i--) {
            result += (long) v1[i] * v2[i];
        }
        return result;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final int[] ads = new int[n];
            for (int i = 0; i < n; i++) {
                ads[i] = in.nextInt();
            }
            final int[] slots = new int[n];
            for (int i = 0; i < n; i++) {
                slots[i] = in.nextInt();
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(maxDotProduct(ads, slots));
        }
    }
}

/*
 *  algopad.
 */

package algtb.w5;

import static java.lang.Math.max;
import static java.lang.System.arraycopy;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class Knapsack {

    private Knapsack() {}

    @SuppressWarnings("MethodWithMultipleLoops")
    static int calculateOptimalWeight(final int maxCapacity, final int[] weights) {

        // This implementation uses O(n) space instead of O(n**2) spaces
        final int[] cache = new int[maxCapacity];
        final int[] buf = new int[maxCapacity];

        for (final int weight : weights) {
            for (int capacity = 0; capacity < maxCapacity; capacity++) {
                if (weight <= capacity) {
                    final int solutionWithoutItem = cache[capacity];
                    final int solutionWithItem = cache[capacity - weight] + weight;
                    buf[capacity] = max(solutionWithoutItem, solutionWithItem);
                }
            }
            // Update the cache with the buffer contents
            arraycopy(buf, 0, cache, 0, maxCapacity);
        }

        return cache[cache.length - 1];

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int maxCapacity = in.nextInt();
            final int[] w = nextIntArray(in);
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateOptimalWeight(maxCapacity, w));
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

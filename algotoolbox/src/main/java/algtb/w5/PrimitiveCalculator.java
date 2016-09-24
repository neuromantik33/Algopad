/*
 *  algopad.
 */

package algtb.w5;

import static java.util.Collections.reverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class PrimitiveCalculator {

    private PrimitiveCalculator() {}

    static List<Integer> calculateOptimalSequence(final int n) {

        final int[] cache = new int[n + 1];

        // Update the cache with optimal solutions to sub problems
        for (int i = 2; i <= n; i++) {
            final int index = calculateIndex(cache, i);
            cache[i] = cache[index] + 1;
        }

        // Reconstruct the solution
        final int numOperations = cache[cache.length - 1];
        final List<Integer> sequence = new ArrayList<>(numOperations);
        int i = n;
        while (i > 0) {
            sequence.add(i);
            i = calculateIndex(cache, i);
        }
        reverse(sequence);

        return sequence;

    }

    private static int calculateIndex(final int[] cache, final int i) {

        // n + 1
        int index = i - 1;
        int min = cache[index];

        // n * 2
        if (i % 2 == 0) {
            final int k = i / 2;
            if (cache[k] < min) {
                index = k;
                min = cache[k];
            }
        }

        // n * 3
        if (i % 3 == 0) {
            final int k = i / 3;
            if (cache[k] < min) {
                index = k;
            }
        }

        return index;

    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final List<Integer> sequence = calculateOptimalSequence(n);
            System.out.println(sequence.size() - 1);
            for (final Integer x : sequence) {
                System.out.print(x + " ");
            }
        }
    }
}

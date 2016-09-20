/*
 *  algopad.
 */

package algtb.w4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class MajorityElement {

    private MajorityElement() {}

    /**
     * @return the {@code true} of the element in the array <i>a</i> that appears
     * strictly more than n/2 times, {@code false} otherwise.
     */
    static boolean hasMajorityElement(final int[] a) {
        final Map<Integer, Integer> countMap = countOccurrences(a);
        final double majority = (double) a.length / 2;
        for (final int count : countMap.values()) {
            if (count > majority) {
                return true;
            }
        }
        return false;
    }

    private static Map<Integer, Integer> countOccurrences(final int[] a) {
        final Map<Integer, Integer> countMap = new HashMap<>(a.length / 2);
        for (final int val : a) {
            final Integer cnt = countMap.get(val);
            final int newCnt = cnt != null ? cnt + 1 : 1;
            countMap.put(val, newCnt);
        }
        return countMap;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int[] a = nextIntArray(in);
            final int answer = hasMajorityElement(a) ? 1 : 0;
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(answer);
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

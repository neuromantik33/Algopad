/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.algtb.w4;

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

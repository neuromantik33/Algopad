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

package algtb.w3;

import static java.lang.Math.abs;
import static java.util.Arrays.sort;

import java.util.Comparator;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class LargestNumber {

    private LargestNumber() {}

    static String getLargestNumber(final String[] a) {
        sort(a, BY_DECREASING_ORDER_WITH_PADDING);
        final StringBuilder sb = new StringBuilder(a.length);
        for (final String s : a) {
            sb.append(s);
        }
        return sb.toString();
    }

    @SuppressWarnings("AssignmentToMethodParameter")
    private static int padSmallestAndCompare(String o1, String o2) {
        final int len1 = o1.length();
        final int len2 = o2.length();
        final int delta = abs(len1 - len2);
        if (len1 > len2) {
            o2 += o1.substring(0, delta);
        } else {
            o1 += o2.substring(0, delta);
        }
        return o2.compareTo(o1);
    }

    private static int compareOutcomes(final String o1, final String o2) {
        //noinspection StringConcatenationMissingWhitespace,StringConcatenation
        return (o2 + o1).compareTo(o1 + o2);
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final String[] a = new String[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.next();
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getLargestNumber(a));
        }
    }

    private static final Comparator<String> BY_DECREASING_ORDER_WITH_PADDING = new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
            if (o1.length() == o2.length()) {
                return o2.compareTo(o1);
            }
            final int cmp = padSmallestAndCompare(o1, o2);
            if (cmp != 0) {
                return cmp;
            }
            // Fallback to building the outcomes and comparing them
            return compareOutcomes(o1, o2);
        }
    };

}

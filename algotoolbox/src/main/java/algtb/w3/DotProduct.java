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

    @SuppressWarnings("MethodWithMultipleLoops")
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

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

package algopad.algtb.w5;

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
        final int[] cache = new int[maxCapacity + 1];
        final int[] buf = new int[maxCapacity + 1];

        for (final int weight : weights) {
            for (int capacity = 1; capacity <= maxCapacity; capacity++) {
                if (weight <= capacity) {
                    final int solutionWithoutItem = cache[capacity];
                    final int solutionWithItem = cache[capacity - weight] + weight;
                    buf[capacity] = max(solutionWithoutItem, solutionWithItem);
                }
            }
            // Update the cache with the buffer contents
            arraycopy(buf, 0, cache, 0, maxCapacity + 1);
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

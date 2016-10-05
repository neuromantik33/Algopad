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

package algopad.algtb.w1;

import java.util.Scanner;

import static java.lang.Integer.MIN_VALUE;

/**
 * Given a sequence of non-negative integers a0,…,an−1, find the maximum pairwise product,
 * that is, the largest integer that can be obtained by multiplying two different elements
 * from the sequence.<br>
 * Different elements here mean ai and aj with i≠j (it can be the case that ai=aj).
 *
 * @author Nicolas Estrada.
 */
public final class MaxPairwiseProduct {

    private MaxPairwiseProduct() {}

    @SuppressWarnings("MethodWithMultipleLoops")
    static long getMaxPairwiseProduct(final int[] numbers) {

        int max1 = MIN_VALUE;
        int idx = -1;
        for (int i = 0, len = numbers.length; i < len; i++) {
            if (numbers[i] > max1) {
                max1 = numbers[i];
                idx = i;
            }
        }

        int max2 = MIN_VALUE;
        for (int i = 0, len = numbers.length; i < len; i++) {
            if (i != idx && numbers[i] > max2) {
                max2 = numbers[i];
            }
        }

        return (long) max1 * max2;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getMaxPairwiseProduct(numbers));
        }
    }
}

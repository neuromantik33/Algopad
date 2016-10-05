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

package algtb.w2;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class FibonacciSum {

    private FibonacciSum() {}

    @SuppressWarnings("StandardVariableNames")
    static long getFibonacciSumLastDigit(final long n) {
        int val = getLastDigitForFib(n + 2) - 1;
        if (val < 0) {
            val += 10;
        }
        return val;
    }

    private static int getLastDigitForFib(final long l) {
        final long offset = l % PISANO_PERIOD_MOD10.length;
        //noinspection NumericCastThatLosesPrecision
        return PISANO_PERIOD_MOD10[(int) offset];
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final long l = in.nextLong();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciSumLastDigit(l));
        }
    }

    private static final int[] PISANO_PERIOD_MOD10 =
      {
        0, 1, 1, 2, 3, 5, 8, 3, 1, 4,
        5, 9, 4, 3, 7, 0, 7, 7, 4, 1,
        5, 6, 1, 7, 8, 5, 3, 8, 1, 9,
        0, 9, 9, 8, 7, 5, 2, 7, 9, 6,
        5, 1, 6, 7, 3, 0, 3, 3, 6, 9,
        5, 4, 9, 3, 2, 5, 7, 2, 9, 1
      };
}


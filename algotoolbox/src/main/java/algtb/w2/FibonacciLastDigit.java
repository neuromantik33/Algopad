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
public final class FibonacciLastDigit {

    private FibonacciLastDigit() {}

    static int getFibonacciLastDigit(final int n) {

        if (n < 2) {
            return n;
        }

        int nMinus2 = 0;
        int nMinus1 = 1;

        for (int i = 1; i < n; i++) {
            final int tmp = nMinus1 + nMinus2;
            nMinus2 = nMinus1;
            nMinus1 = tmp % 10;
        }

        return nMinus1;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getFibonacciLastDigit(n));
        }
    }
}


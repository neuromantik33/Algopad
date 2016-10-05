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
public final class LCM {

    private LCM() {}

    static long calculateLCM(final int x, final int y) {
        final int xDivGcd = x / calculateGCD(x, y);
        return (long) xDivGcd * y;
    }

    private static int calculateGCD(final int x, final int y) {
        int i = x;
        int j = y;
        while (j != 0) {
            final int tmp = i % j;
            i = j;
            j = tmp;
        }
        return i;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int x = in.nextInt();
            final int y = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateLCM(x, y));
        }
    }
}

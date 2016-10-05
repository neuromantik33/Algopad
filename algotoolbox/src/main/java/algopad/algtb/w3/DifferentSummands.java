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

package algopad.algtb.w3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings("StandardVariableNames")
public final class DifferentSummands {

    private DifferentSummands() {}

    static List<Long> calculateOptimalSummands(final long n) {
        final List<Long> summands = new ArrayList<>(DEFAULT_CAPACITY);
        long k = n;
        long j = 1;
        while (true) {
            if (k <= 2 * j) {
                summands.add(k);
                break;
            }
            summands.add(j);
            k -= j;
            j += 1;
        }
        return summands;
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final List<Long> summands = calculateOptimalSummands(n);
            System.out.println(summands.size());
            for (final Long summand : summands) {
                System.out.print(summand + " ");
            }
        }
    }

    private static final int DEFAULT_CAPACITY = 10;

}

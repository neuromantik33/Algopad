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

import java.util.Random;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class Sorting {

    private Sorting() {}

    static void randomizedQuickSort(final int[] a, final int lo, final int hi) {

        if (lo >= hi) { return; }

        //noinspection UnsecureRandomNumberGeneration
        final Random rnd = new Random();
        final int k = rnd.nextInt(hi - lo + 1) + lo;
        swap(lo, k, a);

        final int[] pivot = partition(a, lo, hi);
        randomizedQuickSort(a, lo, pivot[0] - 1);
        randomizedQuickSort(a, pivot[1] + 1, hi);

    }

    private static int[] partition(final int[] a, final int lo, final int hi) {
        final int x = a[lo];
        int lt = lo;
        int gt = hi;
        int i = lo;
        while (i <= gt) {
            if (a[i] < x) {
                swap(lt, i, a);
                lt += 1;
                i += 1;
            } else if (a[i] > x) {
                swap(i, gt, a);
                gt -= 1;
            } else { // a[i] == x
                i += 1;
            }
        }
        return new int[] { lt, gt };
    }

    private static void swap(final int i, final int j, final int[] a) {
        final int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int[] a = nextIntArray(in);
            randomizedQuickSort(a, 0, a.length - 1);
            for (final int x : a) {
                //noinspection UseOfSystemOutOrSystemErr
                System.out.print(x + " ");
            }
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

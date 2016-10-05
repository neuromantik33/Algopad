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

import static java.lang.Math.min;
import static java.text.MessageFormat.format;
import static java.util.Arrays.sort;

import java.util.Comparator;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {}

    static double calculateOptimalValue(final int capacity, final Item[] items) {

        sort(items, BY_INCREASING_RATIO);

        double value = 0.0D;
        int remainingCapacity = capacity;
        int i = 0;
        while (remainingCapacity > 0 && i < items.length) {
            final Item item = items[i];
            final int allowedWeight = min(remainingCapacity, item.weight);
            value += item.getRatio() * allowedWeight;
            remainingCapacity -= allowedWeight;
            i += 1;
        }

        return value;

    }

    private static final Comparator<Item> BY_INCREASING_RATIO = new Comparator<Item>() {
        @Override
        public int compare(final Item o1, final Item o2) {
            return Double.compare(o2.getRatio(), o1.getRatio()); // Increasing
        }
    };

    @SuppressWarnings("PackageVisibleInnerClass")
    static final class Item {

        private final int value;
        private final int weight;

        Item(final int value, final int weight) {
            this.value = value;
            this.weight = weight;
        }

        private double getRatio() {
            return (double) value / weight;
        }

        @Override
        public String toString() {
            return format("'{'v={0},w={1}'}'", value, weight);
        }
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final int capacity = in.nextInt();
            final Item[] items = new Item[n];
            for (int i = 0; i < n; i++) {
                items[i] = new Item(in.nextInt(), in.nextInt());
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateOptimalValue(capacity, items));
        }
    }
}

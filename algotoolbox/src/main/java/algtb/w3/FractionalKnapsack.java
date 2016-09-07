/*
 *  algopad.
 */

package algtb.w3;

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

    static double calculateOptimalValue(final int capacity, final int[] values, final int[] weights) {

        final Item[] items = sortItemsByIncreasingRatio(values, weights);

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

    private static Item[] sortItemsByIncreasingRatio(final int[] values, final int[] weights) {
        final int n = values.length;
        final Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        sort(items, BY_INCREASING_RATIO);
        return items;
    }

    private static final Comparator<Item> BY_INCREASING_RATIO = new Comparator<Item>() {
        @Override
        public int compare(final Item o1, final Item o2) {
            return Double.compare(o2.getRatio(), o1.getRatio()); // Increasing
        }
    };

    private static final class Item {

        public final int value;
        public final int weight;

        private Item(final int value, final int weight) {
            this.value = value;
            this.weight = weight;
        }

        public double getRatio() {
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
            final int[] values = new int[n];
            final int[] weights = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = in.nextInt();
                weights[i] = in.nextInt();
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateOptimalValue(capacity, values, weights));
        }
    }
}

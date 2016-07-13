package algtb.w1;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

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

    public static void main(final String[] args) {
        final FastScanner scanner = new FastScanner(System.in);
        final int n = scanner.nextInt();
        final int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        //noinspection UseOfSystemOutOrSystemErr
        System.out.println(getMaxPairwiseProduct(numbers));
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static class FastScanner {

        private BufferedReader  reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream stream) {
            try {
                reader = new BufferedReader(new InputStreamReader(stream, UTF_8));
            } catch (final RuntimeException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return parseInt(next());
        }

        private static final Charset UTF_8 = Charset.forName("UTF-8");

    }
}
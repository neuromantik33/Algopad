/*
 *  algopad.
 */

package algtb.w5;

import java.util.Scanner;

import static java.lang.Character.digit;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.util.Arrays.asList;
import static java.util.Collections.max;
import static java.util.Collections.min;

/**
 * @author Nicolas Estrada.
 */
public final class PlacingParentheses {

    // private final int[] operands;
    private final char[] operators;

    private final int len;
    private final long[][] minValues;
    private final long[][] maxValues;

    private PlacingParentheses(final CharSequence exp) {

        this.len = exp.length() / 2 + 1;
        this.operators = new char[len - 1];

        final int[] operands = new int[len];
        parseExpression(exp, operands);

        this.minValues = new long[len][len];
        this.maxValues = new long[len][len];
        for (int i = 0; i < len; i++) {
            minValues[i][i] = operands[i];
            maxValues[i][i] = operands[i];
        }
    }

    private void parseExpression(final CharSequence exp, final int[] operands) {
        for (int i = 0, size = exp.length(); i < size; i++) {
            final int index = i / 2;
            final char c = exp.charAt(i);
            if (i % 2 == 0) {
                operands[index] = digit(c, 10);
            } else {
                operators[index] = c;
            }
        }
    }

    static long calculateMaximumValue(final String exp) {
        final PlacingParentheses pp = new PlacingParentheses(exp);
        return pp.calculateMaximumValue();
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private long calculateMaximumValue() {
        for (int j = 1; j <= len - 1; j++) {
            for (int i = 0; i < len - j; i++) {
                updateMinAndMaxValues(i, i + j);
            }
        }
        return maxValues[0][len - 1];
    }

    private final Long[] buf = new Long[5];

    private void updateMinAndMaxValues(final int i, final int j) {
        long min = MAX_VALUE;
        long max = MIN_VALUE;
        for (int k = i; k < j; k++) {

            final char op = operators[k];
            buf[1] = eval(maxValues[i][k], maxValues[k + 1][j], op);
            buf[2] = eval(maxValues[i][k], minValues[k + 1][j], op);
            buf[3] = eval(minValues[i][k], maxValues[k + 1][j], op);
            buf[4] = eval(minValues[i][k], minValues[k + 1][j], op);

            buf[0] = min;
            min = min(asList(buf));

            buf[0] = max;
            max = max(asList(buf));

        }
        minValues[i][j] = min;
        maxValues[i][j] = max;
    }

    private static long eval(final long x, final long y, final char op) {
        final long val;
        switch (op) {
            case ADD:
                val = x + y;
                break;
            case SUBTRACT:
                val = x - y;
                break;
            case MULTIPLY:
                val = x * y;
                break;
            default:
                throw new IllegalArgumentException("Bad operation : " + op);
        }
        return val;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final String exp = in.next();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(calculateMaximumValue(exp));
        }
    }

    private static final char ADD = '+';
    private static final char SUBTRACT = '-';
    private static final char MULTIPLY = '*';

}

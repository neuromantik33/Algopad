/*
 *  algopad.
 */

package algtb.w5;

import java.util.Scanner;

public class PlacingParentheses {

    static long getMaximumValue(final String exp) {
        //write your code here
        return 0;
    }

    private static long eval(final long a, final long b, final char op) {
        final long val;
        switch (op) {
            case ADD:
                val = a + b;
                break;
            case SUBTRACT:
                val = a - b;
                break;
            case MULTIPLY:
                val = a * b;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return val;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final String exp = in.next();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getMaximumValue(exp));
        }
    }

    private static final char ADD      = '+';
    private static final char SUBTRACT = '-';
    private static final char MULTIPLY = '*';

}

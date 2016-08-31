package algtb.w2;

import static algtb.w2.GCD.calculateGCD;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings("ClassNamingConvention")
public final class LCM {

    private LCM() {}

    static long calculateLCM(final int x, final int y) {
        final int xDivGcd = x / calculateGCD(x, y);
        return (long) xDivGcd * y;
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

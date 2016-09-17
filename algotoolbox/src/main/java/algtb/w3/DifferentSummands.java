package algtb.w3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class DifferentSummands {

    private DifferentSummands() {}

    private static List<Integer> optimalSummands(final int n) {
        return new ArrayList<>(n);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {

            final int n = in.nextInt();
            final List<Integer> summands = optimalSummands(n);

            System.out.println(summands.size());
            for (final Integer summand : summands) {
                System.out.print(summand + " ");
            }
        }
    }
}

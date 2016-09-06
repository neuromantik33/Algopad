package algtb.w3;

import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class Change {

    private Change() {}

    static int getChange(final int m) {
        int amount = m;
        int numCoins = 0;
        for (final int coin : COINS) {
            numCoins += amount / coin;
            amount %= coin;
        }
        return numCoins;
    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int m = in.nextInt();
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(getChange(m));
        }
    }

    // Coin types
    private static final int[] COINS = { 10, 5, 1 };

}


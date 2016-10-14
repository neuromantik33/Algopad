/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
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

package algopad.algorithms.pt1.w1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Class used to estimate the {@link Percolation} threshold, using the
 * following computational experiment: It initializes all sites to be
 * blocked and repeats the following until the system percolates:
 * <ul>
 * <li>Chooses a site (row i, column j) uniformly at random among all
 * blocked sites.</li>
 * <li>Opens the site (row i, column j).</li>
 * <li>The fraction of sites that are opened when the system percolates
 * provides an estimate of the percolation threshold.</li>
 * </ul>
 *
 * @author Nicolas Estrada.
 */
public class PercolationStats {

    private static final double CONFIDENCE_COEFFICIENT = 1.96D;

    private final int      size;
    private final double[] results;

    /**
     * @param N the grid dimension.
     * @param T the number of independent experiments.
     */
    @SuppressWarnings("MethodParameterNamingConvention")
    public PercolationStats(final int N, final int T) {
        if (T < 1) {
            throw new IllegalArgumentException("Bad number of trials");
        }
        size = N;
        results = new double[T];
        for (int i = 0; i < T; i++) {
            results[i] = runTrial();
        }
    }

    private double runTrial() {
        int numOpen = 0;
        final Percolation perc = new Percolation(size);
        while (!perc.percolates()) {
            openRandomSite(perc);
            numOpen++;
        }
        return calculateThreshold(numOpen);
    }

    private void openRandomSite(final Percolation perc) {
        while (true) {
            final int i = StdRandom.uniform(0, size) + 1;
            final int j = StdRandom.uniform(0, size) + 1;
            if (!perc.isOpen(i, j)) {
                perc.open(i, j);
                return;
            }
        }
    }

    private double calculateThreshold(final double numOpen) {
        return numOpen / (size * size);
    }

    /**
     * @return the sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * @return the sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * @return the low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - confidenceMargin();
    }

    /**
     * @return the high endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + confidenceMargin();
    }

    private double confidenceMargin() {
        return CONFIDENCE_COEFFICIENT * stddev() / Math.sqrt(results.length);
    }

    /**
     * Takes two command-line arguments N and T, performs T independent
     * computational experiments on an N-by-N grid, and prints the mean,
     * standard deviation, and the 95% confidence interval for the
     * percolation threshold.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        final int size = Integer.parseInt(args[0]);
        final int numTrials = Integer.parseInt(args[1]);
        final PercolationStats stats = new PercolationStats(size, numTrials);
        System.out.printf("mean                    = %s%n", stats.mean());
        System.out.printf("stddev                  = %s%n", stats.stddev());
        System.out.printf("95%% confidence interval = %s, %s%n",
                          stats.confidenceLo(), stats.confidenceHi());
    }
}

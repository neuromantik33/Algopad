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

package algopad.algorithms.pt2.w2;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

/**
 * This class is capable of resizing a W-by-H image using the seam-carving technique.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings({ "MethodCanBeVariableArityMethod",
                    "NonBooleanMethodNameMayNotStartWithQuestion",
                    "MethodWithMultipleLoops",
                    "ClassWithTooManyMethods" })
public class SeamCarver {

    private static final double BORDER_ENERGY = 1000.0D;

    private int realHeight;
    private int realWidth;

    private int[][] pixels;
    private boolean   transposed;

    public SeamCarver(final Picture picture) {

        this.realHeight = picture.height();
        this.realWidth = picture.width();
        this.pixels = new int[realHeight][realWidth];
        this.transposed = false;

        for (int x = 0; x < realWidth; x++) {
            for (int y = 0; y < realHeight; y++) {
                final Color color = picture.get(x, y);
                pixels[y][x] = color.getRGB();
            }
        }
    }

    /**
     * @return the current picture.
     */
    public Picture picture() {

        setTranspose(false);

        final int height = intHeight();
        final int width = intWidth();

        final Picture picture = new Picture(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                picture.set(x, y, new Color(pixels[y][x]));
            }
        }

        return picture;

    }

    /**
     * @return the width of current picture.
     */
    public int width() {
        return realWidth;
    }

    // Internal width
    private int intWidth() {
        return pixels[0].length;
    }

    /**
     * @return the height of current picture.
     */
    public int height() {
        return realHeight;
    }

    // Internal height
    private int intHeight() {
        return pixels.length;
    }

    /**
     * @return the energy of the pixel at column <i>x</i> and row <i>y</i>.
     */
    public double energy(final int x, final int y) {
        final boolean badRow = y < 0 || y > height() - 1;
        final boolean badCol = x < 0 || x > width() - 1;
        if (badRow || badCol) {
            throw new IndexOutOfBoundsException();
        }
        setTranspose(false);
        return intEnergy(x, y);
    }

    // Internal energy
    private double intEnergy(final int x, final int y) {
        return energyFor(x, y);
    }

    private double energyFor(final int x, final int y) {

        final boolean atColEndpoint = x == 0 || x == intWidth() - 1;
        final boolean atRowEndpoint = y == 0 || y == intHeight() - 1;
        if (atColEndpoint || atRowEndpoint) {
            return BORDER_ENERGY;
        }

        final long colGradient = dualGradient(x + 1, y, x - 1, y);
        final long rowGradient = dualGradient(x, y + 1, x, y - 1);
        return Math.sqrt(colGradient + rowGradient);

    }

    private long dualGradient(final int x1, final int y1,
                              final int x2, final int y2) {
        final int color1 = pixels[y1][x1];
        final int color2 = pixels[y2][x2];
        final int dr = red(color1) - red(color2);
        final int dg = green(color1) - green(color2);
        final int db = blue(color1) - blue(color2);
        return dr * dr + dg * dg + db * db;
    }

    private static int red(final int rgb) {
        //noinspection MagicNumber
        return rgb >> 16 & 0xFF;
    }

    private static int green(final int rgb) {
        //noinspection MagicNumber
        return rgb >> 8 & 0xFF;
    }

    private static int blue(final int rgb) {
        //noinspection MagicNumber
        return rgb & 0xFF;
    }

    /**
     * @return the sequence of indices for a horizontal seam.
     */
    public int[] findHorizontalSeam() {
        setTranspose(true);
        return findSeam();
    }

    /**
     * @return the sequence of indices for a vertical seam.
     */
    public int[] findVerticalSeam() {
        setTranspose(false);
        return findSeam();
    }

    @SuppressWarnings("OverlyLongMethod")
    private int[] findSeam() {

        final int size = 1 + intHeight() * intWidth();
        final double[] distTo = new double[size];
        final int[] edgeTo = new int[size];

        Arrays.fill(distTo, 1, distTo.length, Double.POSITIVE_INFINITY);

        final int height = intHeight();
        final int width = intWidth();

        for (int i = 1; i <= width; i++) {
            distTo[i] = BORDER_ENERGY;
            edgeTo[i] = 0;
        }

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {

                final int v = coordToIndex(x, y);
                final int next = y + 1;

                if (x > 0) { relax(v, x - 1, next, distTo, edgeTo); }
                relax(v, x, next, distTo, edgeTo);
                if (x < width - 1) { relax(v, x + 1, next, distTo, edgeTo); }

            }
        }

        // Save "closest" bottom cell
        int index = 1;
        double distance = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; x++) {
            final int w = coordToIndex(x, height - 1);
            if (distTo[w] < distance) {
                index = w;
                distance = distTo[w];
            }
        }

        // Transform the path into a seam
        int idx = height - 1;
        final int[] seam = new int[height];
        while (index != 0) {
            seam[idx--] = (index - 1) % width;
            index = edgeTo[index];
        }

        return seam;

    }

    private void relax(final int v, final int x, final int y,
                       final double[] distTo, final int[] edgeTo) {
        final int w = coordToIndex(x, y);
        final double distance = distTo[v] + intEnergy(x, y);
        if (distTo[w] > distance) {
            distTo[w] = distance;
            edgeTo[w] = v;
        }
    }

    private int coordToIndex(final int col, final int row) {
        final boolean badRow = row < 0 || row > intHeight() - 1;
        final boolean badCol = col < 0 || col > intWidth() - 1;
        if (badRow || badCol) {
            throw new IndexOutOfBoundsException();
        }
        return row * intWidth() + col + 1;
    }

    /**
     * Removes the horizontal <i>seam</i> from current picture.
     */
    public void removeHorizontalSeam(final int[] seam) {
        verifyHorizontalSeam(seam);
        setTranspose(true);
        removeSeam(seam);
        realHeight -= 1;
    }

    private void verifyHorizontalSeam(final int[] seam) {
        if (realWidth != seam.length || realHeight <= 1) {
            throw new IllegalArgumentException();
        }
        int last = -1;
        for (int i = 0; i < realWidth; i++) {
            final int y = seam[i];
            if (y < 0 || y > realHeight - 1) {
                throw new IllegalArgumentException();
            }
            if (i > 0 && Math.abs(y - last) > 1) {
                throw new IllegalArgumentException();
            }
            last = y;
        }
    }

    /**
     * Removes the vertical <i>seam</i> from current picture.
     */
    public void removeVerticalSeam(final int[] seam) {
        verifyVerticalSeam(seam);
        setTranspose(false);
        removeSeam(seam);
        realWidth -= 1;
    }

    private void verifyVerticalSeam(final int[] seam) {
        if (realHeight != seam.length || realWidth <= 1) {
            throw new IllegalArgumentException();
        }
        int last = -1;
        for (int i = 0; i < realHeight; i++) {
            final int x = seam[i];
            if (x < 0 || x > realWidth - 1) {
                throw new IllegalArgumentException();
            }
            if (i > 0 && Math.abs(x - last) > 1) {
                throw new IllegalArgumentException();
            }
            last = x;
        }
    }

    private void removeSeam(final int[] seam) {

        final int h = intHeight();
        final int w = intWidth();

        for (int y = 0; y < h; y++) {
            final int x = seam[y];
            final int[] row = pixels[y];
            final int[] tmp = new int[w - 1];
            System.arraycopy(row, 0, tmp, 0, x);
            System.arraycopy(row, x + 1, tmp, x, w - x - 1);
            pixels[y] = tmp;
        }
    }

    private void setTranspose(final boolean transpose) {
        if (transposed == transpose) {
            return;
        }
        transposed = transpose;
        pixels = transposePixels(pixels);
    }

    private static int[][] transposePixels(final int[][] array) {
        final int[][] transposed = new int[array[0].length][array.length];
        for (int i = 0, len = array.length; i < len; i++) {
            for (int j = 0, len2 = array[0].length; j < len2; j++) {
                transposed[j][i] = array[i][j];
            }
        }
        return transposed;
    }
}

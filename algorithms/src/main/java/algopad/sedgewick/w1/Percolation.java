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

package algopad.sedgewick.w1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * An abstraction of a Percolation system using an N-by-N grid of <i>sites</i>.
 * Each site is either <i>open</i> or <i>blocked</i>.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
public class Percolation {

    private static final int[][] OFFSETS =
      {
        { -1, 0 }, // Top
        { 0, 1 },  // Left
        { 1, 0 },  // Bottom
        { 0, -1 }  // Right
      };

    // Component status bits taken from 15UnionFind-Tarjan.pdf
    private static final int CONNECTED_TO_TOP    = 0b001;
    private static final int CONNECTED_TO_BOTTOM = 0b010;

    private final WeightedQuickUnionUF unionFind;

    private       boolean percolates;
    private final int     size;

    private final BitArray openSites;
    private final byte[]   rootStatus;

    /**
     * Creates an N-by-N grid, with all sites blocked.
     *
     * @param N the size of the grid square.
     */
    @SuppressWarnings("MethodParameterNamingConvention")
    public Percolation(final int N) {

        if (N < 1) {
            throw new IllegalArgumentException("Bad size");
        }

        size = N;

        unionFind = new WeightedQuickUnionUF(N * N);
        openSites = new BitArray(N * N);
        rootStatus = new byte[N * N];

    }

    /**
     * Opens a site located at {@code (i,j)}.
     */
    public void open(final int i, final int j) {
        final int idx = coordToIndex(i, j);
        if (!openSites.get(idx)) {

            openSites.set(idx, true);

            byte status = 0;
            if (i == 1) { // Is at top row?
                status = CONNECTED_TO_TOP;
            }
            if (i == size) { // Is at bottom row
                status |= CONNECTED_TO_BOTTOM;
            }

            status = connectToNeighboringSites(i, j, status);
            updateStatusAt(idx, status);
            verifyIfPercolated(status);

        }
    }

    private void updateStatusAt(final int site, final byte status) {
        final int root = unionFind.find(site);
        rootStatus[root] = status;
    }

    private byte connectToNeighboringSites(final int i, final int j, byte status) {
        final int idx = coordToIndex(i, j);
        for (final int[] offset : OFFSETS) {
            final int x = i + offset[0];
            final int y = j + offset[1];
            if (!checkCoordinate(x, y) && isOpen(x, y)) {
                final int neighborIdx = coordToIndex(x, y);
                //noinspection AssignmentToMethodParameter
                status = connectToNeighbor(idx, neighborIdx, status);
            }
        }
        return status;
    }

    // Connects to the neighbor and updates the arg status returning the new one
    private byte connectToNeighbor(final int idx,
                                   final int neighborIdx,
                                   byte status) {
        // Update the status with the neighbor's root status
        //noinspection AssignmentToMethodParameter
        status |= rootStatus[unionFind.find(neighborIdx)];
        unionFind.union(idx, neighborIdx);
        return status;
    }

    /**
     * Checks if the <i>status</i> represents a percolation point.
     */
    private void verifyIfPercolated(final byte status) {
        if ((status & CONNECTED_TO_TOP) > 0 &&
            (status & CONNECTED_TO_BOTTOM) > 0) {
            percolates = true;
        }
    }

    /**
     * @return {@code true} if the site located at {@code (i,j)} is open,
     * {@code false} otherwise.
     */
    public boolean isOpen(final int i, final int j) {
        return openSites.get(coordToIndex(i, j));
    }

    /**
     * A full site is an open site that can be connected to an open site in the top
     * row via a chain of neighboring (left, right, up, down) open sites.
     *
     * @return {@code true} if the site located at {@code (i,j)} is full,
     * {@code false} otherwise.
     */
    public boolean isFull(final int i, final int j) {
        final int root = unionFind.find(coordToIndex(i, j));
        return (rootStatus[root] & CONNECTED_TO_TOP) > 0;
    }

    /**
     * A system percolates if there is a full site in the bottom row. In other words,
     * a system percolates if we fill all open sites connected to the top row and
     * that process fills some open site on the bottom row.
     *
     * @return {@code true} if the system percolates, {@code false} otherwise.
     */
    public boolean percolates() {
        return percolates;
    }

    /**
     * @throws IndexOutOfBoundsException if either <i>i</i> or <i>j</i> are invalid.
     */
    private void verifyCoordinates(final int i, final int j) {
        if (checkCoordinate(i, j)) {
            throw new IndexOutOfBoundsException("Bad coordinate");
        }
    }

    private boolean checkCoordinate(final int i, final int j) {
        return checkIndex(i) || checkIndex(j);
    }

    private boolean checkIndex(final int idx) {
        return idx < 1 || idx > size;
    }

    private int coordToIndex(final int i, final int j) {
        verifyCoordinates(i, j);
        return (i - 1) * size + j - 1;
    }

    @SuppressWarnings({ "ProhibitedExceptionThrown", "PackageVisibleInnerClass" })
    static final class BitArray {

        private static final int BITS_PER_UNIT = 8;
        private final byte[] repn;
        private final int    length;

        private BitArray(final int length) {
            if (length < 0) { throw new IllegalArgumentException("Bad length"); }
            this.length = length;
            repn = new byte[(length + BITS_PER_UNIT - 1) / BITS_PER_UNIT];
        }

        public boolean get(final int index) {
            if (index < 0 || index >= length) {
                throw new ArrayIndexOutOfBoundsException(Integer.toString(index));
            }
            return (repn[subscript(index)] & position(index)) != 0;
        }

        @SuppressWarnings("ImplicitNumericConversion")
        private void set(final int index, final boolean value) {
            if (index < 0 || index >= length) {
                throw new ArrayIndexOutOfBoundsException(Integer.toString(index));
            }
            final int idx = subscript(index);
            final int bit = position(index);

            if (value) {
                repn[idx] |= bit;
            } else {
                repn[idx] &= ~bit;
            }
        }

        private static int subscript(final int idx) {
            return idx / BITS_PER_UNIT;
        }

        private static int position(final int idx) { // bits big-endian in each unit
            return 1 << BITS_PER_UNIT - 1 - idx % BITS_PER_UNIT;
        }

        public int length() {
            return length;
        }
    }
}

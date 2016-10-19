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

package algopad.algorithms.pt1.w4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import edu.princeton.cs.algs4.StdRandom;

/**
 * A representation of a board in the A* puzzle goal finding algorithm.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("EqualsAndHashcode")
public class Board {

    private static final int EMPTY_TILE    = 0;
    private static final int MAX_NEIGHBORS = 4;

    private final int     dim;
    private final short[] tiles;

    // Manhattan and hamming distances
    private int manhattan;
    private int hamming;

    // Index of empty block (used to calculate neighbors)
    private int emptyTileIdx = -1;

    // Public constructor
    public Board(final int[][] blocks) {

        if (blocks == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }

        dim = blocks.length;
        tiles = new short[dim * dim];

        for (int i = 0; i < dim; i++) {
            processRow(blocks[i], i);
        }
    }

    // Private constructor used for calculating neighbors and twins
    private Board(final int dim, final short[] tiles) {
        this.dim = dim;
        this.tiles = tiles;
        for (int i = 0, len = tiles.length; i < len; i++) {
            processTile(i);
        }
    }

    private void processRow(final int[] row, final int i) {
        for (int j = 0; j < dim; j++) {
            final int idx = coordToIndex(i, j);
            //noinspection NumericCastThatLosesPrecision
            tiles[idx] = (short) row[j];
            processTile(idx);
        }
    }

    private int coordToIndex(final int x, final int y) {
        return x * dim + y;
    }

    private void processTile(final int idx) {
        if (tiles[idx] == EMPTY_TILE) {
            emptyTileIdx = idx;
        } else {
            updateDistances(idx);
        }
    }

    private void updateDistances(final int i) {
        final int value = tiles[i] - 1;
        updateManhattanDistance(value, i);
        updateHammingDistance(value, i);
    }

    private void updateManhattanDistance(final int value, final int i) {
        final int dx = Math.abs(i / dim - value / dim);
        final int dy = Math.abs(i % dim - value % dim);
        manhattan += dx + dy;
    }

    private void updateHammingDistance(final int value, final int i) {
        if (value != i) {
            hamming++;
        }
    }

    /**
     * @return the board dimension.
     */
    public int dimension() {
        return dim;
    }

    /**
     * @return the number of blocks out of place (hamming distance).
     */
    public int hamming() {
        return hamming;
    }

    /**
     * @return the sum of Manhattan distances between the blocks and goal.
     */
    public int manhattan() {
        return manhattan;
    }

    /**
     * @return {@code true} if this board is the goal board, {@code false} otherwise.
     */
    public boolean isGoal() {
        return manhattan == 0;
    }

    /**
     * @return a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {
        final int[] indexes = chooseRandomTiles();
        return swapAndClone(indexes[0], indexes[1]);
    }

    private int[] chooseRandomTiles() {
        final int[] indexes = new int[2];
        while (true) {
            indexes[0] = StdRandom.uniform(tiles.length);
            indexes[1] = StdRandom.uniform(tiles.length);
            if (indexes[0] != indexes[1] &&
                tiles[indexes[0]] != EMPTY_TILE &&
                tiles[indexes[1]] != EMPTY_TILE) {
                break;
            }
        }
        return indexes;
    }

    private Board swapAndClone(final int i1, final int i2) {
        final short[] copy = tiles.clone();
        swap(copy, i1, i2);
        return new Board(dim, copy);
    }

    private static void swap(final short[] array, final int i, final int j) {
        final short tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /**
     * @return {@code true} if this object is the same as the obj argument,
     * {@code false} otherwise.
     */
    @SuppressWarnings({ "NonFinalFieldReferenceInEquals",
                        "ParameterNameDiffersFromOverriddenParameter" })
    public boolean equals(final Object y) {

        if (y == null) { return false; }
        if (this == y) { return true; }
        if (getClass() != y.getClass()) { return false; }

        final Board other = (Board) y;

        //noinspection SimplifiableIfStatement
        if (dim != other.dim ||
            manhattan != other.manhattan ||
            hamming != other.hamming) {
            return false;
        }

        return Arrays.equals(tiles, other.tiles);

    }

    /**
     * @return an {@link Iterable} of all neighboring boards.
     */
    public Iterable<Board> neighbors() {

        final Collection<Board> neighbors = new ArrayList<>(MAX_NEIGHBORS);
        for (final Direction dir : Direction.values()) {
            if (!isEmptyTileAtBoundary(dir)) {
                final int idx = emptyTileIdx + dir.offset(dim);
                if (idx >= 0 && idx < tiles.length) {
                    neighbors.add(swapAndClone(idx, emptyTileIdx));
                }
            }
        }

        return neighbors;

    }

    private boolean isEmptyTileAtBoundary(final Direction dir) {
        final int mod = emptyTileIdx % dim;
        final boolean atLeftBoundary = dir == Direction.LEFT && mod == 0;
        final boolean atRightBoundary = dir == Direction.RIGHT && mod == dim - 1;
        return atLeftBoundary || atRightBoundary;
    }

    /**
     * @return a string representation of the board.
     */
    public String toString() {

        //noinspection HardcodedLineSeparator
        final String eol = "\n";
        final StringBuilder sb = new StringBuilder(tiles.length << 1);
        sb.append(dim).append(eol);

        final StringBuilder line = new StringBuilder(dim << 1);
        for (int i = 0, len = tiles.length; i < len; i++) {
            line.append(String.format("%1$2d ", tiles[i]));
            if ((i + 1) % dim == 0) {
                sb.append(line).append(eol);
                line.delete(0, line.length());
            }
        }

        return sb.toString();

    }

    /**
     * Used for neighbor calculation.
     */
    private enum Direction {

        LEFT, RIGHT, UP, DOWN;

        public int offset(final int dimension) {
            int result = -1;
            switch (this) {
                case LEFT:
                    result = -1;
                    break;
                case RIGHT:
                    result = 1;
                    break;
                case UP:
                    result = -dimension;
                    break;
                case DOWN:
                    result = dimension;
                    break;
            }
            return result;
        }
    }
}

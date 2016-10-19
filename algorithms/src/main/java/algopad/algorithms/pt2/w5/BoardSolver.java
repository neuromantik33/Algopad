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

package algopad.algorithms.pt2.w5;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class used to solve Boggle boards.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("MagicCharacter")
public class BoardSolver {

    // Offsets for computing tile indices in each direction.
    private static final int[][] OFFSETS;

    static {
        OFFSETS = new int[8][2];
        OFFSETS[0] = new int[] { -1, 0 }; // UP
        OFFSETS[1] = new int[] { -1, 1 }; // UP_RIGHT
        OFFSETS[2] = new int[] { 0, 1 }; // RIGHT
        OFFSETS[3] = new int[] { 1, 1 }; // DOWN_RIGHT
        OFFSETS[4] = new int[] { 1, 0 }; // DOWN
        OFFSETS[5] = new int[] { 1, -1 }; // DOWN_LEFT
        OFFSETS[6] = new int[] { 0, -1 }; // LEFT
        OFFSETS[7] = new int[] { -1, -1 }; // UP_LEFT
    }

    private final BoggleBoard board;
    private final BitSet      marked;
    private final Set<String> words;

    public BoardSolver(final BoggleBoard board) {
        final int size = board.rows() * board.cols();
        this.board = board;
        this.marked = new BitSet(size);
        this.words = new HashSet<>(1 << 8, 0.9F); // 256
    }

    public Collection<String> findWords(final BoggleTrie dict) {
        for (int row = 0, rows = board.rows(); row < rows; row++) {
            for (int col = 0, cols = board.cols(); col < cols; col++) {
                final char c = board.getLetter(row, col);
                final BoggleTrie trie = dict.getSubTrie(c);
                if (trie != null) {
                    search(row, col, c, trie);
                }
            }
        }
        return words;
    }

    private void search(final int row,
                        final int col,
                        final char letter,
                        BoggleTrie trie) {

        final int idx = coordToIndex(row, col);
        marked.set(idx);

        // If the letter is a Q, implicitly add the 'U' character
        if (letter == 'Q') {
            //noinspection AssignmentToMethodParameter
            trie = trie.getSubTrie('U');
            if (trie == null) {
                marked.clear(idx);
                return;
            }
        }

        final String word = trie.getWord();
        if (word != null) {
            words.add(word);
        }

        for (final int[] offset : OFFSETS) {
            final int newRow = row + offset[0];
            final int newCol = col + offset[1];
            if (isLetter(newRow, newCol)) {
                final int newIdx = coordToIndex(newRow, newCol);
                if (!marked.get(newIdx)) {
                    final char newLetter = board.getLetter(newRow, newCol);
                    final BoggleTrie newTrie = trie.getSubTrie(newLetter);
                    if (newTrie != null) {
                        search(newRow, newCol, newLetter, newTrie);
                    }
                }
            }
        }

        marked.clear(idx);

    }

    private boolean isLetter(final int row, final int col) {
        //noinspection OverlyComplexBooleanExpression
        return row >= 0 && row < board.rows() && col >= 0 && col < board.cols();
    }

    private int coordToIndex(final int row, final int col) {
        return row * board.cols() + col;
    }
}

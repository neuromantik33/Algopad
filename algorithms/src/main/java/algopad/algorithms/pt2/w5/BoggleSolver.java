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

/*
 *  algorithms.pt1.
 */

package algopad.algorithms.pt2.w5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Boggle is a word game designed by Allan Turoff and distributed by Hasbro.
 * It involves a board made up of 16 cubic dice, where each die has a letter
 * printed on each of its sides. At the beginning of the game, the 16 dice
 * are shaken and randomly distributed into a 4-by-4 tray, with only the top
 * sides of the dice visible.<br>
 * The players compete to accumulate points by building valid words out of
 * the dice according to the following rules:
 * <ul>
 * <li>A valid word must be composed by following a sequence of adjacent
 * dice, two dice are adjacent if they are horizontal, vertical, or diagonal
 * neighbors.</li>
 * <li>A valid word can use each die at most once.</li>
 * <li>A valid word must contain at least 3 letters.</li>
 * <li>A valid word must be in the dictionary (which typically does not contain
 * proper nouns).</li>
 * </ul>
 * This class is an implementation of a boggle solver that finds all valid words
 * in a given {@link BoggleBoard}, using a given dictionary.<br>
 * <b>Note</b> : All words contain only the uppercase letters A through Z.
 *
 * @author Nicolas Estrada.
 */
public class BoggleSolver {

    private final BoggleTrie trie;

    public BoggleSolver(final String[] dictionary) {
        this.trie = new BoggleTrie();
        for (final String word : dictionary) {
            if (word.length() > 2) {
                trie.add(word);
            }
        }
    }

    /**
     * @return the set of all valid words in the given Boggle <i>board</i>.
     */
    public Iterable<String> getAllValidWords(final BoggleBoard board) {
        return new BoardSolver(board).findWords(trie);
    }

    /**
     * @return the score of the given <i>word</i> if it is in the dictionary,
     * {@code 0} otherwise.
     */
    public int scoreOf(final String word) {

        final int len;

        //noinspection IfMayBeConditional
        if (word == null || !trie.contains(word)) {
            len = 0;
        } else {
            len = word.length();
        }

        final int score;
        //noinspection IfStatementWithTooManyBranches
        if (len < 3) {
            score = 0;
        } else if (len < 5) {
            score = 1;
        } else if (len == 5) {
            score = 2;
        } else if (len == 6) {
            score = 3;
        } else //noinspection IfMayBeConditional
            if (len == 7) {
                score = 5;
            } else {
                score = 11;
            }

        return score;

    }

    // Main test harness
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final String[] dictionary = in.readAllStrings();
        final BoggleSolver solver = new BoggleSolver(dictionary);
        final BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (final String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

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

package algopad.algorithms.pt1.w4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.MinPQ;

/**
 * Given an initial {@link Board}, this class is responsible for finding
 * a solution using the A* algorithm.
 *
 * @author Nicolas Estrada.
 */
public class Solver {

    private final MinPQ<BoardKey> boardQueue;
    private final Board           initial;
    private final Board           initialTwin;

    private BoardKey goal;

    public Solver(final Board initial) {

        this.boardQueue = new MinPQ<>(new BoardKeyComparator());
        this.initial = initial;
        this.initialTwin = initial.twin();

        solve();

    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "FeatureEnvy" })
    private void solve() {

        // Init
        boardQueue.insert(new BoardKey(initial, 0, null));
        boardQueue.insert(new BoardKey(initialTwin, 0, null));

        while (true) {

            goal = boardQueue.delMin();

            final Board current = goal.board;
            if (current.isGoal()) {
                break;
            }

            for (final Board neighbor : current.neighbors()) {
                final BoardKey prevKey = goal.prev;
                if (prevKey == null || !neighbor.equals(prevKey.board)) {
                    addNeighborToQueue(neighbor, goal);
                }
            }
        }
    }

    @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
    private void addNeighborToQueue(final Board neighbor, final BoardKey parent) {
        final BoardKey key = new BoardKey(neighbor, parent.moves + 1, parent);
        boardQueue.insert(key);
    }

    /**
     * @return {@code true} if the initial board is solvable,
     * {@code false} otherwise.
     */
    public boolean isSolvable() {

        Board origin = null;
        for (final Board board : goal) {
            origin = board;
        }

        final boolean answer = initial.equals(origin);
        if (!answer) {
            if (!initialTwin.equals(origin)) {
                throw new AssertionError();
            }
        }

        return answer;

    }

    /**
     * @return the minimum number of moves to solve the initial board,
     * or -1 if unsolvable.
     */
    public int moves() {
        //noinspection IfMayBeConditional
        if (isSolvable()) {
            return goal.moves;
        }
        return -1;
    }

    /**
     * @return sequence of boards in a shortest solution,
     * {@code null} if unsolvable.
     */
    public Iterable<Board> solution() {
        if (isSolvable()) {
            //noinspection CollectionWithoutInitialCapacity
            final List<Board> solution = new ArrayList<>();
            for (final Board board : goal) {
                solution.add(board);
            }
            Collections.reverse(solution);
            return solution;
        }
        //noinspection ReturnOfNull
        return null;
    }

    /**
     * A simple data structure used by the min heap, which "scores" a board by
     * adding the number of moves along with the manhattan distance to the goal.
     * It also keeps a copy of the previous board in order to find a solution.
     */
    private static final class BoardKey implements Iterable<Board> {

        private final Board board;
        private final int   moves;
        private final int   score;

        private final BoardKey prev;

        private BoardKey(final Board board,
                         final int moves,
                         final BoardKey prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.score = moves + board.manhattan();
        }

        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator(this);
        }

    }

    /**
     * {@link Comparator} for board keys.
     */
    private static final class BoardKeyComparator implements Comparator<BoardKey> {

        @Override
        public int compare(final BoardKey o1, final BoardKey o2) {
            return Integer.compare(o1.score, o2.score);
        }
    }

    /**
     * Used to iterate through the solution chain.
     */
    private static final class BoardIterator implements Iterator<Board> {

        private BoardKey key;

        private BoardIterator(final BoardKey key) {
            this.key = key;
        }

        @Override
        public boolean hasNext() {
            return key != null;
        }

        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            //noinspection TooBroadScope
            final Board board = key.board;
            key = key.prev;
            return board;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

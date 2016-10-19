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

package algopad.algorithms.pt2.w6;

import edu.princeton.cs.algs4.Stack;

/**
 * Represents an abstraction of a sorted array of the N circular suffixes of
 * a string of length N.<br>
 * It uses a variation of 3-way radix quick-sort to implicitly create
 * the sorted suffix array.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("MethodWithMultipleLoops")
public class CircularSuffixArray {

    // cutoff to insertion sort
    private static final int CUTOFF = 15;

    private final int[] indices;

    public CircularSuffixArray(final String s) {

        if (s == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }

        final int len = s.length();
        this.indices = new int[len];
        for (int i = 0; i < len; i++) {
            indices[i] = i;
        }

        sortSuffixes(s, len);
    }

    @SuppressWarnings("ValueOfIncrementOrDecrementUsed")
    private void sortSuffixes(final String s, final int len) {

        final Stack<int[]> stack = new Stack<>();
        stack.push(new int[] { 0, len - 1, 0 });

        //noinspection MethodCallInLoopCondition
        while (!stack.isEmpty()) {

            final int[] frame = stack.pop();
            final int lo = frame[0];
            final int hi = frame[1];
            final int offset = frame[2];

            if (hi <= lo + CUTOFF) {
                insertion(s, lo, hi, offset);
            } else {
                final int v = charAt(s, lo, offset);
                int lt = lo;
                int gt = hi;
                int i = lo + 1;
                while (i <= gt) {
                    final int t = charAt(s, i, offset);
                    if (t < v) {
                        swap(lt++, i++);
                    } else if (t > v) {
                        swap(i, gt--);
                    } else {
                        i++;
                    }
                }

                if (lo < lt) {
                    stack.push(new int[] { lo, lt - 1, offset });
                }
                if (v > -1 && offset < len) {
                    stack.push(new int[] { lt, gt, offset + 1 });
                }
                if (gt < hi) {
                    stack.push(new int[] { gt + 1, hi, offset });
                }
            }
        }
    }

    private void insertion(final String s,
                           final int lo,
                           final int hi,
                           final int offset) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                if (less(s, j, j - 1, offset)) {
                    swap(j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    private boolean less(final String s,
                         final int v,
                         final int w,
                         final int offset) {
        for (int i = offset, len = length(); i < len; i++) {
            final char vC = charAt(s, v, i);
            final char wC = charAt(s, w, i);
            if (vC == wC) { continue; }
            //noinspection CharacterComparison
            return vC < wC;
        }
        return false;
    }

    private char charAt(final String s, final int idx, final int offset) {
        final int start = indices[idx];
        return s.charAt((start + offset) % s.length());
    }

    private void swap(final int i, final int j) {
        final int temp = indices[i];
        indices[i] = indices[j];
        indices[j] = temp;
    }

    /**
     * @return the length of <i>s</i>.
     */
    public int length() {
        return indices.length;
    }

    /**
     * @return returns the index of <i>i</i>th sorted suffix.
     */
    public int index(final int i) {
        return indices[i];
    }
}

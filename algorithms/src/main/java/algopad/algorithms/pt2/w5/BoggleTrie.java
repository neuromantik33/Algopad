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

/**
 * The main API for accessing a string trie for the game of Boggle.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
public class BoggleTrie {

    @SuppressWarnings("DuplicateStringLiteralInspection")
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char   START    = ALPHABET.charAt(0);

    private final String       word;
    private final BoggleTrie[] next;

    public BoggleTrie() {
        this(null);
    }

    private BoggleTrie(final String word) {
        this.word = word;
        this.next = new BoggleTrie[ALPHABET.length()];
    }

    /**
     * @return the sub {@code BoggleTrie} corresponding to <i>letter</i>,
     * or {@code null} if absent.
     */
    public BoggleTrie getSubTrie(final char letter) {
        return next[indexFor(letter)];
    }

    /**
     * @return {@code true} if the trie contains the <i>key</i>,
     * {@code false} otherwise.
     */
    public boolean contains(final String key) {

        BoggleTrie x = this;
        int i = 0;
        final int len = key.length();
        while (x != null && i < len) {
            final int idx = indexFor(key.charAt(i));
            x = x.next[idx];
            i++;
        }

        return x != null && x.word != null;

    }

    /**
     * @return the word if present, {@code null} if absent.
     */
    public String getWord() {
        return word;
    }

    /**
     * Adds the <i>key</i> to the trie if it is not already present.
     */
    public void add(final String key) {
        add(this, key, 0);
    }

    @SuppressWarnings("AssignmentToMethodParameter")
    private static BoggleTrie add(BoggleTrie x, final String key, final int i) {
        final boolean wordFound = i == key.length();
        if (x == null) {
            if (wordFound) {
                x = new BoggleTrie(key);
                //noinspection ResultOfMethodCallIgnored (Pre-calculate hash code)
                key.hashCode();
            } else {
                x = new BoggleTrie();
            }
        }
        if (!wordFound) {
            final int idx = indexFor(key.charAt(i));
            x.next[idx] = add(x.next[idx], key, i + 1);
        }
        return x;
    }

    private static int indexFor(final char c) {
        //noinspection CharUsedInArithmeticContext
        return c - START;
    }
}

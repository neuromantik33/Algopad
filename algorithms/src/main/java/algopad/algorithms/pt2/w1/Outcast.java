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

package algopad.algorithms.pt2.w1;

/**
 * Given a list of wordnet nouns <i>A1, A2, ..., An</i>, which noun is the
 * least related to the others?<br>
 * This class is used to identify an outcast, and compute the sum of the
 * distances between each noun and every other one:
 * <pre>di = dist(Ai, A1) + dist(Ai, A2) + ... + dist(Ai, An)</pre>
 * and return a noun <i>At</i> for which <tt>dt</tt> is maximum.
 *
 * @author Nicolas Estrada.
 */
public class Outcast {

    private final WordNet wordNet;

    public Outcast(final WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * @return an outcast given an array of {@link WordNet} nouns.
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public String outcast(final String[] nouns) {

        final int len = nouns.length;
        if (len < 2) {
            throw new IllegalArgumentException();
        }

        final int[] distances = calculateDistances(nouns);
        return findOutcast(nouns, distances);

    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private int[] calculateDistances(final String[] nouns) {
        final int len = nouns.length;
        final int[] distances = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) {
                    continue;
                }
                distances[i] += wordNet.distance(nouns[i], nouns[j]);
            }
        }
        return distances;
    }

    private static String findOutcast(final String[] nouns, final int[] distances) {
        int idx = -1;
        int max = 0;
        for (int i = 0, len = nouns.length; i < len; i++) {
            final int distance = distances[i];
            if (distance > max) {
                max = distance;
                idx = i;
            }
        }
        return nouns[idx];
    }
}

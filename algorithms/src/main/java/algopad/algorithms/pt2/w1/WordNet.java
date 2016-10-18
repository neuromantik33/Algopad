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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

/**
 * This class represents is a semantic lexicon for the English language that is
 * used extensively by computational linguists and cognitive scientists.<br>
 * A WordNet groups words into sets of synonyms called <i>synsets</i> and
 * describes semantic relationships between them. One such relationship is
 * the <i>is-a</i> relationship, which connects a <i>hyponym</i>
 * (more specific <i>synset</i>) to a hypernym (more general synset).
 *
 * @author Nicolas Estrada/
 */
public class WordNet {

    private static final int DEFAULT_CAPACITY = 16;

    private final Digraph graph;
    private final SAP     sap;

    // Map of nouns -> vertices
    private final Map<String, List<Integer>> nounMap;

    // Map of vertices -> primary nouns
    private final Map<Integer, String> vertexMap;

    public WordNet(final String synsets, final String hypernyms) {

        verifyNotnull(synsets, hypernyms);

        this.nounMap = new HashMap<>(DEFAULT_CAPACITY);
        this.vertexMap = new HashMap<>(DEFAULT_CAPACITY);
        this.graph = initGraph(synsets);
        populateGraph(hypernyms);
        this.sap = new SAP(graph);

    }

    private Digraph initGraph(final String synsets) {
        final In input = new In(synsets);
        int numV = 0;
        try {
            String line;
            //noinspection NestedAssignment
            while ((line = input.readLine()) != null) {
                parseSynsetLine(line);
                numV++;
            }
        } finally {
            input.close();
        }
        return new Digraph(numV);
    }

    private void parseSynsetLine(final String line) {
        final String[] parts = line.split(",");
        final int v = Integer.parseInt(parts[0]);
        final String nouns = parts[1];
        for (final String noun : nouns.split(" ")) {
            List<Integer> vertices = nounMap.get(noun);
            if (vertices == null) {
                vertices = new ArrayList<>(DEFAULT_CAPACITY);
                nounMap.put(noun, vertices);
            }
            vertices.add(v);
        }
        vertexMap.put(v, nouns);
    }

    private void populateGraph(final String hypernyms) {

        final In input = new In(hypernyms);
        try {
            String line;
            //noinspection MethodCallInLoopCondition,NestedAssignment
            while ((line = input.readLine()) != null) {
                parseHypernym(line);
            }
        } finally {
            input.close();
        }

        verifyCycles();
        verifyRoots();

    }

    private void parseHypernym(final String line) {
        final String[] parts = line.split(",");
        if (parts.length > 1) {
            final int v = Integer.parseInt(parts[0]);
            for (int i = 1, len = parts.length; i < len; i++) {
                final int w = Integer.parseInt(parts[i]);
                graph.addEdge(v, w);
            }
        }
    }

    private void verifyCycles() {
        if (new DirectedCycle(graph).hasCycle()) {
            throw new IllegalArgumentException("Cycle detected");
        }
    }

    private void verifyRoots() {
        boolean hasRoot = false;
        for (int i = 0, len = graph.V(); i < len; i++) {
            if (graph.outdegree(i) == 0) {
                if (hasRoot) {
                    throw new IllegalArgumentException("2 roots found");
                } else {
                    hasRoot = true;
                }
            }
        }
    }

    /**
     * @return all WordNet nouns.
     */
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    /**
     * @return {@code true} if the <i>word</i> is a WordNet noun,
     * {@code false} otherwise.
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isNoun(final String word) {
        verifyNotnull(word);
        return nounMap.containsKey(word);
    }

    /**
     * The distance is the minimum length of any ancestral path between any
     * <i>synset</i> v of A and any <i>synset</i> w of B.
     *
     * @return the distance (ie. semantic relatedness) between <i>nounA</i>
     * and <i>nounB</i>.
     */
    public int distance(final String nounA, final String nounB) {
        verifyNouns(nounA, nounB);
        return sap.length(nounMap.get(nounA), nounMap.get(nounB));
    }

    /**
     * @return the shortest ancestral path <i>synset</i> that is the common
     * ancestor of <i>nounA</i> and <i>nounB</i>.
     */
    public String sap(final String nounA, final String nounB) {
        verifyNouns(nounA, nounB);
        final int v = sap.ancestor(nounMap.get(nounA), nounMap.get(nounB));
        return vertexMap.get(v);
    }

    private void verifyNouns(final String nounA, final String nounB) {
        verifyNotnull(nounA, nounB);
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
    }

    private static void verifyNotnull(final Object... objs) {
        for (final Object obj : objs) {
            if (obj == null) {
                //noinspection ProhibitedExceptionThrown
                throw new NullPointerException();
            }
        }
    }
}

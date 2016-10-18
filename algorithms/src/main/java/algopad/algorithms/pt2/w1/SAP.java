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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;

/**
 * This class is responsible for calculating the <i>shortest ancestral path</i>
 * of a {@link Digraph}.<br>
 * An ancestral path between two vertices v and w in a digraph is a directed
 * path from v to a common ancestor x, together with a directed path from
 * w to the same ancestor x.
 * A shortest ancestral path is an ancestral path of minimum total length.
 *
 * @author Nicolas Estrada.
 */
public class SAP {

    private final Digraph        graph;
    private final AncestorFinder vFinder;
    private final AncestorFinder wFinder;

    // Cache last values
    private Iterable<Integer> lastV;
    private Iterable<Integer> lastW;
    private int[]             lastAncestor;

    @SuppressWarnings("MethodParameterNamingConvention")
    public SAP(final Digraph G) {

        this.graph = new Digraph(G);
        this.vFinder = new AncestorFinder();
        this.wFinder = new AncestorFinder();

        vFinder.setCollaborator(wFinder);
        wFinder.setCollaborator(vFinder);

    }

    /**
     * @return the length of a shortest ancestral path between
     * <i>v</i> and <i>w</i>, {@code -1} if no such path.
     */
    public int length(final int v, final int w) {
        return length(Collections.singleton(v), Collections.singleton(w));
    }

    /**
     * @return a common ancestor of <i>v</i> and <i>w</i> that participates
     * in a shortest ancestral path, {@code -1} if no such path
     */
    public int ancestor(final int v, final int w) {
        return ancestor(Collections.singleton(v), Collections.singleton(w));
    }

    /**
     * @return the length of a shortest ancestral path between any vertex in
     * <i>v</i> and any vertex in <i>w</i>, {@code -1} if no such path.
     */
    public int length(final Iterable<Integer> v, final Iterable<Integer> w) {
        final int[] ancestor = lookupAncestor(v, w);
        //noinspection IfMayBeConditional
        if (ancestor[1] == Integer.MAX_VALUE) {
            return -1;
        } else {
            return ancestor[1];
        }
    }

    /**
     * @return a common ancestor that participates in shortest ancestral path,
     * {@code -1} if no such path
     */
    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w) {
        final int[] ancestor = lookupAncestor(v, w);
        return ancestor[0];
    }

    private int[] lookupAncestor(final Iterable<Integer> v,
                                 final Iterable<Integer> w) {
        if (v.equals(lastV) && w.equals(lastW)) {
            return lastAncestor;
        }
        lastAncestor = findAncestor(v, w);
        lastV = v;
        lastW = w;
        return lastAncestor;
    }

    private int[] findAncestor(final Iterable<Integer> v,
                               final Iterable<Integer> w) {

        final int[] ancestor = { -1, Integer.MAX_VALUE };
        vFinder.setAncestor(ancestor);
        wFinder.setAncestor(ancestor);

        vFinder.init(v);
        wFinder.init(w);

        boolean lockstep = true;
        boolean vContinue = true;
        boolean wContinue = true;

        do {

            if (lockstep) {
                if (vContinue) {
                    vFinder.step();
                }
            } else {
                if (wContinue) {
                    wFinder.step();
                }
            }

            lockstep = !lockstep;
            vContinue = !vFinder.isDone();
            wContinue = !wFinder.isDone();

        } while (vContinue || wContinue);

        vFinder.cleanup();
        wFinder.cleanup();
        return ancestor;
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private final class AncestorFinder {

        private final boolean[]           marked;
        private final int[]               distTo;
        private final Collection<Integer> visited;
        private final Queue<Integer>      queue;

        // Ancestor vertex and its length
        @SuppressWarnings("FieldHasSetterButNoGetter")
        private int[] ancestor;

        // Collaborating finder
        private AncestorFinder collab;

        private AncestorFinder() {
            final int numV = graph.V();
            this.marked = new boolean[numV];
            this.distTo = new int[numV];
            for (int i = 0; i < numV; i++) {
                distTo[i] = Integer.MAX_VALUE;
            }
            this.visited = new ArrayList<>(numV);
            this.queue = new LinkedList<>();
        }

        private void init(final Iterable<Integer> vertices) {
            verifyVertices(vertices);
            for (final int vertex : vertices) {
                marked[vertex] = true;
                distTo[vertex] = 0;
                visited.add(vertex);
                queue.offer(vertex);
            }
        }

        private void verifyVertices(final Iterable<Integer> vertices) {
            for (final int vertex : vertices) {
                if (vertex < 0 || vertex >= graph.V()) {
                    throw new IndexOutOfBoundsException();
                }
            }
        }

        private void setAncestor(final int[] ancestor) {
            this.ancestor = ancestor;
        }

        private void setCollaborator(final AncestorFinder collaborator) {
            this.collab = collaborator;
        }

        private void cleanup() {
            //noinspection AssignmentToNull
            ancestor = null;
            queue.clear();
            for (final int v : visited) {
                marked[v] = false;
                distTo[v] = Integer.MAX_VALUE;
            }
            visited.clear();
        }

        private boolean isDone() {
            return queue.isEmpty();
        }

        private void step() {

            if (isDone()) {
                return;
            }

            final int v = queue.poll();
            final int len = distTo[v];

            // If the distance exceeds the length of the best ancestral path
            // found so far, it's no point continuing searching
            if (len >= ancestor[1]) {
                queue.clear();
            } else {
                updateAncestor(v);
                for (final int w : graph.adj(v)) {
                    if (!marked[w]) {
                        marked[w] = true;
                        distTo[w] = len + 1;
                        visited.add(w);
                        queue.offer(w);
                    }
                }
            }
        }

        private void updateAncestor(final int v) {
            if (collab.marked[v]) {
                // A common ancestor was found so note the length
                final int length = distTo[v] + collab.distTo[v];
                if (length < ancestor[1]) {
                    ancestor[0] = v;
                    ancestor[1] = length;
                }
            }
        }
    }
}

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

package algopad.algorithms.pt1.w3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class examines 4 points at a time and checks whether they all lie on
 * the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, it checks
 * whether the three slopes between p and q, between p and r, and between
 * p and s are all equal.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("MethodWithMultipleLoops")
public class BruteCollinearPoints {

    private static final int MAX_X = 32768;
    private static final int MAX_Y = 32768;

    private final List<LineSegment> segments = new ArrayList<>(10);

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public BruteCollinearPoints(final Point[] points) {
        final Point[] sortedPts = verifyPoints(points);
        if (sortedPts.length >= 4) {
            iterateOverPointCombinations(sortedPts);
        }
    }

    @SuppressWarnings("Duplicates")
    private static Point[] verifyPoints(final Point[] points) {
        if (points == null) {
            throwNPE();
        }
        for (final Point point : points) {
            if (point == null) {
                throwNPE();
            }
        }

        final Point[] copy = points.clone();
        Arrays.sort(copy);
        for (int i = 0, len = copy.length; i < len; i++) {
            final Point pt = copy[i];
            Point pt2 = null;
            if (i + 1 < len) {
                pt2 = copy[i + 1];
            }
            if (pt2 != null && pt.compareTo(pt2) == 0) {
                throw new IllegalArgumentException();
            }
        }

        return copy;

    }

    @SuppressWarnings({ "FeatureEnvy", "StandardVariableNames" })
    private void iterateOverPointCombinations(final Point[] points) {

        final int len = points.length;

        // Begin iterating for the 1st point
        for (int i = 0; i < len; i++) {
            final Point p = points[i];

            // Look for the 2nd point
            for (int j = i + 1; j < len; j++) {
                final Point q = points[j];
                final double slopePQ = p.slopeTo(q);

                // Look for the 3rd point
                for (int k = j + 1; k < len; k++) {

                    final Point r = points[k];
                    final double slopePR = p.slopeTo(r);

                    // If the 1st 2 slopes aren't equal,
                    // don't bother searching any further
                    if (!areSlopesEqual(slopePQ, slopePR)) {
                        continue;
                    }

                    // Look for the 4th point
                    for (int l = k + 1; l < len; l++) {
                        final Point s = points[l];
                        final double slopePS = p.slopeTo(s);
                        if (areSlopesEqual(slopePQ, slopePS)) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }

    private static boolean areSlopesEqual(final double sl1, final double sl2) {
        return Double.doubleToRawLongBits(sl1) == Double.doubleToRawLongBits(sl2);
    }

    /**
     * @return the number of line segments.
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * @return the line segments.
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }

    private static void throwNPE() {
        //noinspection ProhibitedExceptionThrown
        throw new NullPointerException();
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(final String[] args) {

        // Read the N points from a file
        final In in = new In(args[0]);
        //noinspection LocalVariableNamingConvention
        final int N = in.readInt();
        final Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            final int x = in.readInt();
            final int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, MAX_X);
        StdDraw.setYscale(0, MAX_Y);
        for (final Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // Print and draw the line segments
        final BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (final LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}

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
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.SET;

/**
 * This class examines 4 points at a time and checks whether they all lie on
 * the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, it checks
 * whether the three slopes between p and q, between p and r, and between
 * p and s are all equal.
 *
 * @author Nicolas Estrada.
 */
public class FastCollinearPoints {

    private final List<LineSegment> segments      = new ArrayList<>(10);
    private final SET<PointKey>     visitedPoints = new SET<>();

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public FastCollinearPoints(final Point[] points) {
        final Point[] sortedPts = verifyPoints(points);
        if (sortedPts.length >= 4) {
            final Point[] copy = sortedPts.clone();
            for (int i = 0, len = sortedPts.length - 3; i < len; i++) {
                final Point origin = sortedPts[i];
                findCollinearSegmentsFor(origin, i, copy);
            }
        }
    }

    @SuppressWarnings({ "Duplicates", "MethodWithMultipleLoops" })
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

    @SuppressWarnings("MethodWithMultipleLoops")
    private void findCollinearSegmentsFor(final Point origin,
                                          final int originIdx,
                                          final Point[] points) {

        Arrays.sort(points, originIdx, points.length,
                    new SlopeThenNaturalPointComparator(origin));

        int start = originIdx + 1;
        int end = originIdx + 2;

        final int len = points.length;
        while (end < len) {

            final double startSlope = origin.slopeTo(points[start]);

            //noinspection MethodCallInLoopCondition
            while (end < len &&
                   areSlopesEqual(startSlope, origin.slopeTo(points[end]))) {
                end++;
            }

            if (end - start > 2 && !wasPointVisited(origin, startSlope)) {
                for (int i = start; i < end; i++) {
                    visitedPoints.add(new PointKey(points[i], startSlope));
                }
                segments.add(new LineSegment(origin, points[end - 1]));
            }

            // Continue searching
            start = end;

        }
    }

    private boolean wasPointVisited(final Point pt, final double slope) {
        return visitedPoints.contains(new PointKey(pt, slope));
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

    private static boolean areSlopesEqual(final double sl1, final double sl2) {
        return Double.doubleToRawLongBits(sl1) == Double.doubleToRawLongBits(sl2);
    }

    /**
     * {@link Point} {@link Comparator} which compares 2 points by first
     * measuring the slopes with respect to <i>origin</i> followed by their
     * natural ordering.
     */
    private static final class SlopeThenNaturalPointComparator
      implements Comparator<Point> {

        private final Point origin;

        private SlopeThenNaturalPointComparator(final Point origin) {
            this.origin = origin;
        }

        @Override
        public int compare(final Point o1, final Point o2) {
            int result = origin.slopeOrder().compare(o1, o2);
            if (result == 0) {
                result = o1.compareTo(o2);
            }
            return result;
        }
    }

    @SuppressWarnings("ComparableImplementedButEqualsNotOverridden")
    private static final class PointKey implements Comparable<PointKey> {

        private final int    address;
        private final double slope;

        private PointKey(final Point point, final double slope) {
            address = System.identityHashCode(point);
            this.slope = slope;
        }

        @Override
        public int compareTo(final PointKey o) {
            int result = Long.compare(address, o.address);
            if (result == 0) {
                result = Double.compare(slope, o.slope);
            }
            return result;
        }

        @Override
        public String toString() {
            return String.format("%s@%s", Integer.toHexString(address), slope);
        }
    }
}

/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 *
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

package algopad.algtb.w4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.min;
import static java.lang.StrictMath.sqrt;
import static java.lang.String.format;
import static java.util.Collections.sort;

/**
 * Adapted from pseudo-code (released with no restrictions) using an algorithm described here:
 * J. Kleinberg, E. Tardos, Algorithm Design, 1st edition (Pearson, 2006), pp. 230
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("TypeMayBeWeakened")
public final class Closest {

    private Closest() {}

    static double minimalDistance(final List<Point> points) {

        final List<Point> p = new ArrayList<>(points);
        sort(p, BY_INCREASING_X);

        final List<Point> q = new ArrayList<>(points);
        sort(q, BY_INCREASING_Y);

        final PointPair pair = findClosestPair(p, q);
        return pair.distance;

    }

    private static PointPair findClosestPair(final List<Point> p, final List<Point> q) {

        final int len = p.size();

        // If the points size is small, exhaustively search all pairwise distances
        if (len <= 3) {
            return searchForClosestPoints(p);
        }

        final int mid = len / 2;

        final List<Point> pX = p.subList(0, mid);
        final Set<Point> leftPts = new HashSet<>(pX);
        final List<Point> pY = filterPoints(q, leftPts);
        final PointPair pPair = findClosestPair(pX, pY);

        final List<Point> qX = p.subList(mid, len);
        final Set<Point> rightPts = new HashSet<>(qX);
        final List<Point> qY = filterPoints(q, rightPts);
        final PointPair qPair = findClosestPair(qX, qY);

        final double minDistance = min(pPair.distance, qPair.distance);
        final PointPair sPair = getClosestPairNearMedian(q, p.get(mid), minDistance);

        // Return the smallest pair of the right, left and axis closest pairs
        PointPair minPair = pPair;
        if (sPair.distance < minDistance) {
            minPair = sPair;
        } else if (qPair.distance < pPair.distance) {
            minPair = qPair;
        }

        return minPair;

    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private static PointPair searchForClosestPoints(final List<Point> points) {
        final int len = points.size();
        if (len < 2) {
            return NULL_PAIR;
        }
        if (len == 2) {
            return new PointPair(points.get(0), points.get(1));
        }
        PointPair minPair = new PointPair(points.get(0), points.get(1));
        for (int i = 1; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                final double distance = points.get(i).distanceTo(points.get(j));
                if (distance < minPair.distance) {
                    minPair = new PointPair(points.get(i), points.get(j), distance);
                }
            }
        }
        return minPair;
    }

    private static List<Point> filterPoints(final List<Point> src,
                                            final Set<Point> within) {
        final List<Point> dest = new ArrayList<>(within.size());
        for (final Point pt : src) {
            if (within.contains(pt)) {
                dest.add(pt);
            }
        }
        return dest;
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private static PointPair getClosestPairNearMedian(final List<Point> points,
                                                      final Point median,
                                                      final double minDistance) {

        final List<Point> sY = new ArrayList<>(points.size());
        for (final Point pt : points) {
            if (abs(pt.x - median.x) < minDistance) {
                sY.add(pt);
            }
        }

        PointPair minPair = NULL_PAIR;
        final int len = sY.size();
        if (len > 1) {
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    final Point pt1 = sY.get(i);
                    final Point pt2 = sY.get(j);
                    if (pt2.y - pt1.y >= minDistance) {
                        break;
                    }
                    final double distance = pt1.distanceTo(pt2);
                    if (distance < minPair.distance) {
                        minPair = new PointPair(pt1, pt2, distance);
                    }
                }
            }
        }

        return minPair;

    }

    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final List<Point> points = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                points.add(new Point(in.nextInt(), in.nextInt()));
            }
            //noinspection UseOfSystemOutOrSystemErr
            System.out.println(minimalDistance(points));
        }
    }

    // Point pair sentinel with maximum distance
    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final PointPair NULL_PAIR = new PointPair(null, null, POSITIVE_INFINITY);

    // Various comparators
    private static final Comparator<Point> BY_INCREASING_X = new Comparator<Point>() {
        @Override
        public int compare(final Point o1, final Point o2) {
            //noinspection SuspiciousNameCombination
            return Integer.compare(o1.x, o2.x);
        }
    };

    private static final Comparator<Point> BY_INCREASING_Y = new Comparator<Point>() {
        @Override
        public int compare(final Point o1, final Point o2) {
            //noinspection SuspiciousNameCombination
            return Integer.compare(o1.y, o2.y);
        }
    };

    @SuppressWarnings("PackageVisibleInnerClass")
    static class Point {

        private final int x;
        private final int y;

        Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        private double distanceTo(final Point point) {
            final int dx = x - point.x;
            final int dy = y - point.y;
            return sqrt((long) dx * dx + (long) dy * dy);
        }

        @Override
        public String toString() {
            return format("{%d,%d}", x, y);
        }
    }

    private static final class PointPair {

        private final Point pt1;
        private final Point pt2;
        private final double distance;

        private PointPair(final Point pt1, final Point pt2) {
            this(pt1, pt2, pt1.distanceTo(pt2));
        }

        private PointPair(final Point pt1, final Point pt2, final double distance) {
            this.pt1 = pt1;
            this.pt2 = pt2;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return format("(%s,%s)[%6.4f]", pt1, pt2, distance);
        }
    }
}

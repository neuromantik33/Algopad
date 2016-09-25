/*
 *  algopad.
 */

package algtb.w4;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.min;
import static java.lang.StrictMath.hypot;
import static java.lang.String.format;
import static java.util.Collections.max;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Adapted from pseudo-code (released with no restrictions) using an algorithm described here:
 * J. Kleinberg, E. Tardos, Algorithm Design, 1st edition (Pearson, 2006), pp. 230
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public final class Closest {

    private Closest() {}

    static double minimalDistance(final List<Point> points) {
        final List<Point> sortedX = copyAndSortPoints(points, BY_INCREASING_X);
        final List<Point> sortedY = copyAndSortPoints(points, BY_INCREASING_Y);
        final PointPair pair = findClosestPair(points, sortedX, sortedY);
        return pair.getDistance();
    }

    private static List<Point> copyAndSortPoints(final List<Point> points,
                                                 final Comparator<Point> comparator) {
        final List<Point> sorted = new ArrayList<>(points);
        sort(sorted, comparator);
        return sorted;
    }

    private static PointPair findClosestPair(final Collection<Point> p,
                                             final List<Point> px,
                                             final List<Point> py) {

        final int len = px.size();

        // If the points size is small, exhaustively search all pairwise distances
        if (len < 4) {
            return searchForClosestPoints(px);
        }

        final int midX = len / 2;
        final List<Point> qx = px.subList(0, midX);
        final List<Point> rx = px.subList(midX, len);

        final int midY = py.size() / 2;
        final List<Point> qy = py.subList(0, midY);
        final List<Point> ry = py.subList(midY, py.size());

        final Set<Point> q = collectDistinct(qx, qy);
        final PointPair qPair = findClosestPair(q, qx, qy);

        final Set<Point> r = collectDistinct(rx, ry);
        final PointPair rPair = findClosestPair(r, rx, ry);

        final double delta = min(qPair.getDistance(), rPair.getDistance());
        final PointPair sPair = getClosestPairNearAxis(q, py, delta);

        // Return the smallest pair of the right, left and axis closest pairs
        PointPair minPair = rPair;
        if (sPair.getDistance() < delta) {
            minPair = sPair;
        } else if (qPair.getDistance() < rPair.getDistance()) {
            minPair = qPair;
        }
        return minPair;
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private static PointPair searchForClosestPoints(final Collection<Point> points) {

        if (points.size() < 2) {
            return NULL_PAIR;
        }

        PointPair minPair = NULL_PAIR;
        for (final Point pt1 : points) {
            for (final Point pt2 : points) {
                if (!pt1.equals(pt2)) {
                    final PointPair pair = new PointPair(pt1, pt2);
                    if (pair.getDistance() < minPair.getDistance()) {
                        minPair = pair;
                    }
                }
            }
        }

        return minPair;

    }

    private static PointPair getClosestPairNearAxis(final Collection<Point> q,
                                                    final Collection<Point> py,
                                                    final double delta) {
        final Point lPoint = max(q, BY_INCREASING_X);
        final List<Point> sy = new ArrayList<>(py.size());
        for (final Point pt : py) {
            if (pt.distanceTo(lPoint) <= delta) {
                sy.add(pt);
            }
        }
        return searchForClosestPoints(sy);
    }

    @SafeVarargs
    private static <T> Set<T> collectDistinct(final Collection<T>... points) {
        //noinspection CollectionWithoutInitialCapacity
        final Set<T> distinct = new HashSet<>();
        for (final Collection<T> collection : points) {
            distinct.addAll(collection);
        }
        return distinct;
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
    private static final PointPair NULL_PAIR = new PointPair(POSITIVE_INFINITY);

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

    @SuppressWarnings("PublicInnerClass")
    public static class Point {

        public final int x;
        public final int y;

        public Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        double distanceTo(final Point point) {
            final int dx = x - point.x;
            final int dy = y - point.y;
            return hypot(dx, dy);
        }

        @Override
        public String toString() {
            return format("{%d,%d}", x, y);
        }
    }

    private static final class PointPair {

        private final Point  pt1;
        private final Point  pt2;
        private final double distance;

        private PointPair(final Point pt1, final Point pt2) {
            this.pt1 = pt1;
            this.pt2 = pt2;
            this.distance = pt1.distanceTo(pt2);
        }

        private PointPair(final double distance) {
            this.pt1 = null;
            this.pt2 = null;
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return format("(%s,%s)[%6.4f]", pt1, pt2, distance);
        }
    }
}

/*
 *  algopad.
 */

package algtb.w4;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.min;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static java.lang.String.format;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

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

        final SortedSet<Point> p = new TreeSet<>(BY_INCREASING_X);
        p.addAll(points);

        final List<Point> q = new ArrayList<>(points);
        sort(q, BY_INCREASING_Y);

        final PointPair pair = findClosestPair(p, q);
        return sqrt(pair.getDistance());

    }

    private static PointPair findClosestPair(final SortedSet<Point> p,
                                             final List<Point> q) {

        final int len = p.size();

        // If the points size is small, exhaustively search all pairwise distances
        if (len <= 3) {
            return searchForClosestPoints(p);
        }

        final Point median = findMedianPoint(p);

        final SortedSet<Point> pX = p.headSet(median);
        final List<Point> pY = filterPoints(q, pX);
        final PointPair pPair = findClosestPair(pX, pY);

        final SortedSet<Point> qX = p.tailSet(median);
        final List<Point> qY = filterPoints(q, qX);
        final PointPair qPair = findClosestPair(qX, qY);

        final double delta = min(pPair.getDistance(), qPair.getDistance());
        final PointPair sPair = getClosestPairNearMedian(q, median, delta);

        // Return the smallest pair of the right, left and axis closest pairs
        PointPair minPair = pPair;
        if (sPair.getDistance() < delta) {
            minPair = sPair;
        } else if (qPair.getDistance() < pPair.getDistance()) {
            minPair = qPair;
        }

        return minPair;

    }

    private static Point findMedianPoint(final SortedSet<Point> points) {
        Point median = null;
        final int to = points.size() / 2 - 1;
        int i = 0;
        for (final Point pt : points) {
            if (i > to) {
                median = pt;
                break;
            }
            i++;
        }
        return median;
    }

    private static List<Point> filterPoints(final List<Point> src,
                                            final SortedSet<Point> within) {
        final List<Point> dest = new ArrayList<>(within.size());
        for (final Point pt : src) {
            if (within.contains(pt)) {
                dest.add(pt);
            }
        }
        return dest;
    }

    private static PointPair getClosestPairNearMedian(final List<Point> points,
                                                      final Point median,
                                                      final double distance) {
        final List<Point> sY = new ArrayList<>(points.size());
        for (final Point pt : points) {
            if (abs(pt.x - median.x) < distance) {
                sY.add(pt);
            }
        }
        return searchForClosestPoints(sY);
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private static PointPair searchForClosestPoints(final Collection<Point> points) {
        PointPair minPair = NULL_PAIR;
        final int len = points.size();
        if (len > 1) {
            final Point[] pts = points.toArray(new Point[len]);
            for (int i = 0; i < len; i++) {
                for (int j = i + 1; j < len; j++) {
                    final PointPair pair = new PointPair(pts[i], pts[j]);
                    if (pair.getDistance() < minPair.getDistance()) {
                        minPair = pair;
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
    private static final PointPair NULL_PAIR = new PointPair(POSITIVE_INFINITY);

    // Various comparators
    @SuppressWarnings("OverlyComplexAnonymousInnerClass")
    private static final Comparator<Point> BY_INCREASING_X = new Comparator<Point>() {
        @SuppressWarnings("SuspiciousNameCombination")
        @Override
        public int compare(final Point o1, final Point o2) {
            int cmp = Integer.compare(o1.x, o2.x);
            if (cmp == 0) {
                cmp = Integer.compare(o1.y, o2.y);
                if (cmp == 0) {
                    cmp = o1.equals(o2) ? 0 : 1;
                }
            }
            return cmp;
        }
    };

    @SuppressWarnings("OverlyComplexAnonymousInnerClass")
    private static final Comparator<Point> BY_INCREASING_Y = new Comparator<Point>() {
        @SuppressWarnings("SuspiciousNameCombination")
        @Override
        public int compare(final Point o1, final Point o2) {
            int cmp = Integer.compare(o1.y, o2.y);
            if (cmp == 0) {
                cmp = Integer.compare(o1.x, o2.x);
                if (cmp == 0) {
                    cmp = o1.equals(o2) ? 0 : 1;
                }
            }
            return cmp;
        }
    };

    @SuppressWarnings({ "PublicInnerClass", "InstanceVariableNamingConvention" })
    public static class Point {

        public final int x;
        public final int y;

        public Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        double squareDistanceTo(final Point point) {
            final int dx = x - point.x;
            final int dy = y - point.y;
            return pow(dx, 2) + pow(dy, 2);
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
            this.pt1 = pt1;
            this.pt2 = pt2;
            this.distance = pt1.squareDistanceTo(pt2);
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

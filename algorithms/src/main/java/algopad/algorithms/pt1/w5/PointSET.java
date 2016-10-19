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

package algopad.algorithms.pt1.w5;

import java.util.Collection;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * This class represents a set of points in a plane.
 *
 * @author Nicolas Estrada.
 */
public class PointSET {

    private final RedBlackBST<Point2D, Boolean> points;

    public PointSET() {
        this.points = new RedBlackBST<>();
    }

    /**
     * @return {@code true} if the set is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * @return the number of points int the set.
     */
    public int size() {
        return points.size();
    }

    /**
     * Adds the point <i>p</i> to the set if not present.
     */
    public void insert(final Point2D p) {
        final Point2D point = checkNotNull(p);
        points.put(point, Boolean.TRUE);
    }

    /**
     * @return {@code true} if the set contains the point <i>p</i>,
     * {@code false} otherwise.
     */
    public boolean contains(final Point2D p) {
        final Point2D point = checkNotNull(p);
        return points.contains(point);
    }

    /**
     * Draws all the points.
     */
    public void draw() {
        for (final Point2D pt : points.keys()) {
            pt.draw();
        }
    }

    /**
     * @return all the points that are inside the rectangle <i>rect</i>.
     */
    public Iterable<Point2D> range(final RectHV rect) {

        final RectHV rectangle = checkNotNull(rect);

        final Collection<Point2D> range = new LinkedList<>();
        for (final Point2D pt : getPointsBetweenY(rectangle.ymin(),
                                                  rectangle.ymax())) {
            if (rect.contains(pt)) {
                range.add(pt);
            }
        }

        return range;

    }

    private Iterable<Point2D> getPointsBetweenY(final double ymin,
                                                final double ymax) {
        return points.keys(new Point2D(0.0, ymin), new Point2D(1.0, ymax));
    }

    /**
     * @return the nearest neighbor in the set to the point <i>p</i>,
     * {@code null} if the set is empty.
     */
    public Point2D nearest(final Point2D p) {

        final Point2D point = checkNotNull(p);

        double dist = 0.0D;
        Point2D neighbor = null;
        for (final Point2D pt : points.keys()) {
            final double newDist = point.distanceSquaredTo(pt);
            if (neighbor == null || dist > newDist) {
                dist = newDist;
                neighbor = pt;
            }
        }

        return neighbor;

    }

    @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
    private static <T> T checkNotNull(final T arg) {
        if (arg == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }
        return arg;
    }

}

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * This class represents a set of points in a plane backed by a 2d-tree.
 * A 2d-tree is a generalization of a BST to two-dimensional keys
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
public class KdTree {

    private static final double POINT_PEN_RADIUS = 0.01D;
    private static final double MIN_VAL          = 0.0D;
    private static final double MAX_VAL          = 1.0D;

    private Node root;
    private int  size;

    /**
     * @return {@code true} if the set is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return the number of points int the set.
     */
    public int size() {
        return size;
    }

    /**
     * Adds the point <i>p</i> to the set if not present.
     */
    public void insert(final Point2D p) {
        final Point2D point = checkNotNull(p);
        root = insert(root, point, true);
        if (root.bounds == null) {
            root.bounds = new RectHV(MIN_VAL, MIN_VAL, MAX_VAL, MAX_VAL);
        }
    }

    private Node insert(final Node node,
                        final Point2D newPt,
                        final boolean splitByX) {

        if (node == null) {
            size++;
            return new Node(newPt);
        }

        final Point2D pt = node.point;

        if (!pt.equals(newPt)) {
            final int result = comparePoints(newPt, pt, splitByX);
            if (result < 0) {
                node.left = insert(node.left, newPt, !splitByX);
                updateBounds(node, splitByX, true);
            } else {
                node.right = insert(node.right, newPt, !splitByX);
                updateBounds(node, splitByX, false);
            }
        }

        return node;

    }

    private static void updateBounds(final Node node,
                                     final boolean splitByX,
                                     final boolean left) {
        final Node child;
        //noinspection IfMayBeConditional
        if (left) {
            child = node.left;
        } else {
            child = node.right;
        }

        if (child.bounds == null) {

            final Point2D pt = node.point;
            final double xmin = node.bounds.xmin();
            final double ymin = node.bounds.ymin();
            final double xmax = node.bounds.xmax();
            final double ymax = node.bounds.ymax();

            if (left) {
                //noinspection IfMayBeConditional
                if (splitByX) {
                    child.bounds = new RectHV(xmin, ymin, pt.x(), ymax);
                } else {
                    child.bounds = new RectHV(xmin, ymin, xmax, pt.y());
                }
            } else {
                //noinspection IfMayBeConditional
                if (splitByX) {
                    child.bounds = new RectHV(pt.x(), ymin, xmax, ymax);
                } else {
                    child.bounds = new RectHV(xmin, pt.y(), xmax, ymax);
                }
            }
        }
    }

    private static int comparePoints(final Point2D pt1,
                                     final Point2D pt2,
                                     final boolean useXAxis) {
        //noinspection IfMayBeConditional
        if (useXAxis) {
            return Double.compare(pt1.x(), pt2.x());
        } else {
            return Double.compare(pt1.y(), pt2.y());
        }
    }

    /**
     * @return {@code true} if the set contains the point <i>p</i>,
     * {@code false} otherwise.
     */
    public boolean contains(final Point2D p) {
        final Point2D point = checkNotNull(p);
        return contains(root, point);
    }

    private static boolean contains(final Node node, final Point2D point) {
        boolean result = false;
        if (node != null && node.bounds.contains(point)) {
            result = node.point.equals(point) ||
                     contains(node.left, point) ||
                     contains(node.right, point);
        }
        return result;
    }

    /**
     * Draws all the points.
     */
    public void draw() {
        draw(root, true);
    }

    private static void draw(final Node node, final boolean splitByX) {

        if (node == null) { return; }

        drawPoint(node);
        drawBisectingLine(node, splitByX);
        draw(node.left, !splitByX);
        draw(node.right, !splitByX);

    }

    private static void drawPoint(final Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(POINT_PEN_RADIUS);
        node.point.draw();
    }

    private static void drawBisectingLine(final Node node, final boolean splitByX) {
        StdDraw.setPenRadius();
        final RectHV r = node.bounds;
        final Point2D pt = node.point;
        if (splitByX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(pt.x(), r.ymin(), pt.x(), r.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(r.xmax(), pt.y(), r.xmin(), pt.y());
        }
    }

    /**
     * @return all the points that are inside the rectangle <i>rect</i>.
     */
    public Iterable<Point2D> range(final RectHV rect) {

        final RectHV rectangle = checkNotNull(rect);
        if (root == null) {
            return Collections.emptyList();
        }

        final Queue<Point2D> points = new Queue<>();
        range(root, rectangle, points);
        return points;

    }

    private static void range(final Node node,
                              final RectHV rect,
                              final Queue<Point2D> points) {

        if (rect.contains(node.point)) {
            points.enqueue(node.point);
        }

        for (final Node child : Arrays.asList(node.left, node.right)) {
            if (child != null && rect.intersects(child.bounds)) {
                range(child, rect, points);
            }
        }
    }

    /**
     * @return the nearest neighbor in the set to the point <i>p</i>,
     * {@code null} if the set is empty.
     */
    public Point2D nearest(final Point2D p) {

        final Point2D point = checkNotNull(p);
        if (root == null) {
            //noinspection ReturnOfNull
            return null;
        }

        final Point2D rootPt = root.point;
        final Object[] ptDistance = { rootPt, rootPt.distanceSquaredTo(point) };
        nearest(root, point, ptDistance, true);

        return (Point2D) ptDistance[0];

    }

    private static void nearest(final Node node,
                                final Point2D point,
                                final Object[] ptDistance,
                                final boolean splitByX) {

        final double distance = (Double) ptDistance[1];

        if (node == null || node.bounds.distanceSquaredTo(point) > distance) {
            return;
        }

        // Update the point & distance if necessary
        final double distanceToNode = node.point.distanceSquaredTo(point);
        if (distanceToNode < distance) {
            ptDistance[0] = node.point;
            ptDistance[1] = distanceToNode;
        }

        final List<Node> children;
        final int result = comparePoints(point, node.point, splitByX);
        //noinspection IfMayBeConditional
        if (result < 0) {
            children = Arrays.asList(node.left, node.right);
        } else {
            children = Arrays.asList(node.right, node.left);
        }

        for (final Node child : children) {
            nearest(child, point, ptDistance, !splitByX);
        }
    }

    @SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
    private static <T> T checkNotNull(final T arg) {
        if (arg == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }
        return arg;
    }

    private static final class Node {

        private final Point2D point;
        private       RectHV  bounds;
        private       Node    left;
        private       Node    right;

        private Node(final Point2D point) {
            this.point = point;
        }
    }
}

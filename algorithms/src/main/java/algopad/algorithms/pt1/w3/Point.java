/******************************************************************************
 * Compilation:  javac Point.java
 * Execution:    java Point
 * Dependencies: none
 * An immutable data type for points in the plane.
 * For use on Coursera, Algorithms Part I programming assignment.
 ******************************************************************************/

package algopad.algorithms.pt1.w3;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

/**
 * Represents a point in the plane.
 *
 * @author Nicolas Estrada.
 */
public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(final int x, final int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(final Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     *
     * @return the slope between this point and the specified point
     */
    public double slopeTo(final Point that) {
        final double result;
        if (x == that.x) {
            //noinspection IfMayBeConditional
            if (y == that.y) {
                result = Double.NEGATIVE_INFINITY;
            } else {
                result = Double.POSITIVE_INFINITY;
            }
        } else //noinspection IfMayBeConditional
            if (y == that.y) {
                result = 0.0D;
            } else {
                result = (that.y - y) / (double) (that.x - x);
            }
        return result;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     *
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    @Override
    public int compareTo(final Point that) {
        if (that == null) {
            //noinspection ProhibitedExceptionThrown
            throw new NullPointerException();
        }
        //noinspection SuspiciousNameCombination
        int result = Integer.compare(y, that.y);
        if (result == 0) {
            //noinspection SuspiciousNameCombination
            result = Integer.compare(x, that.x);
        }
        return result;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(final Point o1, final Point o2) {
                final double slope1 = slopeTo(o1);
                final double slope2 = slopeTo(o2);
                return Double.compare(slope1, slope2);
            }
        };
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}

/*
 *  algopad.
 */

package algtb.w3;

import static java.text.MessageFormat.format;
import static java.util.Arrays.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
public final class CoveringSegments {

    private CoveringSegments() {}

    static int[] optimalPoints(final Segment[] segments) {

        sort(segments, BY_RIGHT_ENDPOINT);

        final List<Integer> points = new ArrayList<>(segments.length);
        Segment prev = segments[0];
        points.add(prev.getEnd());

        for (int i = 1; i < segments.length; i++) {
            final Segment segment = segments[i];
            if (segment.getStart() > prev.getEnd()) {
                prev = segment;
                points.add(segment.getEnd());
            }
        }

        return toIntArray(points);

    }

    private static final Comparator<Segment> BY_RIGHT_ENDPOINT = new Comparator<Segment>() {
        @Override
        public int compare(final Segment o1, final Segment o2) {
            return Integer.compare(o1.getEnd(), o2.getEnd());
        }
    };

    private static int[] toIntArray(final List<Integer> list) {
        final int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    @SuppressWarnings("PackageVisibleInnerClass")
    static class Segment {

        private final int start;
        private final int end;

        Segment(final int start, final int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return format("[{0},{1}]", start, end);
        }
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {

            final int n = in.nextInt();
            final Segment[] segments = new Segment[n];
            for (int i = 0; i < n; i++) {
                segments[i] = new Segment(in.nextInt(), in.nextInt());
            }

            final int[] points = optimalPoints(segments);
            System.out.println(points.length);
            for (final int point : points) {
                System.out.print(point + " ");
            }
        }
    }
}

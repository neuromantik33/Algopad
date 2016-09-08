package algtb.w3;

import static java.text.MessageFormat.format;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class CoveringSegments {

    private CoveringSegments() {}

    static int[] optimalPoints(final Segment[] segments) {

        Arrays.sort(segments, BY_RIGHT_ENDPOINT);

        System.out.println(Arrays.toString(segments));

        Segment segment = segments[0];
        List<Integer> points = new ArrayList<>(segments.length);
        points.add(segment.getEnd());

        for (int i = 1; i < segments.length; i++) {
            final int end = segments[i].getEnd();
            if (end)
        }

        //write your code here
//        final int[] points = new int[2 * segments.length];
//        for (int i = 0; i < segments.length; i++) {
//            points[2 * i] = segments[i].getStart();
//            points[2 * i + 1] = segments[i].getEnd();
//        }
//        return points;
        return new int[0];
    }

    private static final Comparator<Segment> BY_RIGHT_ENDPOINT = new Comparator<Segment>() {
        @Override
        public int compare(final Segment o1, final Segment o2) {
            return Integer.compare(o1.getEnd(), o2.getEnd());
        }
    };

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

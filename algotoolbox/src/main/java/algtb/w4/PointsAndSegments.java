/*
 *  algopad.
 */

package algtb.w4;

import static java.text.MessageFormat.format;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Nicolas Estrada.
 */
@SuppressWarnings("UnnecessarilyQualifiedInnerClassAccess")
public final class PointsAndSegments {

    private PointsAndSegments() {}

    static int[] countSegments(final Segment[] segments, final int[] points) {

        final int[] counts = new int[points.length];
        final int estimatedSize = (segments.length << 1) + points.length;
        final Queue<SweepEvent> heap = new PriorityQueue<>(estimatedSize);

        addPointsToHeap(points, heap);
        addSegmentsToHeap(segments, heap);

        // Sweep the dimension by increasing point values and react to the events accordingly
        int currentSegments = 0;
        while (!heap.isEmpty()) {
            final SweepEvent evt = heap.poll();
            switch (evt.type) {
                case POINT:
                    counts[evt.index] = currentSegments;
                    break;
                case SEGMENT_START:
                    currentSegments++;
                    break;
                case SEGMENT_END:
                    currentSegments--;
                    break;
            }
        }

        return counts;

    }

    private static void addPointsToHeap(final int[] points,
                                        final Queue<SweepEvent> heap) {
        for (int i = 0, len = points.length; i < len; i++) {
            heap.offer(new SweepEvent(SweepEvent.Type.POINT, points[i], i));
        }
    }

    private static void addSegmentsToHeap(final Segment[] segments,
                                          final Queue<SweepEvent> heap) {
        for (final Segment segment : segments) {
            heap.offer(new SweepEvent(SweepEvent.Type.SEGMENT_START, segment.start));
            heap.offer(new SweepEvent(SweepEvent.Type.SEGMENT_END, segment.end));
        }
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    public static void main(final String... args) {
        try (final Scanner in = new Scanner(System.in, "UTF-8")) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            final Segment[] segments = new Segment[n];
            for (int i = 0; i < n; i++) {
                segments[i] = new Segment(in.nextInt(), in.nextInt());
            }
            final int[] points = new int[m];
            for (int i = 0; i < m; i++) {
                points[i] = in.nextInt();
            }
            final int[] counts = countSegments(segments, points);
            for (final int x : counts) {
                //noinspection UseOfSystemOutOrSystemErr
                System.out.print(x + " ");
            }
        }
    }

    @SuppressWarnings({ "ComparableImplementedButEqualsNotOverridden", "PackageVisibleField" })
    private static final class SweepEvent implements Comparable<SweepEvent> {

        // Ordering is VERY IMPORTANT as it is important for SEGMENT_START
        // events to have higher priority than POINT events, ergo for POINT < SEGMENT_END
        enum Type {
            SEGMENT_START, POINT, SEGMENT_END
        }

        final Type type;
        final int  point;

        // For point events only
        final int index;

        // Constructor for segments
        private SweepEvent(final Type type, final int point) {
            this(type, point, -1);
        }

        // Constructor for points
        private SweepEvent(final Type type, final int point, final int index) {
            this.type = type;
            this.point = point;
            this.index = index;
        }

        @SuppressWarnings("IfMayBeConditional")
        @Override
        public int compareTo(final SweepEvent o) {
            final int cmp = Integer.compare(point, o.point);
            if (cmp == 0) {
                return type.compareTo(o.type);
            }
            return cmp;
        }
    }

    @SuppressWarnings("PublicInnerClass")
    public static class Segment {

        public final int start;
        public final int end;

        public Segment(final int start, final int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return format("[{0},{1}]", start, end);
        }
    }
}


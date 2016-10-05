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

package algopad.algtb.w3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static java.text.MessageFormat.format;
import static java.util.Arrays.sort;

/**
 * @author Nicolas Estrada.
 */
public final class CoveringSegments {

    private CoveringSegments() {}

    static int[] optimalPoints(final Segment[] segments) {

        sort(segments, BY_RIGHT_ENDPOINT);

        final List<Integer> points = new ArrayList<>(segments.length);
        Segment prev = segments[0];
        points.add(prev.end);

        for (int i = 1, len = segments.length; i < len; i++) {
            final Segment segment = segments[i];
            if (segment.start > prev.end) {
                prev = segment;
                points.add(segment.end);
            }
        }

        return toIntArray(points);

    }

    private static final Comparator<Segment> BY_RIGHT_ENDPOINT = new Comparator<Segment>() {
        @Override
        public int compare(final Segment o1, final Segment o2) {
            return Integer.compare(o1.end, o2.end);
        }
    };

    private static int[] toIntArray(final List<Integer> list) {
        final int len = list.size();
        final int[] array = new int[len];
        for (int i = 0; i < len; i++) {
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

        @Override
        public String toString() {
            return format("[{0},{1}]", start, end);
        }
    }

    @SuppressWarnings({ "UseOfSystemOutOrSystemErr", "MethodWithMultipleLoops" })
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

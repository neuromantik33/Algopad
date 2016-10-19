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

package algopad.algorithms.pt1.w2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings({ "UtilityClassWithoutPrivateConstructor",
                    "NonFinalUtilityClass" })
public class Subset {

    @SuppressWarnings({ "FeatureEnvy", "MethodWithMultipleLoops" })
    public static void main(final String... args) {

        final int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }

        final RandomizedQueue<String> queue = new RandomizedQueue<>();

        /*
            Keep the first item in memory.
            When the i-th item arrives (for i>1):
            with probability 1/i, keep the new item instead of the current item;
            with probability 1-1/i, keep the current item and discard the new item.
         */

        int count = 0;
        while (true) {

            final String value = StdIn.readString();
            if (count < k) {
                queue.enqueue(value);
                count++;
            } else {
                if (StdRandom.bernoulli(1.0 / queue.size())) {
                    queue.dequeue();
                    queue.enqueue(value);
                } else {
                    // Do nothing and discard sample
                }
            }

            if (StdIn.isEmpty()) {
                break;
            }
        }

        if (queue.size() != k) {
            throw new IllegalStateException("Impossible");
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}

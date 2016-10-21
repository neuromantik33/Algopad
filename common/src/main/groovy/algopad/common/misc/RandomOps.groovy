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

package algopad.common.misc

/**
 * Some useful methods for working with {@link Random}s.
 *
 * @author Nicolas Estrada.
 */
@Category(Random)
class RandomOps {

    /**
     * Rearranges the elements of the specified <i>listOrArray</i> in uniformly random order.
     */
    @SuppressWarnings('GroovyUntypedAccess')
    static void shuffle(Random rnd, def listOrArray) {
        int len = listOrArray.size()
        (0..<len).each { int idx ->
            int randomIx = idx + rnd.nextInt(len - idx)
            listOrArray.swap idx, randomIx
        }
    }

    /**
     * Generates random characters present in an <i>alphabet</i> and returns a string.
     *
     * @param len the length of the string.
     */
    static String nextString(Random rnd, int len, Range alphabet) {
        def sb = new StringBuilder()
        len.times {
            int randomIx = rnd.nextInt(alphabet.size())
            sb.append(alphabet[randomIx])
        }
        sb.toString()
    }

    /**
     * Generates random integers and returns them as an array.
     *
     * @param len the length of the int array.
     * @param bound optional upper bound (exclusive). Must be positive.
     * @see Random#nextInt(int)
     */
    static int[] nextInts(Random rnd, int len, int bound = 0) {
        def array = new int[len]
        def nextInt = bound ? rnd.&nextInt.curry(bound) : rnd.&nextInt
        len.times {
            array[it] = nextInt()
        }
        array
    }
}

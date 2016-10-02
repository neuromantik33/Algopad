/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

/**
 * Some useful methods for working with {@link Random}s.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
@Category(Random)
class RandomOps {

    /**
     * Rearranges the elements of the specified <i>array</i> in uniformly random order.
     */
    static void shuffle(Random rnd, Object[] array) {
        int len = array.length
        (0..<len).each { int idx ->
            int randomIx = idx + rnd.nextInt(len - idx)
            array.swap idx, randomIx
        }
    }
}

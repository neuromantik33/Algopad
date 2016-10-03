/*
 *  algopad.
 */

package algdsgn2.w6

import algopad.common.ds.BitArray
import groovy.transform.CompileStatic

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class Clause {

    int v1, v2
    boolean not1, not2

    boolean evaluate(final BitArray bits) {
        def val1 = bits[v1]
        if (not1) {
            val1 = !val1
        }
        def val2 = bits[v2]
        if (not2) {
            val2 = !val2
        }
        val1 || val2
    }

    String toString() {
        def s1 = (not1 ? '¬' : '') + "x($v1)"
        def s2 = (not2 ? '¬' : '') + "x($v2)"
        "{ $s1 ∨ $s2 }"
    }
}

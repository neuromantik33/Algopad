/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

/**
 * Some useful methods for working with {@link Closure}s.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Closures {

    /**
     * <b>At the moment, only single argument closures are supported</b>.
     *
     * @return a negated version of the passed-in <i>predicate</i>.
     */
    static Closure<Boolean> negate(Closure<Boolean> predicate) {
        assert predicate.maximumNumberOfParameters == 1
        { arg -> !predicate.call(arg) }
    }
}

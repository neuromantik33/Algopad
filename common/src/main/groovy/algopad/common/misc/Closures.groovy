/*
 *  algopad.
 */

package algopad.common.misc

/**
 * Some useful methods for working with {@link Closure}s.
 *
 * @author Nicolas Estrada.
 */
class Closures {

    /**
     * <b>At the moment, only single argument closures are supported</b>.
     *
     * @return a negated version of the passed-in <i>predicate</i>.
     */
    static Closure<Boolean> negate(Closure<Boolean> predicate) {
        { ... args -> !predicate.call(*args) }
    }
}

/*
 *  algopad.
 */

package algopad.common.misc

/**
 * Some useful methods for working various counting problems (permutations, combinations... etc).
 *
 * @author Nicolas Estrada.
 */
class Counting {

    /**
     * Chooses <i>k</i> items for the input <i>elements</i> and returns a list of unique combinations
     * in accordance with binomial coefficients.
     *
     * @return a list of list of elements of size k.
     */
    static List chooseK(int k, List elements) {

        def len = elements.size()
        def selections = new int[k]
        def results = []
        def choose = { int ix = 0 ->
            if (ix == selections.length) {
                results << selections.collect { int it -> elements[it] }
            } else {
                def start = ix > 0 ? selections[ix - 1] + 1 : 0
                for (int i = start; i < len; i++) {
                    selections[ix] = i
                    call ix + 1
                }
            }
            results
        }

        choose()

    }
}

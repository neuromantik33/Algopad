/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

/**
 * Some useful methods for working with arrays.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Matrices {

    /**
     * TODO
     * @param matrix
     * @param closure
     */
    @SuppressWarnings("GroovyParameterNamingConvention")
    static def initMatrix(Object[][] matrix, Closure closure) {
        matrix.eachWithIndex { Object[] row, int i ->
            row.eachWithIndex { Object col, int j ->
                matrix[i][j] = closure.call(i, j)
            }
        }
    }
}

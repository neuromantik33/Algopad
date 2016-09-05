/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic

/**
 * Some useful methods for working with 2D matrices/arrays.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Matrices {

    /**
     * Scans the entire <i>matrix</i> and executes the param {@link Closure}
     * in order to initialize each cell.
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

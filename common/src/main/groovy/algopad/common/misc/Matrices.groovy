/*
 *  algopad.
 */

package algopad.common.misc

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FromString

/**
 * Some useful methods for working with 2D matrices/arrays.
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class Matrices {

    final static LINE_SEPARATOR = System.getProperty('line.separator')

    /**
     * Scans the entire <i>matrix</i> and executes the param {@link Closure}
     * in order to initialize each cell.
     */
    static def initMatrix(Object[][] matrix,
                          @ClosureParams(value = FromString, options = 'Integer,Integer') Closure closure) {
        matrix.eachWithIndex { Object[] row, int i ->
            row.eachWithIndex { Object col, int j ->
                matrix[i][j] = closure.call(i, j)
            }
        }
    }

    /**
     * @return a string representation of the contents of the specified matrix.
     */
    @SuppressWarnings('GroovyUntypedAccess')
    static String toString(matrix) {
        def sb = new StringBuilder()
        matrix.eachWithIndex { row, int i ->
            row.eachWithIndex { col, int j ->
                sb.append(col).append(' ')
            }
            sb.append LINE_SEPARATOR
        }
        sb.toString()
    }
}

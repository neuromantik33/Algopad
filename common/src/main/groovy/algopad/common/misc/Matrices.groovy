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
    @SuppressWarnings(['GroovyUntypedAccess', "GroovyUnusedDeclaration"])
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

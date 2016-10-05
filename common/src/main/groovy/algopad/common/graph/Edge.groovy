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

package algopad.common.graph

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false)
class Edge {

    final Vertex v
    final Vertex w
    final int weight

    Edge(Vertex v, Vertex w, int weight) {
        this.v = v
        this.w = w
        this.weight = weight
    }

    Vertex other(Vertex vertex) {
        assert vertex.is(v) || vertex.is(w)
        return vertex.is(v) ? w : v
    }
}

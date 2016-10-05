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

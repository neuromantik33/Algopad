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
 * Some useful methods for working with {@link List}s.
 *
 * @author Nicolas Estrada.
 */
@Category(List)
class ListOps {

    /**
     * @return {@code true} if the list is sorted, {@code false} otherwise.
     */
    @SuppressWarnings('ChangeToOperator')
    static boolean isSorted(List list) {
        if (list.size() < 2) {
            return true
        }
        def sorted = true
        def itr = list.iterator()
        def o1 = itr.next()
        while (itr.hasNext()) {
            def o2 = itr.next()
            if (o1 > o2) {
                sorted = false
                break
            }
            o1 = o2
        }
        sorted
    }
}

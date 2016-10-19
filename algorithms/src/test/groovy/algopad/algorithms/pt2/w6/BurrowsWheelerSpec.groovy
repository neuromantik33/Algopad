/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
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

package algopad.algorithms.pt2.w6

import java.util.concurrent.TimeUnit

import spock.lang.Specification
import spock.lang.Unroll

import static algopad.algorithms.pt2.w6.Util.*
import static java.lang.System.nanoTime

@SuppressWarnings('GroovyAccessibility')
class BurrowsWheelerSpec extends Specification {

    @Unroll
    def 'it should encode the file #name using the burrows wheeler transformation'() {

        given:
        def input = newSource(getClass().getResource(name))
        def output = new ByteArrayOutputStream()

        when:
        new BurrowsWheeler.Encoder(input, newSink(output)).encode()

        then:
        output.toByteArray() == getClass().getResource("${name}.bwt").bytes

        where:
        name << ['abra.txt', 'aesop.txt', 'us.gif']

    }

    @Unroll
    def 'it should decode the file #name using the burrows wheeler transformation'() {

        given:
        def time = nanoTime()
        def memory = Util.usedMemory
        def input = newSource(getClass().getResource("${name}.bwt"))
        def output = new ByteArrayOutputStream()

        when:
        new BurrowsWheeler.Decoder(input, newSink(output)).decode()

        then:
        output.toByteArray() == getClass().getResource(name).bytes

        cleanup:
        println "Time taken : ${TimeUnit.NANOSECONDS.toMillis(nanoTime() - time)}ms"
        println "Memory usage : ${Util.usedMemory - memory}kb"

        where:
        name << ['abra.txt', 'aesop.txt', 'us.gif']

    }
}

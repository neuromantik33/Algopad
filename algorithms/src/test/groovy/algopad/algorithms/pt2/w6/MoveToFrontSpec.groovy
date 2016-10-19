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

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.System.nanoTime
import static java.util.concurrent.TimeUnit.NANOSECONDS
import static algopad.algorithms.pt2.w6.Util.*

@SuppressWarnings('GroovyAccessibility')
class MoveToFrontSpec extends Specification {

    @Unroll
    def 'it should encode the file #name using the move-to-front transformation'() {

        given:
        def input = newSource(getClass().getResource(name))
        def output = new ByteArrayOutputStream()

        when:
        new MoveToFront.Encoder(input, newSink(output)).encode()

        then:
        output.toByteArray() == getClass().getResource("${name}.mtf").bytes

        where:
        name << ['abra.txt', 'aesop.txt', 'us.gif']

    }

    @Unroll
    def 'it should decode the file #name using the move-to-front transformation'() {

        given:
        def input = newSource(getClass().getResource("${name}.mtf"))
        def output = new ByteArrayOutputStream()

        when:
        new MoveToFront.Decoder(input, newSink(output)).decode()

        then:
        output.toByteArray() == getClass().getResource(name).bytes

        where:
        name << ['abra.txt', 'aesop.txt', 'us.gif']

    }

    def 'it should move-to-front the lilwomen.txt in reasonable time'() {

        given:
        def url = getClass().getResource(name)
        def input = newSource(url)
        def output = new ByteArrayOutputStream()

        when:
        def start = nanoTime()
        new MoveToFront.Encoder(input, newSink(output)).encode()
        def end = nanoTime()

        then:
        NANOSECONDS.toMillis(end - start) < 500

        cleanup:
        println "Time taken : ${NANOSECONDS.toMillis(end - start)}ms"

        where:
        name = 'textfiles/lilwomen.txt'

    }
}

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

package algopad.common

import org.junit.rules.Stopwatch
import org.junit.runner.Description

import static java.util.concurrent.TimeUnit.NANOSECONDS

/**
 * Default {@link Stopwatch} implementation for long tests.
 *
 * @author Nicolas Estrada.
 */
class DefaultStopwatch extends Stopwatch {

    @Override
    protected void finished(final long nanos, final Description description) {
        println "Test '${description.methodName}' finished, spent ${NANOSECONDS.toMillis(nanos)}ms"
    }
}

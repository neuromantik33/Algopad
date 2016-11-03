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

package algopad.hackerrank.strings

import algopad.common.DefaultStopwatch
import groovy.transform.CompileStatic
import org.junit.Rule
import org.junit.rules.Stopwatch
import org.junit.rules.TemporaryFolder
import spock.lang.*

@Narrative('''We're going to make our own Contacts application! The application must perform two types of operations:
              1) add name, where name is a string denoting a contact name. This must store name as a new contact.
              2) find partial, where partial is a string denoting a partial name to search the application for.
                 It must count the number of contacts starting with partial and print the count on a new line.
                 Given n sequential add and find operations, perform each operation in order.''')
@See('http://www.hackerrank.com/challenges/ctci-contacts')
class Contacts extends Specification {

    @Rule
    TemporaryFolder tf = new TemporaryFolder()

    @Rule
    Stopwatch stopwatch = new DefaultStopwatch()

    @Subject
    @CompileStatic
    class ContactTrie {
        final char first = 'a' as char
        final Node root = new Node()
        final PrintWriter writer

        ContactTrie(final PrintWriter writer) {
            this.writer = writer
        }

        void add(String name) {
            Node node = root
            int i = 0, len = name.length()
            while (i < len) {
                node.size++
                int idx = (int) (name.charAt(i) - first)
                node = node.newChildAt(idx)
                i++
            }
            node.size++
        }

        void find(String partial) {
            Node node = root
            int i = 0, len = partial.length()
            while (i < len && node != null) {
                int idx = (int) (partial.charAt(i) - first)
                node = node.children ? node.children[idx] : null
                i++
            }
            if (node) {
                writer.print "${node.size}\n"
            } else {
                writer.print "0\n"
            }
        }

        static class Node {
            int size
            Node[] children

            Node newChildAt(int idx) {
                if (children == null) { children = new Node[26] }
                if (children[idx] == null) { children[idx] = new Node() }
                children[idx]
            }
        }
    }

    @SuppressWarnings('ChangeToOperator')
    @Unroll
    def 'given a sequence of operations in #input, it should match the output contained in #output'() {

        given:
        def file = tf.newFile()
        def writer = new PrintWriter(file)
        def contacts = new ContactTrie(writer)

        when:
        url(input).withInputStream {
            def scn = new Scanner(it)
            def n = scn.nextInt()
            n.times {
                def op = scn.next()
                def val = scn.next()
                contacts."$op"(val)
            }
            writer.flush()
            writer.close()
        }

        then:
        file.text.trim() == url(output).text.trim()

        where:
        input         | output
        'input00.txt' | 'output00.txt'
        'input01.txt' | 'output01.txt'
        'input03.txt' | 'output03.txt'
        'input05.txt' | 'output05.txt'
        'input12.txt' | 'output12.txt'

    }

    private URL url(name) {
        getClass().getResource(name)
    }
}

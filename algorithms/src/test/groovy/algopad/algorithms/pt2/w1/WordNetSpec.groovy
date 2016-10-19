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

package algopad.algorithms.pt2.w1

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings(['GroovyAccessibility', 'LongLine'])
class WordNetSpec extends Specification {

    @Shared
    def wordNet = new WordNet(filePathFor('synsets.txt'),
                              filePathFor('hypernyms.txt'))

    def filePathFor(name) {
        return getClass().getResource(name).file
    }

    @Unroll
    def 'all methods should throw a error if any argument to #method is null'() {

        when:
        new WordNet(null, '')

        then:
        thrown(NullPointerException)

        when:
        new WordNet('', null)

        then:
        thrown(NullPointerException)

        when:
        def net = new WordNet('', '')
        net."$method"(*args)

        then:
        thrown(NullPointerException)

        where:
        method     | args
        'isNoun'   | [null]
        'distance' | ['dog', null]
        'distance' | [null, 'cat']
        'sap'      | ['dog', null]
        'sap'      | [null, 'cat']

    }

    @Unroll
    def "it should throw an error if the '#hypernym' file results in a non-root DAG"() {

        when:
        new WordNet(filePathFor("${synset}.txt"),
                    filePathFor("${hypernym}.txt"))

        then:
        thrown(IllegalArgumentException)

        where:
        synset                | hypernym
        'synsets100-subgraph' | 'hypernyms3InvalidCycle'
        'synsets100-subgraph' | 'hypernyms3InvalidTwoRoots'
        'synsets100-subgraph' | 'hypernyms6InvalidCycle+Path'
        'synsets100-subgraph' | 'hypernyms6InvalidCycle'
        'synsets100-subgraph' | 'hypernyms6InvalidTwoRoots'

    }

    def 'the primary wordnet should display the following characteristics'() {

        expect:
        wordNet.nouns().size() == 119188
        wordNet.graph.V() == 82192
        wordNet.graph.E() == 84505

    }

    @Unroll
    def 'it should calculate the following ancestors and distances for #a and #b'() {

        expect:
        wordNet.sap(a, b) == ancestor
        wordNet.distance(a, b) == distance

        where:
        a                      | b                       | ancestor                                                | distance
        'worm'                 | 'bird'                  | 'animal animate_being beast brute creature fauna'       | 5
        'individual'           | 'edible_fruit'          | 'physical_entity'                                       | 7
        'Palace_of_Versailles' | 'orthophosphorous_acid' | 'physical_entity'                                       | 17
        'horse'                | 'zebra'                 | 'equine equid'                                          | 2
        'horse'                | 'cat'                   | 'placental placental_mammal eutherian eutherian_mammal' | 7
        'horse'                | 'bear'                  | 'placental placental_mammal eutherian eutherian_mammal' | 6
        'horse'                | 'table'                 | 'social_group'                                          | 6
        'manioc'               | 'old_style'             | 'entity'                                                | 18
        'Hottentot_fig'        | 'diltiazem'             | 'physical_entity'                                       | 14
        'gran'                 | 'Central_Standard_Time' | 'entity'                                                | 13

    }

    @Unroll
    def 'it should calculate the very far distances for #a and #b'() {

        expect:
        wordNet.distance(a, b) == distance

        where:
        a                        | b              | distance
        'white_marlin'           | 'mileage'      | 23
        'Black_Plague'           | 'black_marlin' | 33
        'American_water_spaniel' | 'histology'    | 27
        'Brown_Swiss'            | 'barrel_roll'  | 29

    }

    @Unroll
    def "it should calculate the distances for '#a' and '#b' using '#synset/#hypernym'"() {

        when:
        def net = new WordNet(filePathFor("${synset}.txt"),
                              filePathFor("${hypernym}.txt"))

        then:
        net.distance(a, b) == distance

        where:
        synset                 | hypernym                          | a           | b                  | distance
        'synsets15'            | 'hypernyms15Path'                 | 'a'         | 'b'                | 1
        'synsets15'            | 'hypernyms15Tree'                 | 'a'         | 'b'                | 1
        'synsets6'             | 'hypernyms6TwoAncestors'          | 'a'         | 'b'                | 1
        'synsets11'            | 'hypernyms11AmbiguousAncestor'    | 'a'         | 'b'                | 1
        'synsets8'             | 'hypernyms8WrongBFS'              | 'a'         | 'b'                | 1
        'synsets11'            | 'hypernyms11ManyPathsOneAncestor' | 'a'         | 'b'                | 1
        'synsets8'             | 'hypernyms8ManyAncestors'         | 'a'         | 'b'                | 1
        'synsets100-subgraph'  | 'hypernyms100-subgraph'           | 'fibrin'    | 'aminotransferase' | 4
        'synsets500-subgraph'  | 'hypernyms500-subgraph'           | 'matchwood' | 'protamine'        | 10
        'synsets1000-subgraph' | 'hypernyms1000-subgraph'          | 'leg_bone'  | 'nerve'            | 7

    }

    @Unroll
    def "it should calculate the sap for '#a' and '#b' using '#synset/#hypernym'"() {

        when:
        def net = new WordNet(filePathFor("${synset}.txt"),
                              filePathFor("${hypernym}.txt"))

        then:
        net.distance(a, b) == distance
        net.sap(a, b) == sap

        where:
        synset                 | hypernym                          | a                                   | b                | distance | sap
        'synsets15'            | 'hypernyms15Path'                 | 'a'                                 | 'b'              | 1        | 'b'
        'synsets15'            | 'hypernyms15Tree'                 | 'a'                                 | 'b'              | 1        | 'a'
        'synsets6'             | 'hypernyms6TwoAncestors'          | 'a'                                 | 'b'              | 1        | 'a'
        'synsets11'            | 'hypernyms11AmbiguousAncestor'    | 'a'                                 | 'b'              | 1        | 'b'
        'synsets8'             | 'hypernyms8WrongBFS'              | 'a'                                 | 'b'              | 1        | 'b'
        'synsets11'            | 'hypernyms11ManyPathsOneAncestor' | 'a'                                 | 'b'              | 1        | 'b'
        'synsets8'             | 'hypernyms8ManyAncestors'         | 'a'                                 | 'b'              | 1        | 'b'
        'synsets100-subgraph'  | 'hypernyms100-subgraph'           | 'iodinated_protein'                 | 'telomerase'     | 3        | 'protein'
        'synsets500-subgraph'  | 'hypernyms500-subgraph'           | 'glutamic_oxaloacetic_transaminase' | 'lysozyme'       | 4        | 'enzyme'
        'synsets1000-subgraph' | 'hypernyms1000-subgraph'          | 'adipose_tissue'                    | 'straight_chain' | 9        | 'thing'

    }
}

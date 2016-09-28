/*
 *  algopad.
 */

package algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w5.EditDistance.calculateEditDistance

@Narrative('''The edit distance between two strings is the minimum number of insertions, deletions,
              and mismatches in an alignment of two strings.''')
class EditDistanceSpec extends Specification {

    @Unroll
    def "it should compute the edit distance #distance of the 2 strings '#s' and '#t'"() {

        expect:
        calculateEditDistance(s, t) == distance

        where:
        s         | t          | distance
        'ab'      | 'ab'       | 0
        'short'   | 'ports'    | 3
        'editing' | 'distance' | 5

    }
}

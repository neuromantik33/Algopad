/*
 *  algopad.
 */

package algtb.w5

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static algtb.w5.LCS3.getLCSLength

@Narrative('In this problem, your goal is to compute the length of a longest common sub-sequence of three sequences.')
class LCS3Spec extends Specification {

    @Unroll
    def 'Given three sequences #seq1, #seq2, and #seq3, it should find the length of their longest common subsequence'() {

        given:
        seq1 = seq1 as int[]
        seq2 = seq2 as int[]
        seq3 = seq3 as int[]

        expect:
        getLCSLength(seq1, seq2, seq3) == len

        where:
        seq1                                    | seq2                            | seq3                | len
        [1, 2, 3]                               | [2, 1, 3]                       | [1, 3, 5]           | 2
        [8, 3, 2, 1, 7]                         | [8, 2, 1, 3, 8, 10, 7]          | [6, 8, 3, 1, 4, 7]  | 3
        [3, 5, 4, 34, 2, 56, 4, 7, 32, 5, 6, 3] | [2, 3, 2, 4, 7, 32, 4, 5, 6, 4] | [2, 4, 7, 32, 5, 3] | 5

    }
}